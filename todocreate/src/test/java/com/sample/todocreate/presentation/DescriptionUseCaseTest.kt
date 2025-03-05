package com.sample.todocreate.presentation

import com.sample.todocore.data.TaskRepositoryImpl
import com.sample.todocore.domain.model.ToDoDomain
import com.sample.todocreate.domain.usecases.DescriptionValidationUseCase
import com.sample.todocreate.domain.utils.InputStatus
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class DescriptionUseCaseTest{

    @InjectMocks
    private lateinit var descriptionUseCase: DescriptionValidationUseCase

    @Mock
    private lateinit var repository: TaskRepositoryImpl

    @Before
    fun startUP(){
        MockitoAnnotations.initMocks(this)
    }


    @Test
    fun descriptionValidateWithError()= runBlockingTest{
        var task = ToDoDomain(title = "", description = "")
        var result = task.description?.let { descriptionUseCase(it) }
        assertEquals(InputStatus.EMPTY,result?.description)
    }
    @Test
    fun taskValidateWithSuccess()= runBlockingTest {
        var task = ToDoDomain(title = "Hello", description = "Description")
        var result = task.description?.let { descriptionUseCase(it) }
        assertEquals(InputStatus.VALID, result?.description)
    }

}
