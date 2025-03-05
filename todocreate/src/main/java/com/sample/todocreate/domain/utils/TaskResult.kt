package com.sample.todocreate.domain.utils

import com.sample.todocore.domain.repository.RowId


data class TaskResult(
    var isValid:Boolean = false,
    var title: InputStatus?= null,
    var description: InputStatus? = null,
    val result: RowId?=null
)