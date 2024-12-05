package com.nickel.mackenscanner.ui.scanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nickel.mackenscanner.domain.HandleQrCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ScannerScreenViewModel @Inject constructor(
    private val handleQrCodeUseCase: HandleQrCodeUseCase
): ViewModel() {

    companion object {
        const val RESULT_VISIBLE_MS = 10_000L
    }

    private val _state = MutableStateFlow<ScannerScreenState>(ScannerScreenState.Idle)
    val state = _state.asStateFlow()

    fun onScannerStarted() {
        _state.update { ScannerScreenState.Scanning }
    }

    fun onScannerCanceled() {
        _state.update { ScannerScreenState.Idle }
    }

    fun onScannerSucceeded(result: String) {
        _state.update { ScannerScreenState.Loading }
        viewModelScope.launch {
            handleQrCodeUseCase(
                qrCode = result,
                onSuccess = ::onHandleQrCodeSuccess,
                onError = ::onHandleQrCodeError
            )
        }
    }

    private fun onHandleQrCodeSuccess() {
        _state.update { ScannerScreenState.Success }
        viewModelScope.launch {
            delay(RESULT_VISIBLE_MS)
            _state.update { ScannerScreenState.Idle }
        }
    }

    private fun onHandleQrCodeError(message: String) {
        _state.update { ScannerScreenState.Error(message) }
        viewModelScope.launch {
            delay(RESULT_VISIBLE_MS)
            _state.update { ScannerScreenState.Idle }
        }
    }
}