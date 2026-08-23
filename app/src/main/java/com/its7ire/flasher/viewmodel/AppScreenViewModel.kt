package com.its7ire.flasher.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class FlashViewModel : ViewModel() {
    private val _isOn = MutableStateFlow(false)
    val isOn: StateFlow<Boolean> = _isOn.asStateFlow()

    private val _flashSpeed = MutableStateFlow(0)
    val flashSpeed: StateFlow<Int> = _flashSpeed.asStateFlow()

    fun togglePower() {
        _isOn.value = !_isOn.value
        if (!_isOn.value) {
            _flashSpeed.value = 0
        }
    }

    fun setSpeed(speed: Int) {
        _flashSpeed.value = speed
        _isOn.value = true
    }

}