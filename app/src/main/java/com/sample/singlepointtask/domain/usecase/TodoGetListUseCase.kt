package com.sample.singlepointtask.domain.usecase

import com.sample.singlepointtask.domain.model.ToDoDomain
import com.sample.singlepointtask.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TodoGetListUseCase @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke(): Flow<List<ToDoDomain>> {
        return repository.getTaskList()
    }
}