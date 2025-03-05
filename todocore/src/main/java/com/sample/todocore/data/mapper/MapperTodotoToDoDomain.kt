package com.sample.todocore.data.mapper

import com.sample.todocore.data.database.Todo
import com.sample.todocore.domain.model.ToDoDomain


fun Todo.toToDoDomain(): ToDoDomain {
    return ToDoDomain(
        id =id,
        title = title,
        description = description
    )
}

fun ToDoDomain.toTodo(): Todo {
    return Todo(
        id = id,
        title = title,
        description = description
    )
}