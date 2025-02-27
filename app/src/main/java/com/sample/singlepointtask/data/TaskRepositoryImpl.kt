package com.sample.singlepointtask.data

import com.sample.singlepointtask.data.database.TodoDAO
import com.sample.singlepointtask.data.mapper.toToDoDomain
import com.sample.singlepointtask.data.mapper.toTodo
import com.sample.singlepointtask.domain.model.ToDoDomain
import com.sample.singlepointtask.domain.repository.RowId
import com.sample.singlepointtask.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class TaskRepositoryImpl(
    private val taskDao: TodoDAO
): TaskRepository {
    override suspend fun insertTask(task: ToDoDomain): RowId {
        return taskDao.insertTask(task.toTodo())
    }

    override suspend fun getTaskList(): Flow<List<ToDoDomain>> {
        return taskDao.getTaskList().map { todoList ->
            todoList.map { it.toToDoDomain() }
        }

    }

    override suspend fun searchQuery(query: String): Flow<List<ToDoDomain>> {
        return  taskDao.search(query).map { todoList ->
            todoList.map { it.toToDoDomain() }
        }
    }

}
