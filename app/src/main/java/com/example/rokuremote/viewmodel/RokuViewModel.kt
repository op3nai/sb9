package com.example.rokuremote.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import com.example.rokuremote.model.CustomButton

class RokuViewModel : ViewModel() {
    private val _customButtons = mutableStateListOf<CustomButton>()
    val customButtons: List<CustomButton> = _customButtons

    fun addCustomButton(name: String, commands: List<String>) {
        _customButtons.add(CustomButton(name, commands))
    }

    fun processVoiceCommand(command: String) {
        // TODO: Implement voice command processing
        println("Processing voice command: $command")
    }
}