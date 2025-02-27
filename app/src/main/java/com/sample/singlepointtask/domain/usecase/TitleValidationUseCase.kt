package com.sample.singlepointtask.domain.usecase

import com.sample.singlepointtask.domain.utils.InputStatus
import com.sample.singlepointtask.domain.utils.TaskResult
import javax.inject.Inject


class TitleValidationUseCase @Inject constructor(){

    operator fun invoke(title: String): TaskResult {
        if (title.contains("Error")) {
            throw IllegalArgumentException("Title cannot contain 'Error' text.")
        }
        var titleStatus: InputStatus? = null
        if (title.isEmpty())
            titleStatus = InputStatus.EMPTY
        else if(title.length<10)
            titleStatus = InputStatus.LENGTH_TOO_SHORT
        else
            titleStatus = InputStatus.VALID

        return TaskResult(title = titleStatus)
    }
}