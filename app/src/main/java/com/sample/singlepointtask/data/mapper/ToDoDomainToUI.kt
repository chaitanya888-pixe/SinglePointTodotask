package com.sample.singlepointtask.data.mapper

import com.sample.singlepointtask.domain.model.ToDoDomain
import com.sample.singlepointtask.domain.model.ToDoUi


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