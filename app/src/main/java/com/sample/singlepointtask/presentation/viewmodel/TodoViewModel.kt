package com.sample.singlepointtask.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.singlepointtask.data.mapper.toToDoDomain
import com.sample.singlepointtask.domain.model.ToDoUi
import com.sample.singlepointtask.domain.usecase.DescriptionValidationUseCase
import com.sample.singlepointtask.domain.usecase.TaskUseCase
import com.sample.singlepointtask.domain.usecase.TitleValidationUseCase
import com.sample.singlepointtask.domain.utils.InputStatus
import com.sample.singlepointtask.domain.utils.TaskResult
import com.sample.singlepointtask.presentation.StandardTextFieldState
import com.sample.singlepointtask.presentation.UiEvent
import com.sample.singlepointtask.presentation.events.FieldStatus
import com.sample.singlepointtask.presentation.events.TaskEvent

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
                        val task = ToDoUi(
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

    suspend fun insertTask(task: ToDoUi): TaskResult {
     return  taskUseCase.insertTask(task.toToDoDomain())
    }


}
