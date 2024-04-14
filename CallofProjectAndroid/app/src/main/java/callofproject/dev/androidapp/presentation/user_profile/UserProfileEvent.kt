package callofproject.dev.androidapp.presentation.user_profile

import callofproject.dev.androidapp.domain.dto.user_profile.course.CourseDTO
import callofproject.dev.androidapp.domain.dto.user_profile.education.EducationDTO
import callofproject.dev.androidapp.domain.dto.user_profile.experience.ExperienceDTO
import callofproject.dev.androidapp.domain.dto.user_profile.link.LinkDTO

sealed class UserProfileEvent {
    data class OnCreateEducation(val educationDTO: EducationDTO) : UserProfileEvent()
    data class OnUpdateEducation(val educationDTO: EducationDTO) : UserProfileEvent()
    data class OnCreateExperience(val experienceDTO: ExperienceDTO) : UserProfileEvent()
    data class OnUpdateExperience(val experienceDTO: ExperienceDTO) : UserProfileEvent()
    data class OnCreateCourse(val courseDTO: CourseDTO) : UserProfileEvent()
    data class OnUpdateCourse(val courseDTO: CourseDTO) : UserProfileEvent()
    data class OnCreateLink(val linkDTO: LinkDTO) : UserProfileEvent()
    data class OnUpdateLink(val linkDTO: LinkDTO) : UserProfileEvent()
    data class OnUpdateAboutMe(val aboutMe: String) : UserProfileEvent()
}