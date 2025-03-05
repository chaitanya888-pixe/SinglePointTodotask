package com.sample.todocreate.presentation

import com.sample.todocore.data.TaskRepositoryImpl
import com.sample.todocore.domain.model.ToDoDomain
import com.sample.todocreate.domain.usecases.TitleValidationUseCase
import com.sample.todocreate.domain.utils.InputStatus
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class TitleUseCaseTest{

    @InjectMocks
    private lateinit var taskUseCase: TitleValidationUseCase

    @Mock
    private lateinit var repository: TaskRepositoryImpl

    @Before
    fun startUP(){
        MockitoAnnotations.initMocks(this)
    }


    @Test
    fun titleValidateWithError()= runBlockingTest{
        var task = ToDoDomain(title = "", description = "Description")
        var result = task.title?.let { taskUseCase(it) }
        assertEquals(InputStatus.EMPTY,result?.title)
        assertNotEquals(InputStatus.VALID, result?.title)
    }
    @Test
    fun titleValidateWithShortText()= runBlockingTest {
        var task = ToDoDomain(title = "Hello", description = "Description")
        var result = task.title?.let { taskUseCase(it) }
        assertEquals(InputStatus.LENGTH_TOO_SHORT, result?.title)
        assertNotEquals(InputStatus.VALID, result?.title)
    }

    @Test
    fun titleValidateWithSuccess()= runBlockingTest {
        var task = ToDoDomain(title = "Hello chaitanya", description = "Description")
        var result = task.title?.let { taskUseCase(it) }
        assertNotEquals(InputStatus.EMPTY, result?.title)
        assertNotEquals(InputStatus.LENGTH_TOO_SHORT, result?.title)
        assertEquals(InputStatus.VALID, result?.title)
    }

}
