package callofproject.dev.androidapp.domain.dto.user_profile

import callofproject.dev.androidapp.domain.dto.user_profile.course.CourseDTO
import callofproject.dev.androidapp.domain.dto.user_profile.education.EducationDTO
import callofproject.dev.androidapp.domain.dto.user_profile.experience.ExperienceDTO
import callofproject.dev.androidapp.domain.dto.user_profile.link.LinkDTO
import com.google.gson.annotations.SerializedName

data class UserProfileDTO(
    val cv: String? = "",

    @SerializedName("profile_photo")
    val profilePhoto: String? = "",

    @SerializedName("about_me")
    val aboutMe: String? = "",

    @SerializedName("user_rate")
    val userRate: Double = 0.0,

    @SerializedName("user_feedback_rate")
    val userFeedbackRate: Double = 0.0,

    val courses: List<CourseDTO> = emptyList(),

    val educations: List<EducationDTO> = emptyList(),

    val experiences: List<ExperienceDTO> = emptyList(),

    val links: List<LinkDTO> = emptyList(),
)
