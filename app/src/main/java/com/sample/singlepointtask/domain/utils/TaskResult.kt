package com.sample.singlepointtask.domain.utils

import com.sample.singlepointtask.domain.repository.RowId


data class TaskResult(
    var isValid:Boolean = false,
    var title: InputStatus?= null,
    var description: InputStatus? = null,
    val result: RowId?=null
)