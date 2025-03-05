package com.sample.todocore.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "TaskTodo")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0,
    val title :String?=null,
    val description:String?= null
)