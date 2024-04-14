package callofproject.dev.androidapp.data.mapper

import callofproject.dev.androidapp.domain.dto.user_profile.course.Course
import callofproject.dev.androidapp.domain.dto.user_profile.course.CourseCreateDTO
import callofproject.dev.androidapp.domain.dto.user_profile.course.CourseDTO
import callofproject.dev.androidapp.domain.dto.user_profile.course.CourseUpdateDTO
import callofproject.dev.androidapp.domain.dto.user_profile.experience.Experience
import callofproject.dev.androidapp.domain.dto.user_profile.experience.ExperienceCreateDTO
import callofproject.dev.androidapp.domain.dto.user_profile.experience.ExperienceDTO
import callofproject.dev.androidapp.domain.dto.user_profile.experience.ExperienceUpdateDTO

fun Course.toCourseDTO(): CourseDTO {
    return CourseDTO(
        courseId = courseId,
        organization = courseOrganization.courseOrganizationName,
        courseName = courseName,
        startDate = startDate,
        finishDate = finishDate,
        isContinue = `continue`,
        description = description
    )
}


fun CourseDTO.toCourseCreateDTO(userId: String): CourseCreateDTO {
    return CourseCreateDTO(
        userId = userId,
        organizator = organization,
        courseName = courseName,
        startDate = startDate,
        finishDate = finishDate,
        isContinue = isContinue,
        description = description

    )
}

fun CourseDTO.toCourseUpdateDTO(userId: String): CourseUpdateDTO {
    return CourseUpdateDTO(
        userId = userId,
        courseId = courseId,
        organizator = organization,
        courseName = courseName,
        startDate = startDate,
        finishDate = finishDate,
        isContinue = isContinue,
        description = description
    )
}