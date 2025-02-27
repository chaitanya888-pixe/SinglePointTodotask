package com.sample.singlepointtask.domain.usecase

import com.sample.singlepointtask.domain.utils.InputStatus
import com.sample.singlepointtask.domain.utils.TaskResult
import javax.inject.Inject


class DescriptionValidationUseCase @Inject constructor(){

    operator fun invoke(description: String): TaskResult {
        var descriptionStatus: InputStatus? = null
        if (description.isEmpty())
            descriptionStatus = InputStatus.EMPTY
       else
            descriptionStatus = InputStatus.VALID

        return TaskResult(description = descriptionStatus)
    }
}