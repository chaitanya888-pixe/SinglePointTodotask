package com.sample.singlepointtask.presentation.events

sealed class TaskEvent {
    data class EnteredTitle(val title:String): TaskEvent()
    data class EnteredDescription(val description:String): TaskEvent()
    data class DialogueEvent(val isDismiss:Boolean): TaskEvent()
    data object AddTask: TaskEvent()
}