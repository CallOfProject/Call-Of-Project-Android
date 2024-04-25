package callofproject.dev.androidapp.presentation.user_profile

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import callofproject.dev.androidapp.R
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
import callofproject.dev.androidapp.util.route.UiEvent
import callofproject.dev.androidapp.util.route.UiEvent.ShowSnackbar
import callofproject.dev.androidapp.util.route.UiText.DynamicString
import callofproject.dev.androidapp.util.route.UiText.StringResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val useCaseFacade: UseCaseFacade,
    private val preferences: IPreferences
) : ViewModel() {

    var state by mutableStateOf(UserProfileState())
        private set
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var uploadImageJob: Job? = null
    private var uploadCVJob: Job? = null

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
        is UserProfileEvent.OnUploadProfilePhoto -> uploadProfilePhoto(event.pp)
        is UserProfileEvent.OnUploadCv -> uploadCv(event.file)
    }

    private fun uploadCv(file: Uri) {
        uploadCVJob?.cancel()
        uploadCVJob = viewModelScope.launch {
            useCaseFacade.uploadFile.uploadCv(file)
                .onStart { delay(500L) }
                .onEach { uploadCvCallback(it) }.launchIn(this)
        }
    }

    private suspend fun uploadCvCallback(resource: Resource<String>) = when (resource) {
        is Resource.Success -> uploadCvSuccessCallback(resource.data!!)
        is Resource.Error -> uploadCvErrorCallback()
        is Resource.Loading -> state = state.copy(isCvLoading = true, isPhotoLoading = false)
    }

    private suspend fun uploadCvSuccessCallback(data: String) {
        state = state.copy(
            userProfileDTO = state.userProfileDTO
                .copy(profile = state.userProfileDTO.profile.copy(cv = data)),
            isCvLoading = false
        )
        _uiEvent.send(ShowSnackbar(StringResource(R.string.msg_cv_uploaded)))
    }

    private suspend fun uploadCvErrorCallback() {
        state = state.copy(isCvLoading = false)
        _uiEvent.send(ShowSnackbar(StringResource(R.string.msg_cv_upload_fail)))
    }


    private fun uploadProfilePhoto(file: Uri) {
        uploadImageJob?.cancel()
        uploadImageJob = viewModelScope.launch {
            useCaseFacade.uploadFile.uploadProfilePhoto(file)
                .onStart { delay(1000) }
                .onEach { uploadPhotoCallback(it) }
                .launchIn(this)
        }
    }

    private suspend fun uploadPhotoCallback(resource: Resource<String>) = when (resource) {
        is Resource.Success -> uploadPhotoSuccessCallback(resource.data!!)
        is Resource.Error -> uploadPhotoErrorCallback()
        is Resource.Loading -> state = state.copy(isPhotoLoading = true)
    }

    private suspend fun uploadPhotoSuccessCallback(data: String) {
        state = state.copy(
            userProfileDTO = state.userProfileDTO.copy(
                profile = state.userProfileDTO.profile.copy(profilePhoto = data)
            ), isPhotoLoading = false
        )
        _uiEvent.send(ShowSnackbar(StringResource(R.string.msg_profile_photo_uploaded)))
    }

    private suspend fun uploadPhotoErrorCallback() {
        state = state.copy(isPhotoLoading = false)
        _uiEvent.send(ShowSnackbar(StringResource(R.string.msg_profile_photo_upload_fail)))
    }

    private fun updateAboutMe(aboutMe: String) {
        viewModelScope.launch {
            val userId = preferences.getUserId()!!

            val profileUpdateDTO =
                state.userProfileDTO.profile.copy(aboutMe = aboutMe).toUserProfileUpdateDTO(userId)

            useCaseFacade.userProfile.updateUserProfile(profileUpdateDTO).let { result ->
                when (result) {
                    is Resource.Success -> {
                        state = state.copy(
                            userProfileDTO = state.userProfileDTO.copy(
                                profile = state.userProfileDTO.profile.copy(aboutMe = aboutMe)
                            )
                        )
                    }

                    is Resource.Error -> {
                        _uiEvent.send(ShowSnackbar(DynamicString(result.message!!)))
                    }

                    is Resource.Loading -> {}
                }
            }
        }

    }

    private fun updateLink(linkDTO: LinkDTO) {
        viewModelScope.launch {
            useCaseFacade.userProfile.updateLink(linkDTO.toLinkUpdateDTO(preferences.getUserId()!!))
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
                            _uiEvent.send(ShowSnackbar(DynamicString(result.message!!)))
                        }

                        is Resource.Loading -> {}
                    }
                }
        }
    }

    private fun saveLink(linkDTO: LinkDTO) {
        viewModelScope.launch {
            val userId = preferences.getUserId()!!
            useCaseFacade.userProfile.saveLink(linkDTO.toLinkCreateDTO(userId))
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
                            _uiEvent.send(ShowSnackbar(DynamicString(result.message!!)))
                        }

                        is Resource.Loading -> {}
                    }
                }
        }

    }

    private fun updateCourse(courseDTO: CourseDTO) {
        viewModelScope.launch {
            useCaseFacade.userProfile.updateCourse(courseDTO.toCourseUpdateDTO(preferences.getUserId()!!))
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
            useCaseFacade.userProfile.saveCourse(courseDTO.toCourseCreateDTO(userId))
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
            useCaseFacade.userProfile.updateExperience(
                experienceDTO.toExperienceUpdateDTO(
                    preferences.getUserId()!!
                )
            )
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
            useCaseFacade.userProfile.saveExperience(experienceDTO.toExperienceCreateDTO(userId))
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
            useCaseFacade.userProfile.updateEducation(educationDTO.toEducationUpdateDTO(preferences.getUserId()!!))
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
            useCaseFacade.userProfile.saveEducation(educationDTO.toEducationUpsertDTO(userId))
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

    fun findUserProfileByUserId(userId: String = preferences.getUserId()!!) {
        viewModelScope.launch {
            useCaseFacade.userProfile.findUserProfile(userId).let { result ->
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