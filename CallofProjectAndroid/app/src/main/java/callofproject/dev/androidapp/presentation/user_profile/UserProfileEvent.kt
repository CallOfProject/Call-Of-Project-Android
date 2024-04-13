package callofproject.dev.androidapp.presentation.user_profile

import callofproject.dev.androidapp.domain.dto.EducationDTO

sealed class UserProfileEvent {
    data class OnUpsertEducation(val educationDTO: EducationDTO) : UserProfileEvent()
}