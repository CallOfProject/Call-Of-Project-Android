package callofproject.dev.androidapp.domain.use_cases

import android.util.Log
import callofproject.dev.androidapp.data.mapper.toCourseDTO
import callofproject.dev.androidapp.data.mapper.toEducationDTO
import callofproject.dev.androidapp.data.mapper.toExperienceDTO
import callofproject.dev.androidapp.data.mapper.toLinkDTO
import callofproject.dev.androidapp.data.remote.ICallOfProjectService
import callofproject.dev.androidapp.domain.dto.user_profile.UserProfileDTO
import callofproject.dev.androidapp.domain.dto.user_profile.UserProfileUpdateDTO
import callofproject.dev.androidapp.domain.dto.user_profile.UserTagDTO
import callofproject.dev.androidapp.domain.dto.user_profile.UserWithProfileDTO
import callofproject.dev.androidapp.domain.dto.user_profile.course.CourseCreateDTO
import callofproject.dev.androidapp.domain.dto.user_profile.course.CourseDTO
import callofproject.dev.androidapp.domain.dto.user_profile.course.CourseUpdateDTO
import callofproject.dev.androidapp.domain.dto.user_profile.education.EducationCreateDTO
import callofproject.dev.androidapp.domain.dto.user_profile.education.EducationDTO
import callofproject.dev.androidapp.domain.dto.user_profile.education.EducationUpdateDTO
import callofproject.dev.androidapp.domain.dto.user_profile.experience.ExperienceCreateDTO
import callofproject.dev.androidapp.domain.dto.user_profile.experience.ExperienceDTO
import callofproject.dev.androidapp.domain.dto.user_profile.experience.ExperienceUpdateDTO
import callofproject.dev.androidapp.domain.dto.user_profile.link.LinkCreateDTO
import callofproject.dev.androidapp.domain.dto.user_profile.link.LinkDTO
import callofproject.dev.androidapp.domain.dto.user_profile.link.LinkUpdateDTO
import callofproject.dev.androidapp.domain.preferences.IPreferences
import callofproject.dev.androidapp.util.Resource
import java.util.UUID
import javax.inject.Inject

class UserProfileUseCase @Inject constructor(
    private val service: ICallOfProjectService,
    private val preferences: IPreferences
) {
    suspend fun saveCourse(courseCreateDTO: CourseCreateDTO): Resource<CourseDTO> {
        return try {
            val responseMessage = service.saveCourse(
                courseCreateDTO = courseCreateDTO,
                token = getBearerToken()
            )
            Resource.Success(responseMessage.`object`.toCourseDTO())

        } catch (e: Exception) {
            Log.e("SaveCourseUseCase", e.message ?: "An error occurred")
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun saveEducation(educationCreateDTO: EducationCreateDTO): Resource<EducationDTO> {
        return try {
            val responseMessage = service.saveEducation(
                educationCreateDTO = educationCreateDTO,
                token = getBearerToken()
            )

            return Resource.Success(responseMessage.`object`.toEducationDTO())

        } catch (e: Exception) {
            Log.e("SaveEducationUseCase", e.message ?: "An error occurred")
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun saveExperience(experienceCreateDTO: ExperienceCreateDTO): Resource<ExperienceDTO> {
        return try {

            val responseMessage = service.saveExperience(
                experienceCreateDTO = experienceCreateDTO,
                token = getBearerToken()
            )

            return Resource.Success(responseMessage.`object`.toExperienceDTO())

        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun saveLink(linkCreateDTO: LinkCreateDTO): Resource<LinkDTO> {
        return try {

            val responseMessage = service.saveLink(
                linkCreateDTO = linkCreateDTO,
                token = getBearerToken()
            )

            return Resource.Success(responseMessage.`object`.toLinkDTO())

        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }


    suspend fun updateCourse(courseUpdateDTO: CourseUpdateDTO): Resource<CourseDTO> {
        return try {
            val responseMessage = service.updateCourse(
                courseUpdateDTO = courseUpdateDTO,
                token = getBearerToken()
            )

            Resource.Success(responseMessage.`object`)

        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }


    suspend fun updateEducation(educationCreateDTO: EducationUpdateDTO): Resource<EducationDTO> {
        return try {

            val responseMessage = service.updateEducation(
                educationUpdateDTO = educationCreateDTO,
                token = getBearerToken()
            )

            Resource.Success(responseMessage.`object`)

        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }


    suspend fun updateExperience(experienceUpdateDTO: ExperienceUpdateDTO): Resource<ExperienceDTO> {
        return try {

            val responseMessage = service.updateExperience(
                experienceUpdateDTO = experienceUpdateDTO,
                token = getBearerToken()
            )

            Resource.Success(responseMessage.`object`)

        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun updateLink(linkUpdateDTO: LinkUpdateDTO): Resource<LinkDTO> {
        return try {

            val responseMessage = service.updateLink(
                linkUpdateDTO = linkUpdateDTO,
                token = getBearerToken()
            )

            Resource.Success(responseMessage.`object`)

        } catch (e: Exception) {
            Resource.Error(e.message ?: "Link update failed")
        }
    }

    suspend fun updateUserProfile(updateDTO: UserProfileUpdateDTO): Resource<UserProfileDTO> {
        return try {

            val responseMessage = service.updateUserProfile(
                token = getBearerToken(),
                userId = UUID.fromString(updateDTO.userId),
                aboutMe = updateDTO.aboutMe,
            )

            Resource.Success(responseMessage.`object`)

        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }


    suspend fun findUserProfile(userId: String): Resource<UserWithProfileDTO> {
        return try {

            val responseMessage = service.findUserProfileByUserId(
                userId = UUID.fromString(userId),
                token = getBearerToken()
            )

            Log.d("UserProfileUseCase", responseMessage.`object`!!.toString())
            Resource.Success(responseMessage.`object`!!)

        } catch (e: Exception) {
            Log.e("UserProfileUseCase", e.message ?: "An error occurred")
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun removeCourse(courseId: String): Resource<Unit> {
        return try {
            service.removeCourse(
                userId = UUID.fromString(preferences.getUserId()!!),
                courseId = UUID.fromString(courseId),
                token = getBearerToken()
            )
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }


    suspend fun removeEducation(educationId: String): Resource<Unit> {
        return try {
            service.removeEducation(
                userId = UUID.fromString(preferences.getUserId()!!),
                educationId = UUID.fromString(educationId),
                token = getBearerToken()
            )
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }


    suspend fun removeExperience(experienceId: String): Resource<Unit> {
        return try {
            service.removeExperience(
                userId = UUID.fromString(preferences.getUserId()!!),
                experienceId = UUID.fromString(experienceId),
                token = getBearerToken()
            )
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }


    suspend fun removeLink(linkId: Long): Resource<Unit> {
        return try {
            service.removeLink(
                userId = UUID.fromString(preferences.getUserId()!!),
                linkId = linkId,
                token = getBearerToken()
            )
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }


    suspend fun createUserTag(tagName: String): Resource<UserTagDTO> {
        return try {
            val result = service.createUserTag(
                userId = UUID.fromString(preferences.getUserId()!!),
                tagName = tagName,
                token = getBearerToken()
            )
            Resource.Success(result.`object`)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun removeUserTag(tagId: UUID): Resource<Unit> {
        return try {
            service.deleteUserTag(
                userId = UUID.fromString(preferences.getUserId()!!),
                tagId = tagId,
                token = getBearerToken()
            )
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }
    private fun getBearerToken(): String = preferences.getToken()!!
}