package com.sample.todohome.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.todocore.domain.model.ToDoDomain
import com.sample.todocore.presentation.UiEvent
import com.sample.todohome.domain.usecases.TodoGetListUseCase
import com.sample.todohome.domain.usecases.TodoSearchUsecase

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
    private var _mutableList = mutableStateOf<List<ToDoDomain>>(emptyList())
    val mutableList = _mutableList
    private var _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()
    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    private val _focusState = mutableStateOf(true)
    val focusState = _focusState

    private val _topBarState = mutableStateOf(false)
    val topBarState = _topBarState

    private val _loadingState = mutableStateOf(false)
    val loadingState: State<Boolean> = _loadingState

    fun getList() {
        viewModelScope.launch {
            todoListUseCase.invoke().flowOn(Dispatchers.IO).collect {
                mutableList.value = it
            }
        }
    }

    fun onSearchEvent(event: SearchEvent) {

        when (event) {
            is SearchEvent.TopSearchSelected -> {
                _topBarState.value = event.selected
            }

            is SearchEvent.OnSearchQuery -> {
                _loadingState.value = true
                searchQuery.value = event.query
            }

            is SearchEvent.OnSearchStart -> {
                viewModelScope.launch {
                    todoSearchUseCase(event.query).flowOn(Dispatchers.IO).collect {
                        mutableList.value = it
                        _loadingState.value = false

                    }
                }
            }

            is SearchEvent.OnFocusChange -> {
                focusState.value = event.focus
            }

            is SearchEvent.OnClearPressed -> {
                _searchQuery.value = ""
                _topBarState.value = false
                _loadingState.value = false

                getList()
            }

        }
    }


}