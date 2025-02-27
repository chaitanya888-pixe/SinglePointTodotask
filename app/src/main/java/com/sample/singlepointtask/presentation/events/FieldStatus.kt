package com.sample.singlepointtask.presentation.events

sealed class FieldStatus : Error(){
    data object FieldEmpty: FieldStatus()
    data object FieldFilled: FieldStatus()
    data object InputTooShort : FieldStatus()
}