package com.sample.todocore.data.di

import android.app.Application
import androidx.room.Room
import com.sample.todocore.data.TaskRepositoryImpl
import com.sample.todocore.data.database.TodoDAO
import com.sample.todocore.data.database.TodoDataBase
import com.sample.todocore.domain.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideTodoDataBase(app: Application): TodoDataBase {
        return Room.databaseBuilder(app, TodoDataBase::class.java,"todotask.db")
            .build()
    }
    @Provides
    @Singleton
    fun providesTodoDAO(todoDataBase: TodoDataBase): TodoDAO {
        return todoDataBase.todoDAO
    }

    @Provides
    @Singleton
    fun providesRepositoryImpl(taskDao: TodoDAO): TaskRepository {
        return TaskRepositoryImpl(taskDao)
    }
}