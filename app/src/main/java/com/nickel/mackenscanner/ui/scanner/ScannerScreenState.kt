package com.nickel.mackenscanner.ui.scanner

import com.nickel.mackenscanner.data.ScanValidationResult

sealed class ScannerScreenState {
    data object Idle : ScannerScreenState()
    data object Scanning : ScannerScreenState()
    data object Loading : ScannerScreenState()
    data class Success(val result: ScanValidationResult) : ScannerScreenState()
    data class Error(val result: ScanValidationResult) : ScannerScreenState()
}