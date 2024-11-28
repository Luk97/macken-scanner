package com.nickel.mackenscanner.ui.scaffold

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nickel.mackenscanner.ui.scanner.ScannerScreen

@Composable
internal fun MainScaffold() {
    Scaffold { innerPadding ->
        ScannerScreen(Modifier.padding(innerPadding))
    }
}