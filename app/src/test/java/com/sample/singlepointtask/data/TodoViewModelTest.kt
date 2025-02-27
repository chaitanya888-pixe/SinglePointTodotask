package com.sample.singlepointtask.data

import com.sample.singlepointtask.data.mapper.toToDoDomain
import com.sample.singlepointtask.domain.model.ToDoUi
import com.sample.singlepointtask.domain.usecase.DescriptionValidationUseCase
import com.sample.singlepointtask.domain.usecase.TaskUseCase
import com.sample.singlepointtask.domain.usecase.TitleValidationUseCase
import com.sample.singlepointtask.domain.utils.TaskResult
import com.sample.singlepointtask.presentation.viewmodel.TodoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


class TodoViewModelTest {

    @Mock
    private lateinit var taskRepository: TaskRepositoryImpl

    @Mock
    private lateinit var titleUseCase: TitleValidationUseCase

    @Mock
    private lateinit var descriptionUseCase: DescriptionValidationUseCase

    @Mock
    private lateinit var taskUseCase: TaskUseCase

    @InjectMocks
    private lateinit var todoViewModel: TodoViewModel

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(TestCoroutineDispatcher())
    }

    @Test
    fun testInsertTaskSuccess()= runBlockingTest{
        val task = ToDoUi(title = "Title", description = "Description")
        val taskResult = TaskResult(result = 1L)
        `when`(taskUseCase.insertTask(task.toToDoDomain())).thenReturn(taskResult)
        val result = todoViewModel.insertTask(task)
//        Assert.assertEquals(taskResult, result)
    }
    @Test
    fun testInsertTaskError()= runBlockingTest{
        val task = ToDoUi(title = "Error", description = "Description")
        val taskResult = TaskResult(
            isValid = false,
            title = null
        )
        `when`(taskUseCase.insertTask(task.toToDoDomain())).thenReturn(taskResult)

    }



    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}