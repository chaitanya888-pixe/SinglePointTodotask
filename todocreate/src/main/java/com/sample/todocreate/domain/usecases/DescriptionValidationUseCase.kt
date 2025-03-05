package com.sample.todocreate.domain.usecases


import com.sample.todocreate.domain.utils.InputStatus
import com.sample.todocreate.domain.utils.TaskResult
import javax.inject.Inject


class DescriptionValidationUseCase @Inject constructor(){

    operator fun invoke(description: String): TaskResult {
        var descriptionStatus: InputStatus?
        if (description.isEmpty())
            descriptionStatus = InputStatus.EMPTY
       else
            descriptionStatus = InputStatus.VALID

        return TaskResult(description = descriptionStatus)
    }
}