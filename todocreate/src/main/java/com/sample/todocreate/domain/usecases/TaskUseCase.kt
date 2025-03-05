package com.sample.todocreate.domain.usecases

import com.sample.todocreate.domain.utils.TaskResult
import javax.inject.Inject


class TaskUseCase @Inject constructor(
    private val repository: com.sample.todocore.domain.repository.TaskRepository
) {
    suspend fun insertTask(task: com.sample.todocore.domain.model.ToDoDomain): TaskResult {
        val result = repository.insertTask(task)
        return TaskResult(result = result)
    }

}