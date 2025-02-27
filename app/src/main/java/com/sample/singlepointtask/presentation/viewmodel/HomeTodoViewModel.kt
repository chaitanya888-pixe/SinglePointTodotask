package com.sample.singlepointtask.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.singlepointtask.domain.model.ToDoDomain
import com.sample.singlepointtask.domain.usecase.TodoGetListUseCase
import com.sample.singlepointtask.domain.usecase.TodoSearchUsecase
import com.sample.singlepointtask.presentation.UiEvent
import com.sample.singlepointtask.presentation.events.SearchEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeTodoViewModel @Inject constructor(
    private val
    todoListUseCase: TodoGetListUseCase,
    private val
    todoSearchUseCase: TodoSearchUsecase
) : ViewModel() {
    private var _mutableList= mutableStateOf<List<ToDoDomain>>(emptyList())
val mutableList=_mutableList
    private var _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow=_eventFlow.asSharedFlow()
    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    private val _focusState = mutableStateOf(true)
    val focusState = _focusState

    private val _topBarState = mutableStateOf(false)
    val topBarState = _topBarState

    fun getList() {
        viewModelScope.launch {
            todoListUseCase.invoke().flowOn(Dispatchers.IO).collect{
                mutableList.value=it
            }
        }
    }
    fun onSearchEvent(event: SearchEvent){

        when(event){
            is SearchEvent.TopSearchSelected ->{
                _topBarState.value = event.selected
            }
            is SearchEvent.OnSearchQuery ->{
                searchQuery.value = event.query
            }
            is SearchEvent.OnSearchStart ->{
                viewModelScope.launch {
                    todoSearchUseCase(event.query).flowOn(Dispatchers.IO).collect{
                        mutableList.value = it
                    }
                }
            }
            is SearchEvent.OnFocusChange ->{
                focusState.value = event.focus
            }

            is SearchEvent.OnClearPressed ->{
                _searchQuery.value=""
                _topBarState.value = false
                getList()
            }

        }
    }


}