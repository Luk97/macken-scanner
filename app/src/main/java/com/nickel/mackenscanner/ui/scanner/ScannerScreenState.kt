package com.nickel.mackenscanner.ui.scanner

data class ScannerScreenState(
    val scannerState: ScannerState = ScannerState.Idle
) {
    sealed class ScannerState {
        data object Idle : ScannerState()
        data object Scanning : ScannerState()
        data object Loading : ScannerState()
        data class Success(val result: String) : ScannerState()
        data object Error : ScannerState()
    }
}