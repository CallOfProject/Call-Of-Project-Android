package callofproject.dev.androidapp.presentation.user_profile

import callofproject.dev.androidapp.domain.dto.user_profile.UserWithProfileDTO

data class UserProfileState(
    val userProfileDTO: UserWithProfileDTO = UserWithProfileDTO(),
    val isPhotoLoading: Boolean = false,
    val isCvLoading: Boolean = false
)
