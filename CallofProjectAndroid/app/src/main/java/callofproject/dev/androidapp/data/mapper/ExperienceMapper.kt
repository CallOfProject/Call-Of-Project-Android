package callofproject.dev.androidapp.data.mapper

import callofproject.dev.androidapp.domain.dto.user_profile.experience.Experience
import callofproject.dev.androidapp.domain.dto.user_profile.experience.ExperienceCreateDTO
import callofproject.dev.androidapp.domain.dto.user_profile.experience.ExperienceDTO
import callofproject.dev.androidapp.domain.dto.user_profile.experience.ExperienceUpdateDTO

fun Experience.toExperienceDTO(): ExperienceDTO {
    return ExperienceDTO(
        experienceId = experienceId,
        companyName = companyName,
        description = description,
        companyWebsiteLink = companyWebsiteLink,
        startDate = startDate,
        finishDate = finishDate,
        position = jobDefinition,
        isContinue = `continue`
    )
}


fun ExperienceDTO.toExperienceCreateDTO(userId: String): ExperienceCreateDTO {
    return ExperienceCreateDTO(
        userId = userId,
        companyName = companyName,
        description = description,
        companyWebSite = companyWebsiteLink,
        jobDefinition = position,
        startDate = startDate,
        finishDate = finishDate,
        isContinue = isContinue
    )
}

fun ExperienceDTO.toExperienceUpdateDTO(userId: String): ExperienceUpdateDTO {
    return ExperienceUpdateDTO(
        userId = userId,
        experienceId = experienceId,
        companyName = companyName,
        description = description,
        companyWebSite = companyWebsiteLink,
        jobDefinition = position,
        startDate = startDate,
        finishDate = finishDate,
        isContinue = isContinue
    )
}