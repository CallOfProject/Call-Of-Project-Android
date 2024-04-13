package callofproject.dev.androidapp.data.mapper

import callofproject.dev.androidapp.domain.dto.Education
import callofproject.dev.androidapp.domain.dto.EducationDTO
import callofproject.dev.androidapp.domain.dto.EducationCreateDTO


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