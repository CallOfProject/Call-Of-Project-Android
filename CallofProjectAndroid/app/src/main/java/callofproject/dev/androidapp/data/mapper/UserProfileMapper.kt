package callofproject.dev.androidapp.data.mapper

import callofproject.dev.androidapp.domain.dto.user_profile.UserProfileDTO
import callofproject.dev.androidapp.domain.dto.user_profile.UserProfileUpdateDTO

fun UserProfileDTO.toUserProfileUpdateDTO(userId: String): UserProfileUpdateDTO {
    return UserProfileUpdateDTO(userRate, userFeedbackRate, userId, aboutMe!!)
}