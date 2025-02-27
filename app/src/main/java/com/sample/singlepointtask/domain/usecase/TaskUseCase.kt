package com.sample.singlepointtask.domain.usecase

import com.sample.singlepointtask.domain.model.ToDoDomain
import com.sample.singlepointtask.domain.repository.TaskRepository
import com.sample.singlepointtask.domain.utils.TaskResult
import javax.inject.Inject



class TaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend fun insertTask(task: ToDoDomain): TaskResult {
        val result = repository.insertTask(task)
        return TaskResult(result = result)
    }

}