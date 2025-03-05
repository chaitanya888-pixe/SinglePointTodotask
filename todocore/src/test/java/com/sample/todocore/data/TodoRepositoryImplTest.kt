package com.sample.todocore.data

import com.sample.todocore.data.database.TodoDAO
import com.sample.todocore.data.mapper.toTodo
import com.sample.todocore.domain.model.ToDoDomain
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class TodoRepositoryImplTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    @Mock
    private lateinit var taskDao: TodoDAO

    @InjectMocks
    private lateinit var repository: TaskRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }
    @Test
    fun testAddTask()=runTest(testDispatcher) {
        val task = ToDoDomain(title = "Title", description = "Description")
        val id: Long = 1L
        `when`(taskDao.insertTask(task.toTodo())).thenReturn(id)
        val taskId = repository.insertTask(task)
        assertEquals(id, taskId)
    }
    @Test(expected = Exception::class)
    fun testAddTaskThrowException() = runTest(testDispatcher) {
        val task = ToDoDomain(id = 1, title = "Error", description = "Test Task")
        val exceptionMessage = "Error inserting task"
        val exception = Exception(exceptionMessage)
        `when`(taskDao.insertTask(task.toTodo())).thenThrow(exception)
        try {
            repository.insertTask(task)
        } catch (e: Exception) {
            assertEquals(exceptionMessage, e.message)
        }
    }

    @Test
    fun testGetTaskList() = runTest(testDispatcher) {
        val taskList = listOf(
            ToDoDomain(id = 1, title = "Title 1", description = "Test Task"),
            ToDoDomain(id = 2, title = "Title 2", description = "Test Task"),
            ToDoDomain(id = 3, title = "Title 3", description = "Test Task")
        )
        val flowList = flowOf(taskList.map { it.toTodo() })
        `when`(taskDao.getTaskList()).thenReturn(flowList)
        val result = repository.getTaskList().first()
        assertEquals(taskList, result.map { it.toTodo() })
    }
}