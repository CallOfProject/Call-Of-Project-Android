package callofproject.dev.androidapp.data.validator

import android.content.Context
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.data.extension.toLocalDate
import callofproject.dev.androidapp.domain.dto.user_profile.course.CourseDTO
import callofproject.dev.androidapp.domain.dto.user_profile.education.EducationDTO
import callofproject.dev.androidapp.domain.dto.user_profile.experience.ExperienceDTO
import callofproject.dev.androidapp.domain.dto.user_profile.link.LinkDTO
import java.time.format.DateTimeFormatter

fun CourseDTO.validate(
    courseDTO: CourseDTO,
    context: Context,
    formatter: DateTimeFormatter
): String {

    if (courseDTO.courseName.isEmpty() || courseDTO.courseName.isBlank())
        return context.getString(R.string.msg_course_name_empty)

    if (courseDTO.organization.isEmpty() || courseDTO.organization.isBlank())
        return context.getString(R.string.msg_course_organization_empty)


    if (courseDTO.description.isEmpty() || courseDTO.description.isBlank())
        return context.getString(R.string.msg_course_description_empty)


    if (courseDTO.startDate.isBlank() || courseDTO.startDate.isEmpty() ||
        courseDTO.finishDate.isBlank() || courseDTO.finishDate.isEmpty()
    )
        return context.getString(R.string.msg_course_date_empty)


    if (courseDTO.startDate == "Start Date" || courseDTO.finishDate == "Finish Date")
        return context.getString(R.string.msg_course_date_empty)


    if (toLocalDate(courseDTO.startDate, formatter) > toLocalDate(courseDTO.finishDate, formatter))
        return context.getString(R.string.msg_course_date_invalid)

    return context.getString(R.string.msg_course_valid)
}


fun LinkDTO.validate(linkDTO: LinkDTO, context: Context): String {
    if (linkDTO.linkTitle.isEmpty() || linkDTO.linkTitle.isBlank())
        return context.getString(R.string.msg_link_title_empty)

    if (linkDTO.link.isEmpty() || linkDTO.link.isBlank())
        return context.getString(R.string.msg_link_empty)

    return context.getString(R.string.msg_link_valid)
}

fun ExperienceDTO.validate(
    experienceDTO: ExperienceDTO,
    context: Context,
    formatter: DateTimeFormatter
): String {
    if (experienceDTO.companyName.isEmpty() || experienceDTO.companyName.isBlank())
        return context.getString(R.string.msg_experience_company_name_empty)

    if (experienceDTO.description.isEmpty() || experienceDTO.description.isBlank())
        return context.getString(R.string.msg_experience_description_empty)

    if (experienceDTO.position.isEmpty() || experienceDTO.position.isBlank())
        return context.getString(R.string.msg_experience_position_empty)

    if (experienceDTO.startDate.isEmpty() || experienceDTO.startDate.isBlank() ||
        experienceDTO.finishDate.isEmpty() || experienceDTO.finishDate.isBlank()
    )
        return context.getString(R.string.msg_experience_date_empty)

    if (experienceDTO.startDate == "Start Date" || experienceDTO.finishDate == "Finish Date")
        return context.getString(R.string.msg_experience_date_empty)

    if (toLocalDate(experienceDTO.startDate, formatter) > toLocalDate(
            experienceDTO.finishDate,
            formatter
        )
    )
        return context.getString(R.string.date_invalid)

    return context.getString(R.string.msg_experience_valid)
}

fun EducationDTO.validate(
    education: EducationDTO,
    context: Context,
    formatter: DateTimeFormatter
): String {
    if (education.schoolName.isEmpty() || education.schoolName.isBlank())
        return context.getString(R.string.msg_education_school_name_empty)

    if (education.department.isEmpty() || education.department.isBlank())
        return context.getString(R.string.msg_education_department_empty)

    if (education.gpa == 0.0)
        return context.getString(R.string.msg_education_gpa_empty)

    if (education.gpa < 0.0 || education.gpa > 4.0)
        return context.getString(R.string.msg_education_gpa_invalid)

    if (education.startDate.isEmpty() || education.startDate.isBlank() ||
        education.finishDate.isEmpty() || education.finishDate.isBlank()
    )
        return context.getString(R.string.msg_education_date_empty)

    if (education.startDate == "Start Date" || education.finishDate == "Finish Date")
        return context.getString(R.string.msg_education_date_empty)

    if (toLocalDate(education.startDate, formatter) > toLocalDate(education.finishDate, formatter))
        return context.getString(R.string.date_invalid)

    return context.getString(R.string.msg_education_valid)
}