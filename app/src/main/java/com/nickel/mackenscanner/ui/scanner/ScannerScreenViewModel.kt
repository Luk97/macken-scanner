package com.nickel.mackenscanner.ui.scanner

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import com.nickel.mackenscanner.ui.scanner.ScannerScreenState.ScannerState

@HiltViewModel
class ScannerScreenViewModel @Inject constructor(): ViewModel() {

    private val _state = MutableStateFlow(ScannerScreenState())
    val state = _state.asStateFlow()

    fun onScannerStarted() {
        _state.update {
            it.copy(scannerState = ScannerState.Scanning)
        }
    }

    fun onScannerSucceeded(result: String) {
        _state.update {
            it.copy(scannerState = ScannerState.Idle)
        }
    }

    fun onScannerCanceled() {
        _state.update {
            it.copy(scannerState = ScannerState.Idle)
        }
    }
}