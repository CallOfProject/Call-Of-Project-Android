package callofproject.dev.androidapp.presentation.user_profile

import android.content.Context
import android.net.Uri
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
import callofproject.dev.androidapp.data.validator.validate
import callofproject.dev.androidapp.di.interceptor.LocalDateFormatterInterceptor
import callofproject.dev.androidapp.domain.dto.user_profile.course.CourseDTO
import callofproject.dev.androidapp.domain.dto.user_profile.education.EducationDTO
import callofproject.dev.androidapp.domain.dto.user_profile.experience.ExperienceDTO
import callofproject.dev.androidapp.domain.dto.user_profile.link.LinkDTO
import callofproject.dev.androidapp.domain.preferences.IPreferences
import callofproject.dev.androidapp.domain.use_cases.UseCaseFacade
import callofproject.dev.androidapp.util.route.UiEvent.ShowToastMessage
import callofproject.dev.androidapp.util.route.UiText.DynamicString
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val useCaseFacade: UseCaseFacade,
    private val preferences: IPreferences,
    @LocalDateFormatterInterceptor val dateTimeFormatter: DateTimeFormatter,
    @ApplicationContext private val context: Context,
    private val viewModelHelper: UserProfileViewModelHelper
) : ViewModel() {

    private var uploadImageJob: Job? = null
    private var uploadCVJob: Job? = null

    init {
        preferences.clearFilterObjects()
    }

    fun getState() = viewModelHelper.state
    private fun getUiEventForViewModel() = viewModelHelper._uiEvent
    fun getUiEvent() = viewModelHelper.uiEvent


    fun onEvent(event: UserProfileEvent) = when (event) {
        is UserProfileEvent.OnUpdateAboutMe -> updateAboutMe(event.aboutMe)
        is UserProfileEvent.OnCreateEducation -> saveEducation(event.educationDTO)
        is UserProfileEvent.OnUpdateEducation -> updateEducation(event.educationDTO)
        is UserProfileEvent.OnDeleteEducation -> deleteEducation(event.educationId)
        is UserProfileEvent.OnCreateExperience -> saveExperience(event.experienceDTO)
        is UserProfileEvent.OnUpdateExperience -> updateExperience(event.experienceDTO)
        is UserProfileEvent.OnDeleteExperience -> deleteExperience(event.experienceId)
        is UserProfileEvent.OnCreateCourse -> saveCourse(event.courseDTO)
        is UserProfileEvent.OnUpdateCourse -> updateCourse(event.courseDTO)
        is UserProfileEvent.OnDeleteCourse -> deleteCourse(event.courseId)
        is UserProfileEvent.OnCreateLink -> saveLink(event.linkDTO)
        is UserProfileEvent.OnUpdateLink -> updateLink(event.linkDTO)
        is UserProfileEvent.OnRemoveLinkClicked -> removeLink(event.linkId)
        is UserProfileEvent.OnCreateTag -> createTag(event.tagName)
        is UserProfileEvent.OnRemoveTag -> removeTag(event.tagId)
        is UserProfileEvent.OnUploadProfilePhoto -> uploadProfilePhoto(event.pp)
        is UserProfileEvent.OnUploadCv -> uploadCv(event.file)
    }

    private fun updateAboutMe(aboutMe: String) {
        viewModelScope.launch {
            val profileUpdateDTO = getState().userProfileDTO
                .profile
                .copy(aboutMe = aboutMe)
                .toUserProfileUpdateDTO(preferences.getUserId()!!)

            useCaseFacade.userProfile
                .updateUserProfile(profileUpdateDTO)
                .let { viewModelHelper.updateAboutMeCallback(it, aboutMe) }
        }
    }

    private fun saveEducation(educationDTO: EducationDTO) {
        viewModelScope.launch {
            val validatorMsg = educationDTO.validate(educationDTO, context, dateTimeFormatter)

            if (validatorMsg != context.getString(R.string.msg_education_valid)) {
                getUiEventForViewModel().send(ShowToastMessage(DynamicString(validatorMsg)))
                return@launch
            }

            useCaseFacade.userProfile
                .saveEducation(educationDTO.toEducationUpsertDTO(preferences.getUserId()!!))
                .let { viewModelHelper.saveEducationCallback(it) }
        }
    }

    private fun updateEducation(educationDTO: EducationDTO) {
        viewModelScope.launch {
            val validatorMsg = educationDTO.validate(educationDTO, context, dateTimeFormatter)

            if (validatorMsg != context.getString(R.string.msg_education_valid)) {
                getUiEventForViewModel().send(ShowToastMessage(DynamicString(validatorMsg)))
                return@launch
            }

            useCaseFacade.userProfile
                .updateEducation(educationDTO.toEducationUpdateDTO(preferences.getUserId()!!))
                .let { viewModelHelper.updateEducationCallback(it) }
        }
    }


    private fun deleteEducation(educationId: String) {
        viewModelScope.launch {
            useCaseFacade.userProfile.removeEducation(educationId)
                .let { viewModelHelper.deleteEducationCallback(it, educationId) }
        }
    }


    private fun saveExperience(experienceDTO: ExperienceDTO) {
        viewModelScope.launch {
            val validatorMsg = experienceDTO.validate(experienceDTO, context, dateTimeFormatter)

            if (validatorMsg != context.getString(R.string.msg_experience_valid)) {
                getUiEventForViewModel().send(ShowToastMessage(DynamicString(validatorMsg)))
                return@launch
            }

            useCaseFacade.userProfile
                .saveExperience(experienceDTO.toExperienceCreateDTO(preferences.getUserId()!!))
                .let { viewModelHelper.saveExperienceCallback(it) }
        }
    }


    private fun updateExperience(experienceDTO: ExperienceDTO) {
        viewModelScope.launch {
            val validatorMsg = experienceDTO.validate(experienceDTO, context, dateTimeFormatter)

            if (validatorMsg != context.getString(R.string.msg_experience_valid)) {
                getUiEventForViewModel().send(ShowToastMessage(DynamicString(validatorMsg)))
                return@launch
            }

            useCaseFacade.userProfile
                .updateExperience(experienceDTO.toExperienceUpdateDTO(preferences.getUserId()!!))
                .let { viewModelHelper.updateExperienceCallback(it) }
        }
    }

    private fun deleteExperience(experienceId: String) {
        viewModelScope.launch {
            useCaseFacade.userProfile
                .removeExperience(experienceId)
                .let { viewModelHelper.deleteExperienceCallback(it, experienceId) }
        }
    }

    private fun saveCourse(courseDTO: CourseDTO) {
        viewModelScope.launch {
            val validatorMsg = courseDTO.validate(courseDTO, context, dateTimeFormatter)

            if (validatorMsg != context.getString(R.string.msg_course_valid)) {
                getUiEventForViewModel().send(ShowToastMessage(DynamicString(validatorMsg)))
                return@launch
            }

            useCaseFacade.userProfile
                .saveCourse(courseDTO.toCourseCreateDTO(preferences.getUserId()!!))
                .let { viewModelHelper.saveCourseCallback(it) }
        }
    }

    private fun updateCourse(courseDTO: CourseDTO) {
        viewModelScope.launch {
            val validatorMsg = courseDTO.validate(courseDTO, context, dateTimeFormatter)

            if (validatorMsg != context.getString(R.string.msg_course_valid)) {
                getUiEventForViewModel().send(ShowToastMessage(DynamicString(validatorMsg)))
                return@launch
            }
            useCaseFacade.userProfile
                .updateCourse(courseDTO.toCourseUpdateDTO(preferences.getUserId()!!))
                .let { viewModelHelper.updateCourseCallback(it) }
        }
    }

    private fun deleteCourse(courseId: String) {
        viewModelScope.launch {
            useCaseFacade.userProfile
                .removeCourse(courseId)
                .let { viewModelHelper.deleteCourseCallback(it, courseId) }
        }
    }

    private fun saveLink(linkDTO: LinkDTO) {
        viewModelScope.launch {

            val validatorMsg = linkDTO.validate(linkDTO, context)

            if (validatorMsg != context.getString(R.string.msg_link_valid)) {
                getUiEventForViewModel().send(ShowToastMessage(DynamicString(validatorMsg)))
                return@launch
            }

            useCaseFacade.userProfile
                .saveLink(linkDTO.toLinkCreateDTO(preferences.getUserId()!!))
                .let { viewModelHelper.saveLinkCallback(it) }
        }
    }

    private fun updateLink(linkDTO: LinkDTO) {
        viewModelScope.launch {
            val validatorMsg = linkDTO.validate(linkDTO, context)

            if (validatorMsg != context.getString(R.string.msg_link_valid)) {
                getUiEventForViewModel().send(ShowToastMessage(DynamicString(validatorMsg)))
                return@launch
            }
            useCaseFacade.userProfile
                .updateLink(linkDTO.toLinkUpdateDTO(preferences.getUserId()!!))
                .let { viewModelHelper.updateLinkCallback(it) }
        }
    }

    private fun removeLink(linkId: Long) {
        viewModelScope.launch {
            useCaseFacade.userProfile
                .removeLink(linkId)
                .let { viewModelHelper.deleteLinkCallback(it, linkId) }
        }
    }

    private fun createTag(tagName: String) {
        viewModelScope.launch {
            useCaseFacade.userProfile
                .createUserTag(tagName)
                .let { viewModelHelper.createTagCallback(it) }
        }
    }

    private fun removeTag(tagId: String) {
        viewModelScope.launch {
            useCaseFacade.userProfile
                .removeUserTag(UUID.fromString(tagId))
                .let { viewModelHelper.removeTagCallback(it, tagId) }
        }
    }

    private fun uploadCv(file: Uri) {
        uploadCVJob?.cancel()

        uploadCVJob = viewModelScope.launch {
            useCaseFacade.uploadFile.uploadCv(file)
                .onStart { delay(500L) }
                .onEach { viewModelHelper.uploadCvCallback(it) }
                .launchIn(this)
        }
    }

    private fun uploadProfilePhoto(file: Uri) {
        uploadImageJob?.cancel()

        uploadImageJob = viewModelScope.launch {
            useCaseFacade.uploadFile.uploadProfilePhoto(file)
                .onStart { delay(1000) }
                .onEach { viewModelHelper.uploadPhotoCallback(it) }
                .launchIn(this)
        }
    }


    fun findUserProfileByUserId(userId: String = preferences.getUserId()!!) {
        viewModelScope.launch {
            useCaseFacade.userProfile
                .findUserProfile(userId)
                .let { viewModelHelper.findUserProfileCallback(it) }
        }
    }
}