package com.sample.todocore.presentation.events
import com.sample.todocore.presentation.utils.Error

sealed class FieldStatus : Error(){
    data object FieldEmpty: FieldStatus()
    data object FieldFilled: FieldStatus()
    data object InputTooShort : FieldStatus()
}