package com.sample.todocreate.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.todocore.data.mapper.toToDoDomain
import com.sample.todocore.presentation.StandardTextFieldState
import com.sample.todocore.presentation.UiEvent
import com.sample.todocore.presentation.events.FieldStatus
import com.sample.todocreate.domain.usecases.DescriptionValidationUseCase
import com.sample.todocreate.domain.usecases.TaskUseCase
import com.sample.todocreate.domain.usecases.TitleValidationUseCase
import com.sample.todocreate.domain.utils.InputStatus
import com.sample.todocreate.domain.utils.TaskResult


import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class TodoViewModel @Inject constructor(
    private var titleUseCase: TitleValidationUseCase,
    private var descriptionUseCase: DescriptionValidationUseCase,
    private var taskUseCase: TaskUseCase
):ViewModel(){

    private val _titleState = mutableStateOf(StandardTextFieldState())
    val titleState = _titleState

    private val _descState = mutableStateOf(StandardTextFieldState())
    val descState = _descState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _dialogState = mutableStateOf(false)
    val dialogState = _dialogState

    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery


    fun onEvent(event: TaskEvent){
        when(event){

            is TaskEvent.EnteredTitle ->{
                _titleState.value = titleState.value.copy(
                    text = event.title
                )
            }
            is TaskEvent.EnteredDescription ->{
                _descState.value = descState.value.copy(
                    text = event.description
                )
            }
            is TaskEvent.DialogueEvent ->{
                _dialogState.value = event.isDismiss
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.NavigateUp("Finished"))
                }

            }
            is TaskEvent.AddTask ->{
                viewModelScope.launch {


                    var titleResult = TaskResult()
                    try{
                        titleResult = titleUseCase(title = _titleState.value.text)
                    } catch (e:IllegalArgumentException){
                        _eventFlow.emit(UiEvent.NavigateUp("Exception"))
                    }
                    var titleStatus: FieldStatus = when (titleResult.title) {
                        InputStatus.EMPTY -> FieldStatus.FieldEmpty
                        InputStatus.LENGTH_TOO_SHORT -> FieldStatus.InputTooShort
                        InputStatus.VALID -> FieldStatus.FieldFilled
                        null -> TODO()
                    }
                    _titleState.value = titleState.value.copy(
                        error = titleStatus
                    )
                    var descriptionResult = descriptionUseCase(description = _descState.value.text)
                    var descriptionStatus:FieldStatus = when (descriptionResult.description) {
                        InputStatus.EMPTY -> FieldStatus.FieldEmpty
                        InputStatus.LENGTH_TOO_SHORT -> FieldStatus.InputTooShort
                        InputStatus.VALID -> FieldStatus.FieldFilled
                        null -> TODO()
                    }
                    _descState.value = descState.value.copy(
                        error = descriptionStatus
                    )
                    if(titleResult.title ==InputStatus.VALID && descriptionResult.description == InputStatus.VALID)
                    {
                        val task = com.sample.todocore.domain.model.ToDoUi(
                            title = _titleState.value.text, description = _descState
                                .value.text
                        )
                        val taskResult = taskUseCase.insertTask(task = task.toToDoDomain())
                        taskResult.result?.let {
                            if(it>0){
                                _dialogState.value = true
                            }
                        }
                    }

                }
            }
        }

    }

    suspend fun insertTask(task: com.sample.todocore.domain.model.ToDoUi): TaskResult {
     return  taskUseCase.insertTask(task.toToDoDomain())
    }


}
