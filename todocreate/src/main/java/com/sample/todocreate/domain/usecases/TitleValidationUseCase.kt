package com.sample.todocreate.domain.usecases

import com.sample.todocreate.domain.utils.InputStatus
import com.sample.todocreate.domain.utils.TaskResult
import javax.inject.Inject


class TitleValidationUseCase @Inject constructor(){

    operator fun invoke(title: String): TaskResult {
        var titleStatus: InputStatus? = if (title.isEmpty())
            InputStatus.EMPTY
        else if(title.length<10)
            InputStatus.LENGTH_TOO_SHORT
        else
            InputStatus.VALID

        return TaskResult(title = titleStatus)
    }
}