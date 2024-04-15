package callofproject.dev.androidapp.domain.use_cases

import android.util.Log
import callofproject.dev.androidapp.data.mapper.toCourseDTO
import callofproject.dev.androidapp.data.mapper.toEducationDTO
import callofproject.dev.androidapp.data.mapper.toExperienceDTO
import callofproject.dev.androidapp.data.mapper.toLinkDTO
import callofproject.dev.androidapp.data.remote.ICallOfProjectService
import callofproject.dev.androidapp.domain.dto.user_profile.UserProfileDTO
import callofproject.dev.androidapp.domain.dto.user_profile.UserProfileUpdateDTO
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
        var result: Resource<CourseDTO> = Resource.Loading()
        result = try {
            Log.d("SaveCourseUseCase", "invoke: $courseCreateDTO")
            val token = preferences.getToken()!!

            val responseMessage = service.saveCourse(
                courseCreateDTO = courseCreateDTO,
                token = token
            )

            return Resource.Success(responseMessage.`object`.toCourseDTO())

        } catch (e: Exception) {
            Log.e("SaveCourseUseCase", e.message ?: "An error occurred")
            Resource.Error(e.message ?: "An error occurred")
        }
        return result
    }

    suspend fun saveEducation(educationCreateDTO: EducationCreateDTO): Resource<EducationDTO> {
        var result: Resource<EducationDTO> = Resource.Loading()
        result = try {

            val token = preferences.getToken()!!

            val responseMessage = service.saveEducation(
                educationCreateDTO = educationCreateDTO,
                token = token
            )

            return Resource.Success(responseMessage.`object`.toEducationDTO())

        } catch (e: Exception) {
            Log.e("SaveEducationUseCase", e.message ?: "An error occurred")
            Resource.Error(e.message ?: "An error occurred")
        }
        return result
    }

    suspend fun saveExperience(experienceCreateDTO: ExperienceCreateDTO): Resource<ExperienceDTO> {
        var result: Resource<ExperienceDTO> = Resource.Loading()
        result = try {
            val token = preferences.getToken()!!

            val responseMessage = service.saveExperience(
                experienceCreateDTO = experienceCreateDTO,
                token = token
            )

            return Resource.Success(responseMessage.`object`.toExperienceDTO())

        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
        return result
    }

    suspend fun saveLink(linkCreateDTO: LinkCreateDTO): Resource<LinkDTO> {
        var result: Resource<LinkDTO> = Resource.Loading()
        result = try {
            val token = preferences.getToken()!!

            val responseMessage = service.saveLink(
                linkCreateDTO = linkCreateDTO,
                token = token
            )

            return Resource.Success(responseMessage.`object`.toLinkDTO())

        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
        return result
    }


    suspend fun updateCourse(courseUpdateDTO: CourseUpdateDTO): Resource<CourseDTO> {
        var result: Resource<CourseDTO> = Resource.Loading()
        result = try {

            val token = preferences.getToken()!!

            val responseMessage = service.updateCourse(
                courseUpdateDTO = courseUpdateDTO,
                token = token
            )

            return Resource.Success(responseMessage.`object`)

        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
        return result
    }


    suspend fun updateEducation(educationCreateDTO: EducationUpdateDTO): Resource<EducationDTO> {
        var result: Resource<EducationDTO> = Resource.Loading()
        result = try {

            val token = preferences.getToken()!!

            val responseMessage = service.updateEducation(
                educationUpdateDTO = educationCreateDTO,
                token = token
            )

            return Resource.Success(responseMessage.`object`)

        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
        return result
    }


    suspend fun updateExperience(experienceUpdateDTO: ExperienceUpdateDTO): Resource<ExperienceDTO> {
        var result: Resource<ExperienceDTO> = Resource.Loading()
        result = try {

            val token = preferences.getToken()!!

            val responseMessage = service.updateExperience(
                experienceUpdateDTO = experienceUpdateDTO,
                token = token
            )

            return Resource.Success(responseMessage.`object`)

        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
        return result
    }

    suspend fun updateLink(linkUpdateDTO: LinkUpdateDTO): Resource<LinkDTO> {
        var result: Resource<LinkDTO> = Resource.Loading()
        result = try {

            val token = preferences.getToken()!!

            val responseMessage = service.updateLink(
                linkUpdateDTO = linkUpdateDTO,
                token = token
            )

            return Resource.Success(responseMessage.`object`)

        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
        return result
    }

    suspend fun updateUserProfile(updateDTO: UserProfileUpdateDTO): Resource<UserProfileDTO> {
        return try {
            val token = preferences.getToken() ?: throw IllegalStateException("Token is null")

            val responseMessage = service.updateUserProfile(
                token = token,
                userId = UUID.fromString(updateDTO.userId),
                aboutMe = updateDTO.aboutMe,
            )

            Resource.Success(responseMessage.`object`)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun findUserProfile(): Resource<UserWithProfileDTO> {
        val token = preferences.getToken()!!
        val userId = preferences.getUserId()!!
        return try {
            val responseMessage = service.findUserProfileByUserId(
                userId = UUID.fromString(userId),
                token = token
            )

            Resource.Success(responseMessage.`object`!!)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }
}