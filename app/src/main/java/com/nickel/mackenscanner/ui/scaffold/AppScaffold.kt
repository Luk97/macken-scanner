package com.nickel.mackenscanner.ui.scaffold

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nickel.mackenscanner.ui.scanner.ScannerScreen

@Composable
internal fun AppScaffold(
    modifier: Modifier = Modifier,
    viewModel: AppScaffoldViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    AppScaffoldContent(
        state = state,
        modifier = modifier,
        onInfoClicked = viewModel::onInfoClicked
    )
}

@Composable
private fun AppScaffoldContent(
    state: AppScaffoldState,
    modifier: Modifier = Modifier,
    onInfoClicked: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            AppTopBar(onInfoClicked = onInfoClicked)
        },
        modifier = modifier
    ) { innerPadding ->
        ScannerScreen(modifier = Modifier.padding(innerPadding))
    }
}

