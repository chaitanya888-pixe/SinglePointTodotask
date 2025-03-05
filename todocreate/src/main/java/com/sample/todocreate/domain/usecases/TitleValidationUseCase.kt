package com.sample.todocreate.domain.usecases

import com.sample.todocreate.domain.utils.InputStatus
import com.sample.todocreate.domain.utils.TaskResult
import javax.inject.Inject


class TitleValidationUseCase @Inject constructor(){

    operator fun invoke(title: String): TaskResult {
        if (title.contains("Error")) {
            throw IllegalArgumentException("Title cannot contain 'Error' text.")
        }
        var titleStatus: InputStatus?
        if (title.isEmpty())
            titleStatus = InputStatus.EMPTY
        else if(title.length<10)
            titleStatus = InputStatus.LENGTH_TOO_SHORT
        else
            titleStatus = InputStatus.VALID

        return TaskResult(title = titleStatus)
    }
}