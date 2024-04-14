package callofproject.dev.androidapp.data.mapper

import callofproject.dev.androidapp.domain.dto.UserProfileUpdateDTO
import callofproject.dev.androidapp.domain.dto.user_profile.UserProfileDTO

fun UserProfileDTO.toUserProfileUpdateDTO(userId: String): UserProfileUpdateDTO {
    return UserProfileUpdateDTO(
        userRate = userRate,
        userFeedbackRate = userFeedbackRate,
        userId = userId,
        aboutMe = aboutMe!!
    )
}