package callofproject.dev.androidapp.presentation.user_profile

import callofproject.dev.androidapp.domain.dto.UserWithProfileDTO

data class UserProfileState(
    val userProfileDTO: UserWithProfileDTO = UserWithProfileDTO()
)
