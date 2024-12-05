package com.nickel.mackenscanner.ui.scanner

sealed class ScannerScreenState {
    data object Idle : ScannerScreenState()
    data object Scanning : ScannerScreenState()
    data object Loading : ScannerScreenState()
    data object Success : ScannerScreenState()
    data object Error : ScannerScreenState()
}