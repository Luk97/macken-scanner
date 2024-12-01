package com.nickel.mackenscanner.ui.scanner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nickel.mackenscanner.theme.AppTheme
import com.nickel.mackenscanner.theme.ThemedPreviews
import com.nickel.mackenscanner.ui.scanner.ScannerScreenState.ScannerState
import com.nickel.mackenscanner.ui.scanner.components.CameraPreview
import com.nickel.mackenscanner.ui.scanner.components.QrCodeSection

@Composable
internal fun ScannerScreen(
    modifier: Modifier = Modifier,
    viewModel: ScannerScreenViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    ScannerScreenContent(
        state = state,
        modifier = modifier,
        onScannerStarted = viewModel::onScannerStarted,
        onScannerSucceeded = viewModel::onScannerSucceeded,
        onScannerCanceled = viewModel::onScannerCanceled
    )
}

@Composable
fun ScannerScreenContent(
    state: ScannerScreenState,
    modifier: Modifier = Modifier,
    onScannerStarted: () -> Unit = {},
    onScannerSucceeded: (String) -> Unit = {},
    onScannerCanceled: () -> Unit = {}
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .background(AppTheme.colorScheme.background)
    ) {
        if (state.scannerState is ScannerState.Scanning) {
            CameraPreview(
                onScannerSucceeded = onScannerSucceeded,
                onScannerCanceled = onScannerCanceled
            )
        } else {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.fillMaxSize()
            ) {
                when (state.scannerState) {
                    ScannerState.Loading -> {}
                    is ScannerState.Success -> {}
                    ScannerState.Error -> {}
                    else -> Spacer(Modifier.weight(1f))
                }
                QrCodeSection(
                    onScannerStarted = onScannerStarted,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@ThemedPreviews
@Composable
private fun ScannerScreenPreview() {
    AppTheme {
        ScannerScreen()
    }
}