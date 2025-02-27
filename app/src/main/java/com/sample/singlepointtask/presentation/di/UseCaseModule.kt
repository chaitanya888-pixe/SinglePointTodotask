package com.sample.singlepointtask.presentation.di

import com.sample.singlepointtask.domain.repository.TaskRepository
import com.sample.singlepointtask.domain.usecase.TodoGetListUseCase
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    fun getListUseCase(repository: TaskRepository):TodoGetListUseCase{
        return TodoGetListUseCase(repository)
    }
}