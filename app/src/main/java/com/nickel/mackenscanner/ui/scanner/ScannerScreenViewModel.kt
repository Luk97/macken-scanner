package com.nickel.mackenscanner.ui.scanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nickel.mackenscanner.network.MackenRepository
import com.nickel.mackenscanner.ui.scanner.ScannerScreenState.ScannerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ScannerScreenViewModel @Inject constructor(
    private val mackenRepository: MackenRepository
): ViewModel() {

    private val _state = MutableStateFlow(ScannerScreenState())
    val state = _state.asStateFlow()

    fun onScannerStarted() {
        _state.update {
            it.copy(scannerState = ScannerState.Scanning)
        }
    }

    fun onScannerSucceeded(result: String) {
        _state.update { it.copy(scannerState = ScannerState.Idle) }
        viewModelScope.launch {
            val response = mackenRepository.sendData(result)
            if (response.success) {
                _state.update {
                    it.copy(
                        scannerState = ScannerState.Success(
                            result = response.message
                        )
                    )
                }
            } else {
                _state.update { it.copy(scannerState = ScannerState.Error) }
            }
        }
    }

    fun onScannerCanceled() {
        _state.update {
            it.copy(scannerState = ScannerState.Idle)
        }
    }
}