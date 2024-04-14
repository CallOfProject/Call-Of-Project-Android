package callofproject.dev.androidapp.presentation.user_profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import callofproject.dev.androidapp.data.mapper.toCourseCreateDTO
import callofproject.dev.androidapp.data.mapper.toCourseUpdateDTO
import callofproject.dev.androidapp.data.mapper.toEducationUpdateDTO
import callofproject.dev.androidapp.data.mapper.toEducationUpsertDTO
import callofproject.dev.androidapp.data.mapper.toExperienceCreateDTO
import callofproject.dev.androidapp.data.mapper.toExperienceUpdateDTO
import callofproject.dev.androidapp.data.mapper.toLinkCreateDTO
import callofproject.dev.androidapp.data.mapper.toLinkUpdateDTO
import callofproject.dev.androidapp.data.mapper.toUserProfileUpdateDTO
import callofproject.dev.androidapp.domain.dto.user_profile.course.CourseDTO
import callofproject.dev.androidapp.domain.dto.user_profile.education.EducationDTO
import callofproject.dev.androidapp.domain.dto.user_profile.experience.ExperienceDTO
import callofproject.dev.androidapp.domain.dto.user_profile.link.LinkDTO
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


    fun onEvent(event: UserProfileEvent) = when (event) {
        is UserProfileEvent.OnCreateEducation -> saveEducation(event.educationDTO)
        is UserProfileEvent.OnUpdateEducation -> updateEducation(event.educationDTO)
        is UserProfileEvent.OnCreateExperience -> saveExperience(event.experienceDTO)
        is UserProfileEvent.OnUpdateExperience -> updateExperience(event.experienceDTO)
        is UserProfileEvent.OnCreateCourse -> saveCourse(event.courseDTO)
        is UserProfileEvent.OnUpdateCourse -> updateCourse(event.courseDTO)
        is UserProfileEvent.OnCreateLink -> saveLink(event.linkDTO)
        is UserProfileEvent.OnUpdateLink -> updateLink(event.linkDTO)
        is UserProfileEvent.OnUpdateAboutMe -> updateAboutMe(event.aboutMe)
    }

    private fun updateAboutMe(aboutMe: String) {
        viewModelScope.launch {
            val userId = preferences.getUserId()!!
            useCaseFacade.updateProfile(
                state.userProfileDTO.profile.copy(aboutMe = aboutMe).toUserProfileUpdateDTO(userId)
            ).let { result ->
                when (result) {
                    is Resource.Success -> {
                        state = state.copy(
                            userProfileDTO = state.userProfileDTO.copy(
                                profile = state.userProfileDTO.profile.copy(aboutMe = aboutMe)
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

    private fun updateLink(linkDTO: LinkDTO) {
        viewModelScope.launch {
            useCaseFacade.updateLink(linkDTO.toLinkUpdateDTO(preferences.getUserId()!!))
                .let { result ->
                    when (result) {
                        is Resource.Success -> {
                            state = state.copy(
                                userProfileDTO = state.userProfileDTO.copy(
                                    profile = state.userProfileDTO.profile
                                        .copy(links = state.userProfileDTO.profile.links.map {
                                            if (it.linkId == result.data!!.linkId) result.data else it
                                        })
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

    private fun saveLink(linkDTO: LinkDTO) {
        viewModelScope.launch {
            val userId = preferences.getUserId()!!
            useCaseFacade.saveLink(linkDTO.toLinkCreateDTO(userId))
                .let { result ->
                    when (result) {
                        is Resource.Success -> {
                            state = state.copy(
                                userProfileDTO = state.userProfileDTO
                                    .copy(
                                        profile = state.userProfileDTO.profile
                                            .copy(links = state.userProfileDTO.profile.links + result.data!!)
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

    private fun updateCourse(courseDTO: CourseDTO) {
        viewModelScope.launch {
            useCaseFacade.updateCourse(courseDTO.toCourseUpdateDTO(preferences.getUserId()!!))
                .let { result ->
                    when (result) {
                        is Resource.Success -> {
                            state = state.copy(
                                userProfileDTO = state.userProfileDTO.copy(
                                    profile = state.userProfileDTO.profile
                                        .copy(courses = state.userProfileDTO.profile.courses.map {
                                            if (it.courseId == result.data!!.courseId) result.data else it
                                        })
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

    private fun saveCourse(courseDTO: CourseDTO) {
        viewModelScope.launch {
            val userId = preferences.getUserId()!!
            useCaseFacade.saveCourse(courseDTO.toCourseCreateDTO(userId))
                .let { result ->
                    when (result) {
                        is Resource.Success -> {
                            state = state.copy(
                                userProfileDTO = state.userProfileDTO
                                    .copy(
                                        profile = state.userProfileDTO.profile
                                            .copy(courses = state.userProfileDTO.profile.courses + result.data!!)
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

    private fun updateExperience(experienceDTO: ExperienceDTO) {
        viewModelScope.launch {
            useCaseFacade.updateExperience(experienceDTO.toExperienceUpdateDTO(preferences.getUserId()!!))
                .let { result ->
                    when (result) {
                        is Resource.Success -> {
                            state = state.copy(
                                userProfileDTO = state.userProfileDTO.copy(
                                    profile = state.userProfileDTO.profile
                                        .copy(experiences = state.userProfileDTO.profile.experiences.map {
                                            if (it.experienceId == result.data!!.experienceId) result.data else it
                                        })
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

    private fun saveExperience(experienceDTO: ExperienceDTO) {
        viewModelScope.launch {
            val userId = preferences.getUserId()!!
            useCaseFacade.saveExperience(experienceDTO.toExperienceCreateDTO(userId))
                .let { result ->
                    when (result) {
                        is Resource.Success -> {
                            state = state.copy(
                                userProfileDTO = state.userProfileDTO
                                    .copy(
                                        profile = state.userProfileDTO.profile
                                            .copy(experiences = state.userProfileDTO.profile.experiences + result.data!!)
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

    private fun updateEducation(educationDTO: EducationDTO) {
        viewModelScope.launch {
            useCaseFacade.updateEducation(educationDTO.toEducationUpdateDTO(preferences.getUserId()!!))
                .let { result ->
                    when (result) {
                        is Resource.Success -> {
                            state = state.copy(
                                userProfileDTO = state.userProfileDTO.copy(
                                    profile = state.userProfileDTO.profile.copy(
                                        educations = state.userProfileDTO.profile.educations.map {
                                            if (it.educationId == result.data!!.educationId) result.data else it
                                        }
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

    private fun saveEducation(educationDTO: EducationDTO) {
        viewModelScope.launch {
            val userId = preferences.getUserId()!!
            useCaseFacade.saveEducation(educationDTO.toEducationUpsertDTO(userId))
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
            useCaseFacade.findUserProfile(userId).let { result ->
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