package callofproject.dev.androidapp.data.mapper

import callofproject.dev.androidapp.domain.dto.user_profile.education.Education
import callofproject.dev.androidapp.domain.dto.user_profile.education.EducationDTO
import callofproject.dev.androidapp.domain.dto.user_profile.education.EducationCreateDTO
import callofproject.dev.androidapp.domain.dto.user_profile.education.EducationUpdateDTO


fun Education.toEducationDTO(): EducationDTO {
    return EducationDTO(
        educationId = educationId,
        schoolName = schoolName,
        department = department,
        description = description,
        startDate = startDate,
        finishDate = finishDate,
        isContinue = `continue`,
        gpa = gpa
    )
}


fun EducationDTO.toEducationUpsertDTO(userId: String): EducationCreateDTO {
    return EducationCreateDTO(
        userId = userId,
        schoolName = schoolName,
        department = department,
        description = description,
        startDate = startDate,
        finishDate = finishDate,
        isContinue = isContinue,
        gpa = gpa
    )
}


fun EducationDTO.toEducationUpdateDTO(userId: String): EducationUpdateDTO {
    return EducationUpdateDTO(
        userId = userId,
        educationId = educationId,
        schoolName = schoolName,
        department = department,
        description = description,
        startDate = startDate,
        finishDate = finishDate,
        isContinue = isContinue,
        gpa = gpa
    )
}