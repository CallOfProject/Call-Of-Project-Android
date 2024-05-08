package callofproject.dev.androidapp.presentation.user_profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.domain.dto.user_profile.UserProfileDTO
import callofproject.dev.androidapp.domain.dto.user_profile.UserTagDTO
import callofproject.dev.androidapp.domain.dto.user_profile.UserWithProfileDTO
import callofproject.dev.androidapp.domain.dto.user_profile.course.CourseDTO
import callofproject.dev.androidapp.domain.dto.user_profile.education.EducationDTO
import callofproject.dev.androidapp.domain.dto.user_profile.experience.ExperienceDTO
import callofproject.dev.androidapp.domain.dto.user_profile.link.LinkDTO
import callofproject.dev.androidapp.util.Resource
import callofproject.dev.androidapp.util.route.UiEvent
import callofproject.dev.androidapp.util.route.UiEvent.ShowSnackbar
import callofproject.dev.androidapp.util.route.UiEvent.ShowToastMessage
import callofproject.dev.androidapp.util.route.UiText
import callofproject.dev.androidapp.util.route.UiText.StringResource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject


class UserProfileViewModelHelper @Inject constructor() {

    val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(UserProfileState())
        private set

    suspend fun updateAboutMeCallback(result: Resource<UserProfileDTO>, aboutMe: String) {
        when (result) {
            is Resource.Success -> {
                state = state.copy(
                    userProfileDTO = state.userProfileDTO.copy(
                        profile = state.userProfileDTO.profile.copy(aboutMe = aboutMe)
                    )
                )

                _uiEvent.send(ShowToastMessage(StringResource(R.string.msg_about_me_updated)))
            }

            is Resource.Error -> {
                _uiEvent.send(ShowToastMessage(StringResource(R.string.msg_about_me_not_updated)))
            }

            is Resource.Loading -> {}
        }
    }


    suspend fun saveEducationCallback(result: Resource<EducationDTO>) {
        when (result) {
            is Resource.Success -> {
                state = state.copy(
                    userProfileDTO = state.userProfileDTO.copy(
                        profile = state.userProfileDTO.profile.copy(
                            educations = state.userProfileDTO.profile.educations + result.data!!
                        )
                    )
                )

                _uiEvent.send(
                    UiEvent.ShowToastMessageViaStatus(
                        StringResource(R.string.msg_education_saved),
                        true
                    )
                )
            }

            is Resource.Error -> {
                _uiEvent.send(ShowToastMessage(StringResource(R.string.msg_education_not_saved)))
            }

            is Resource.Loading -> Unit
        }
    }

    suspend fun updateEducationCallback(result: Resource<EducationDTO>) {
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

                _uiEvent.send(
                    UiEvent.ShowToastMessageViaStatus(
                        StringResource(R.string.msg_education_updated),
                        true
                    )
                )
            }

            is Resource.Error -> {
                _uiEvent.send(ShowToastMessage(StringResource(R.string.msg_education_not_updated)))
            }

            is Resource.Loading -> Unit
        }
    }

    suspend fun deleteEducationCallback(result: Resource<Unit>, educationId: String) {
        when (result) {
            is Resource.Success -> {
                state = state.copy(
                    userProfileDTO = state.userProfileDTO
                        .copy(
                            profile = state.userProfileDTO.profile
                                .copy(educations = state.userProfileDTO.profile.educations
                                    .filter { it.educationId != educationId })
                        )
                )

                _uiEvent.send(ShowToastMessage(StringResource(R.string.msg_education_deleted)))
            }

            is Resource.Error -> {
                _uiEvent.send(ShowToastMessage(StringResource(R.string.msg_education_not_deleted)))

            }

            is Resource.Loading -> {}
        }
    }

    suspend fun saveExperienceCallback(result: Resource<ExperienceDTO>) {
        when (result) {
            is Resource.Success -> {
                state = state.copy(
                    userProfileDTO = state.userProfileDTO
                        .copy(
                            profile = state.userProfileDTO.profile
                                .copy(experiences = state.userProfileDTO.profile.experiences + result.data!!)
                        )
                )

                _uiEvent.send(
                    UiEvent.ShowToastMessageViaStatus(
                        StringResource(R.string.msg_experience_saved),
                        true
                    )
                )
            }

            is Resource.Error -> {
                _uiEvent.send(ShowToastMessage(StringResource(R.string.msg_experience_not_saved)))
            }

            else -> Unit
        }
    }

    suspend fun updateExperienceCallback(result: Resource<ExperienceDTO>) {
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
                _uiEvent.send(
                    UiEvent.ShowToastMessageViaStatus(
                        StringResource(R.string.msg_experience_updated),
                        true
                    )
                )
            }

            is Resource.Error -> {
                _uiEvent.send(ShowToastMessage(StringResource(R.string.msg_experience_not_updated)))
            }

            is Resource.Loading -> Unit
        }
    }

    suspend fun deleteExperienceCallback(result: Resource<Unit>, experienceId: String) {
        when (result) {
            is Resource.Success -> {
                state = state.copy(
                    userProfileDTO = state.userProfileDTO
                        .copy(
                            profile = state.userProfileDTO.profile
                                .copy(experiences = state.userProfileDTO.profile.experiences
                                    .filter { it.experienceId != experienceId })
                        )
                )

                _uiEvent.send(ShowToastMessage(UiText.StringResource(R.string.msg_experience_deleted)))
            }

            is Resource.Error -> {
                _uiEvent.send(ShowToastMessage(UiText.StringResource(R.string.msg_experience_not_deleted)))
            }

            is Resource.Loading -> {}
        }
    }

    suspend fun saveCourseCallback(result: Resource<CourseDTO>) {
        when (result) {
            is Resource.Success -> {

                state = state.copy(
                    userProfileDTO = state.userProfileDTO
                        .copy(
                            profile = state.userProfileDTO.profile
                                .copy(courses = state.userProfileDTO.profile.courses + result.data!!)
                        )
                )

                _uiEvent.send(
                    UiEvent.ShowToastMessageViaStatus(
                        StringResource(R.string.msg_course_saved),
                        true
                    )
                )
            }

            is Resource.Error -> {
                _uiEvent.send(
                    ShowToastMessage(StringResource(R.string.msg_course_not_saved))
                )
            }

            is Resource.Loading -> {}
        }
    }

    suspend fun updateCourseCallback(result: Resource<CourseDTO>) {
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
                _uiEvent.send(
                    UiEvent.ShowToastMessageViaStatus(
                        StringResource(R.string.msg_course_updated),
                        true
                    )
                )
            }

            is Resource.Error -> _uiEvent.send(ShowToastMessage(StringResource(R.string.msg_course_not_updated)))

            is Resource.Loading -> Unit
        }
    }

    suspend fun deleteCourseCallback(result: Resource<Unit>, courseId: String) {
        when (result) {
            is Resource.Success -> {
                state = state.copy(
                    userProfileDTO = state.userProfileDTO
                        .copy(
                            profile = state.userProfileDTO.profile
                                .copy(courses = state.userProfileDTO.profile.courses
                                    .filter { it.courseId != courseId })
                        )
                )

                _uiEvent.send(ShowToastMessage(UiText.StringResource(R.string.msg_course_deleted)))
            }

            is Resource.Error -> {
                _uiEvent.send(ShowToastMessage(UiText.StringResource(R.string.msg_course_not_deleted)))
            }

            is Resource.Loading -> {}
        }
    }

    suspend fun saveLinkCallback(result: Resource<LinkDTO>) {
        when (result) {
            is Resource.Success -> {
                state = state.copy(
                    userProfileDTO = state.userProfileDTO
                        .copy(
                            profile = state.userProfileDTO.profile
                                .copy(links = state.userProfileDTO.profile.links + result.data!!)
                        )
                )

                _uiEvent.send(
                    UiEvent.ShowToastMessageViaStatus(
                        StringResource(R.string.msg_link_saved),
                        true
                    )
                )
            }


            is Resource.Error -> {
                _uiEvent.send(ShowToastMessage(StringResource(R.string.msg_link_not_saved)))
            }

            is Resource.Loading -> Unit
        }
    }

    suspend fun updateLinkCallback(result: Resource<LinkDTO>) {
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
                _uiEvent.send(
                    UiEvent.ShowToastMessageViaStatus(
                        StringResource(R.string.msg_link_updated),
                        true
                    )
                )
            }

            is Resource.Error -> {
                _uiEvent.send(ShowToastMessage(StringResource(R.string.msg_link_not_updated)))
            }

            is Resource.Loading -> Unit
        }
    }

    suspend fun deleteLinkCallback(result: Resource<Unit>, linkId: Long) {
        when (result) {
            is Resource.Success -> {
                state = state.copy(
                    userProfileDTO = state.userProfileDTO
                        .copy(
                            profile = state.userProfileDTO.profile
                                .copy(links = state.userProfileDTO.profile.links
                                    .filter { it.linkId != linkId })
                        )
                )
            }

            is Resource.Error -> {
                _uiEvent.send(ShowToastMessage(UiText.DynamicString(result.message!!)))
            }

            is Resource.Loading -> {}
        }
    }

    suspend fun createTagCallback(result: Resource<UserTagDTO>) {
        when (result) {
            is Resource.Success -> {
                state = state.copy(
                    userProfileDTO = state.userProfileDTO
                        .copy(
                            profile = state.userProfileDTO.profile
                                .copy(tags = state.userProfileDTO.profile.tags + result.data!!)
                        )
                )
            }

            is Resource.Error -> {
                _uiEvent.send(ShowToastMessage(UiText.DynamicString(result.message!!)))
            }

            is Resource.Loading -> {}
        }
    }


    suspend fun removeTagCallback(result: Resource<Unit>, tagId: String) {
        when (result) {
            is Resource.Success -> {
                state = state.copy(
                    userProfileDTO = state.userProfileDTO
                        .copy(
                            profile = state.userProfileDTO.profile
                                .copy(tags = state.userProfileDTO.profile.tags
                                    .filter { it.tagId != tagId })
                        )
                )
            }

            is Resource.Error -> {
                _uiEvent.send(ShowToastMessage(UiText.DynamicString(result.message!!)))
            }

            is Resource.Loading -> Unit
        }
    }

    suspend fun uploadCvCallback(resource: Resource<String>) = when (resource) {
        is Resource.Success -> uploadCvSuccessCallback(resource.data!!)
        is Resource.Error -> uploadCvErrorCallback()
        is Resource.Loading -> state = state.copy(isCvLoading = true, isPhotoLoading = false)
    }

    suspend fun uploadPhotoCallback(resource: Resource<String>) = when (resource) {
        is Resource.Success -> uploadPhotoSuccessCallback(resource.data!!)
        is Resource.Error -> uploadPhotoErrorCallback()
        is Resource.Loading -> state = state.copy(isPhotoLoading = true)
    }

    suspend fun findUserProfileCallback(result: Resource<UserWithProfileDTO>) {
        when (result) {
            is Resource.Success -> state = state.copy(userProfileDTO = result.data!!)

            is Resource.Error -> _uiEvent.send(ShowToastMessage(UiText.DynamicString(result.message!!)))

            is Resource.Loading -> Unit
        }
    }


    private suspend fun uploadCvSuccessCallback(data: String) {
        state = state.copy(
            userProfileDTO = state.userProfileDTO
                .copy(profile = state.userProfileDTO.profile.copy(cv = data)),
            isCvLoading = false
        )
        _uiEvent.send(ShowToastMessage(StringResource(R.string.msg_cv_uploaded)))
    }


    private suspend fun uploadCvErrorCallback() {
        state = state.copy(isCvLoading = false)
        _uiEvent.send(ShowToastMessage(StringResource(R.string.msg_cv_upload_fail)))
    }

    private suspend fun uploadPhotoSuccessCallback(data: String) {
        state = state.copy(
            userProfileDTO = state.userProfileDTO.copy(
                profile = state.userProfileDTO.profile.copy(profilePhoto = data)
            ), isPhotoLoading = false
        )
        _uiEvent.send(ShowToastMessage(StringResource(R.string.msg_profile_photo_uploaded)))
    }

    private suspend fun uploadPhotoErrorCallback() {
        state = state.copy(isPhotoLoading = false)
        _uiEvent.send(ShowToastMessage(StringResource(R.string.msg_profile_photo_upload_fail)))
    }
}