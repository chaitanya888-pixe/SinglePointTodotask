package com.sample.singlepointtask.domain.usecase

import com.sample.singlepointtask.data.TaskRepositoryImpl
import com.sample.singlepointtask.domain.model.ToDoDomain
import com.sample.singlepointtask.domain.utils.TaskResult
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class TodoUseCaseTest{

    @InjectMocks
    private lateinit var taskUseCase: TaskUseCase

    @Mock
    private lateinit var repository: TaskRepositoryImpl

    @Before
    fun startUP(){
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun insertTask() = runBlockingTest {
        val task = ToDoDomain(title = "Title", description = "Description")
        val taskResult = TaskResult(result = 1L)
        val id:Long = 1L
        `when`(repository.insertTask(task)).thenReturn(id)
        val result = taskUseCase.insertTask(task)
        assertEquals(taskResult,result)
    }


}
