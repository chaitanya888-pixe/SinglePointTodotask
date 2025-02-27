package com.sample.singlepointtask.data.di

import android.app.Application
import androidx.room.Room
import com.sample.singlepointtask.data.TaskRepositoryImpl
import com.sample.singlepointtask.data.database.TodoDAO
import com.sample.singlepointtask.data.database.TodoDataBase
import com.sample.singlepointtask.domain.repository.TaskRepository
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
        return Room.databaseBuilder(app,TodoDataBase::class.java,"todotask.db")
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