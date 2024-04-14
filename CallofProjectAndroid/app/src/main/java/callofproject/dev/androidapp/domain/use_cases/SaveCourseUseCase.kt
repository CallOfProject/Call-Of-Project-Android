package callofproject.dev.androidapp.domain.use_cases

import android.util.Log
import callofproject.dev.androidapp.data.mapper.toCourseDTO
import callofproject.dev.androidapp.data.mapper.toExperienceDTO
import callofproject.dev.androidapp.data.remote.ICallOfProjectService
import callofproject.dev.androidapp.domain.dto.user_profile.course.CourseCreateDTO
import callofproject.dev.androidapp.domain.dto.user_profile.course.CourseDTO
import callofproject.dev.androidapp.domain.dto.user_profile.experience.ExperienceCreateDTO
import callofproject.dev.androidapp.domain.dto.user_profile.experience.ExperienceDTO
import callofproject.dev.androidapp.domain.preferences.IPreferences
import callofproject.dev.androidapp.util.Resource
import javax.inject.Inject

class SaveCourseUseCase @Inject constructor(
    private val service: ICallOfProjectService,
    private val preferences: IPreferences
) {

    suspend operator fun invoke(courseCreateDTO: CourseCreateDTO): Resource<CourseDTO> {
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
}