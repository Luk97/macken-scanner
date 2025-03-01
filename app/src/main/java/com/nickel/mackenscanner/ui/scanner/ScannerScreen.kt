package com.nickel.mackenscanner.ui.scanner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nickel.mackenscanner.data.ScanValidationResult
import com.nickel.mackenscanner.ui.scanner.components.ErrorView
import com.nickel.mackenscanner.ui.scanner.components.IdleView
import com.nickel.mackenscanner.ui.scanner.components.LoadingView
import com.nickel.mackenscanner.ui.scanner.components.ScanningView
import com.nickel.mackenscanner.ui.scanner.components.SuccessView
import com.nickel.mackenscanner.ui.theme.AppTheme
import com.nickel.mackenscanner.ui.theme.ThemedPreviews

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
private fun ScannerScreenContent(
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
        when(state) {
            is ScannerScreenState.Error -> ErrorView(state.result)
            ScannerScreenState.Idle -> IdleView(
                onScannerStarted = onScannerStarted
            )
            ScannerScreenState.Loading -> LoadingView()
            ScannerScreenState.Scanning -> ScanningView(
                onScannerSucceeded = onScannerSucceeded,
                onScannerCanceled = onScannerCanceled
            )
            is ScannerScreenState.Success -> SuccessView(state.result)
        }
    }
}

@ThemedPreviews
@Composable
private fun ScannerScreenPreview(
    @PreviewParameter(ScannerScreenStatePreviewParameterProvider::class) state: ScannerScreenState
) {
    AppTheme {
        ScannerScreenContent(
            state = state
        )
    }
}

private class ScannerScreenStatePreviewParameterProvider: PreviewParameterProvider<ScannerScreenState> {
    override val values = sequenceOf(
        ScannerScreenState.Idle,
        ScannerScreenState.Loading,
        ScannerScreenState.Success(
            result = ScanValidationResult(
                message = "Success",
                success = true
            )
        ),
        ScannerScreenState.Error(
            result = ScanValidationResult(
                message = "sdkjgflsdkjgkölsdjfgäsjdfgjsdäkfgjskdäjgklsdjgkldsjgfj",
                success = false
            )
        )
    )
}