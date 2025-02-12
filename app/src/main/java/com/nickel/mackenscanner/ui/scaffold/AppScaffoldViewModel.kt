package com.nickel.mackenscanner.ui.scaffold

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
internal class AppScaffoldViewModel @Inject constructor(): ViewModel() {

    private val _state = MutableStateFlow(AppScaffoldState())
    val state = _state.asStateFlow()

    fun onInfoClicked() {

    }
}