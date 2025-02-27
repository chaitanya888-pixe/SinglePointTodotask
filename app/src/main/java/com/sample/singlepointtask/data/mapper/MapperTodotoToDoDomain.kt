package com.sample.singlepointtask.data.mapper

import com.sample.singlepointtask.data.database.Todo
import com.sample.singlepointtask.domain.model.ToDoDomain


fun Todo.toToDoDomain(): ToDoDomain {
    return ToDoDomain(
        id =id,
        title = title,
        description = description
    )
}

fun ToDoDomain.toTodo():Todo{
    return Todo(
        id = id ?: 0,
        title = title,
        description = description
    )
}