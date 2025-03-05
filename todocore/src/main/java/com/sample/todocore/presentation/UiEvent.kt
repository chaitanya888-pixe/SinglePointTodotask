package com.sample.todocore.presentation

import com.sample.todocore.presentation.utils.Event
import com.sample.todocore.presentation.utils.UiText


sealed class UiEvent : Event() {
    data class ShowSnackBar(val uiText: UiText) : UiEvent()
    data class Message(val message: String) : UiEvent()
    data class NavigateUp(val message: String) : UiEvent()

}