package callofproject.dev.androidapp.presentation.user_profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import callofproject.dev.androidapp.data.mapper.toEducationUpsertDTO
import callofproject.dev.androidapp.domain.dto.EducationDTO
import callofproject.dev.androidapp.domain.preferences.IPreferences
import callofproject.dev.androidapp.domain.use_cases.UseCaseFacade
import callofproject.dev.androidapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val useCaseFacade: UseCaseFacade,
    private val preferences: IPreferences
) : ViewModel() {

    var state by mutableStateOf(UserProfileState())
        private set


    fun onEvent(event: UserProfileEvent) {
        when (event) {
            is UserProfileEvent.OnUpsertEducation -> upsertEducation(event.educationDTO)
        }
    }

    private fun upsertEducation(educationDTO: EducationDTO) {
        viewModelScope.launch {
            val userId = preferences.getUserId()!!
            useCaseFacade.upsertEducationUseCase(educationDTO.toEducationUpsertDTO(userId))
                .let { result ->
                    when (result) {
                        is Resource.Success -> {

                            state = state.copy(
                                userProfileDTO = state.userProfileDTO.copy(
                                    profile = state.userProfileDTO.profile.copy(
                                        educations = state.userProfileDTO.profile.educations + result.data!!
                                    )
                                )
                            )
                        }

                        is Resource.Error -> {
                            //_uiEvent.send(UiEvent.ShowSnackbar(result.message!!))
                        }

                        is Resource.Loading -> {
                            //_uiEvent.send(UiEvent.ShowSnackbar("Loading"))
                        }
                    }
                }
        }
    }


    fun findUserProfileByUserId(userId: String) {
        viewModelScope.launch {
            useCaseFacade.findUserProfileUseCase(userId).let { result ->
                when (result) {
                    is Resource.Success -> {
                        state = state.copy(
                            userProfileDTO = result.data!!
                        )
                    }

                    is Resource.Error -> {
                        //_uiEvent.send(UiEvent.ShowSnackbar(result.message!!))
                    }

                    is Resource.Loading -> {
                        //_uiEvent.send(UiEvent.ShowSnackbar("Loading"))
                    }
                }
            }
        }
    }
}