package com.sample.todocore.data.mapper

import com.sample.todocore.domain.model.ToDoDomain
import com.sample.todocore.domain.model.ToDoUi


fun ToDoDomain.ToDoUi(): ToDoUi {
        return ToDoUi(
            id = id,
            title = title,
            description= description
        )
}

fun ToDoUi.toToDoDomain():ToDoDomain{
    return ToDoDomain(
        id = id,
        title = title,
        description = description
    )
}