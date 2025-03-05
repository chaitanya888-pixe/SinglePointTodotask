package com.sample.todohome.domain.usecases

import com.sample.todocore.domain.model.ToDoDomain
import com.sample.todocore.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TodoSearchUsecase @Inject constructor(private  val repository: TaskRepository) {
    suspend operator fun invoke(query: String): Flow<List<ToDoDomain>> {
        return repository.searchQuery(query)
    }
}