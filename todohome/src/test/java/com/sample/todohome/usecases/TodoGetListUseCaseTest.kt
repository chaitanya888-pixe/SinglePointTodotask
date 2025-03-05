package com.sample.todohome.usecases


import com.sample.todocore.domain.model.ToDoDomain
import com.sample.todocore.domain.repository.TaskRepository
import com.sample.todohome.domain.usecases.TodoGetListUseCase
import com.sample.todohome.domain.usecases.TodoSearchUsecase

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class TodoGetListUseCaseTest {

    @InjectMocks
    private lateinit var todoGetListUseCase: TodoGetListUseCase

    @Mock
    private lateinit var toDoSearchUseCaseTest: TodoSearchUsecase

    @Mock
    private lateinit var repository: TaskRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun startUP(){
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(TestCoroutineDispatcher())
    }
    @Test
    fun `test invoke`() = runBlocking {

        val todoList = listOf(
            ToDoDomain(1, "Task 1", "Description 1"),
            ToDoDomain(2, "Task 2", "Description 2")
        )
        `when`(repository.getTaskList()).thenReturn(flowOf(todoList))

        val resultFlow = todoGetListUseCase.invoke()

        val resultList = resultFlow.first()

        verify(repository).getTaskList()

        assertEquals(todoList, resultList)
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}