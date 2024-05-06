package callofproject.dev.androidapp.presentation.user_profile

import android.net.Uri
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
    data class OnUploadProfilePhoto(val pp: Uri) : UserProfileEvent()
    data class OnUploadCv(val file: Uri) : UserProfileEvent()
    data class OnRemoveLinkClicked(val linkId: Long) : UserProfileEvent()
    data class OnDeleteCourse(val courseId: String) : UserProfileEvent()
    data class OnDeleteEducation(val educationId: String) : UserProfileEvent()
    data class OnDeleteExperience(val experienceId: String) : UserProfileEvent()
    data class OnCreateTag(val tagName: String) : UserProfileEvent()
    data class OnRemoveTag(val tagId: String) : UserProfileEvent()
}