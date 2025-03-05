package com.sample.todohome.presentation


import com.sample.todocore.domain.model.ToDoDomain
import com.sample.todohome.domain.usecases.TodoGetListUseCase
import com.sample.todohome.domain.usecases.TodoSearchUsecase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi

class HomeTodoViewModelTest{
    @Mock
    private lateinit var todoGetListUseCase: TodoGetListUseCase

    @Mock
    private lateinit var searchUseCase: TodoSearchUsecase

    @InjectMocks
    private lateinit var homeTodoViewModel: HomeTodoViewModel

    private val testDispatcher = TestCoroutineDispatcher()
    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(TestCoroutineDispatcher())
    }
    @Test
    fun testGetTaskList()= runBlockingTest {
        val taskList = listOf(
            ToDoDomain(id = 1, title = "title 1", description = "Test Task"),
            ToDoDomain(id = 2, title = "title 2", description = "Test Task"),
            ToDoDomain(id = 3, title = "title 3", description = "Test Task")
        )
        `when` (todoGetListUseCase()).thenReturn(flowOf(taskList))
        var list = homeTodoViewModel.getList()
        verify(todoGetListUseCase).invoke()
    }
}