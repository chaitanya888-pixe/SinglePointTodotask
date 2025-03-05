package com.sample.todocore.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDAO {
    @Insert
    suspend fun insertTask(todo: Todo):Long

    @Query("SELECT * FROM TASKTODO ORDER BY id DESC ")
    fun getTaskList(): Flow<List<Todo>>

    @Query("SELECT * FROM TASKTODO WHERE title LIKE '%' || :searchQuery || '%'")
    fun search(searchQuery: String): Flow<List<Todo>>
}