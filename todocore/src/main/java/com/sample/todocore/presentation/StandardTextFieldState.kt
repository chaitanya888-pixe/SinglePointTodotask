package com.sample.todocore.presentation


import com.sample.todocore.presentation.utils.Error


data class StandardTextFieldState(
    var text:String = "",
    val error: Error? = null

)