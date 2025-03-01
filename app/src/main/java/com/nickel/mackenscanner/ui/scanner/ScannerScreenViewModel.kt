package com.nickel.mackenscanner.ui.scanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nickel.mackenscanner.data.ScanValidationResult
import com.nickel.mackenscanner.domain.ValidateCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ScannerScreenViewModel @Inject constructor(
    private val validateCodeUseCase: ValidateCodeUseCase
): ViewModel() {

    companion object {
        const val RESULT_VISIBLE_MS = 7_000L
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
            validateCodeUseCase(
                qrCode = result,
                onSuccess = ::onHandleQrCodeSuccess,
                onError = ::onHandleQrCodeError
            )
        }
    }

    private fun onHandleQrCodeSuccess(result: ScanValidationResult) {
        _state.update { ScannerScreenState.Success(result) }
        viewModelScope.launch {
            delay(RESULT_VISIBLE_MS)
            _state.update { ScannerScreenState.Idle }
        }
    }

    private fun onHandleQrCodeError(result: ScanValidationResult) {
        _state.update { ScannerScreenState.Error(result) }
        viewModelScope.launch {
            delay(RESULT_VISIBLE_MS)
            _state.update { ScannerScreenState.Idle }
        }
    }
}