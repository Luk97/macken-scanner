package com.nickel.mackenscanner.ui.scanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nickel.mackenscanner.domain.HandleQrCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ScannerScreenViewModel @Inject constructor(
    private val handleQrCodeUseCase: HandleQrCodeUseCase
): ViewModel() {

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
    }

    private fun onHandleQrCodeError() {
        _state.update { ScannerScreenState.Error }
    }
}