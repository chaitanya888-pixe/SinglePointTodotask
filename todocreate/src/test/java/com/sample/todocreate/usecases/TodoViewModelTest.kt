package com.sample.todocreate.usecases

import com.sample.todocore.data.TaskRepositoryImpl
import com.sample.todocore.domain.model.ToDoDomain
import com.sample.todocore.presentation.events.FieldStatus
import com.sample.todocreate.domain.usecases.DescriptionValidationUseCase
import com.sample.todocreate.domain.usecases.TaskUseCase
import com.sample.todocreate.domain.usecases.TitleValidationUseCase
import com.sample.todocreate.domain.utils.InputStatus
import com.sample.todocreate.domain.utils.TaskResult
import com.sample.todocreate.presentation.TaskEvent
import com.sample.todocreate.presentation.TodoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


class TodoViewModelTest {
    @Mock
    private lateinit var repository: TaskRepositoryImpl
    @Mock
    private lateinit var titleUseCase: TitleValidationUseCase
    @Mock
    private lateinit var descriptionUseCase: DescriptionValidationUseCase

    @InjectMocks
    private lateinit var taskUseCase: TaskUseCase

    private lateinit var todoViewModel: TodoViewModel
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var closeable: AutoCloseable

    @Before
    fun setUp() {
        closeable = MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

       todoViewModel = TodoViewModel(titleUseCase, descriptionUseCase, taskUseCase)
    }

    @Test
    fun verifyTitleUpdatesState() = runTest {
        val title = "New Task Title"
        todoViewModel.onEvent(TaskEvent.EnteredTitle(title))
        assertEquals(title, todoViewModel.titleState.value.text)
    }


    @Test
    fun testAddTaskEmptyTitleErrorState() = runTest {
        val invalidTitle = ""
        val invalidResult = TaskResult(isValid = false, title = InputStatus.EMPTY)

        `when`(titleUseCase.invoke(invalidTitle)).thenReturn(invalidResult)

        todoViewModel.onEvent(TaskEvent.EnteredTitle(invalidTitle))
        todoViewModel.onEvent(TaskEvent.AddTask)
        advanceUntilIdle()

        assertEquals(FieldStatus.FieldEmpty, todoViewModel.titleState.value.error)
        assertFalse(todoViewModel.dialogState.value)
    }

    @Test
    fun insertTask() = runTest {
        val task = ToDoDomain(title = "Title", description = "Description")
        val taskResult = TaskResult(result = 1L)
        val id: Long = 1L
        `when`(repository.insertTask(task)).thenReturn(id)
        val result = taskUseCase.insertTask(task)
        assertEquals(taskResult, result)
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
        closeable.close()
    }

}