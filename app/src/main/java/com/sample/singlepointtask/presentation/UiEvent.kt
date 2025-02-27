package com.sample.singlepointtask.presentation

import com.sample.singlepointtask.presentation.utils.Event
import com.sample.singlepointtask.presentation.utils.UiText


sealed class UiEvent: Event() {
    data class ShowSnackBar(val uiText: UiText): UiEvent()
    data class ShowProgressDialog(val uiText: UiText): UiEvent()
    data class Navigate(val route:String): UiEvent()
    data class Message(val message:String): UiEvent()
    data class Loading(val isLoading:Boolean): UiEvent()
    data class ShowDialog(val message:String): UiEvent()
    data class NavigateUp(val message:String): UiEvent()
    object OnLogin: UiEvent()
    object UploadImage: UiEvent()
}