package com.sample.singlepointtask.domain.repository

import com.sample.singlepointtask.domain.model.ToDoDomain
import kotlinx.coroutines.flow.Flow

typealias RowId =Long
interface TaskRepository {
    suspend fun insertTask(task: ToDoDomain): RowId
    suspend fun getTaskList(): Flow<List<ToDoDomain>>
    suspend fun searchQuery(query: String): Flow<List<ToDoDomain>>
}