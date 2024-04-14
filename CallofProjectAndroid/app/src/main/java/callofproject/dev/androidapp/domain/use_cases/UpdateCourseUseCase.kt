package callofproject.dev.androidapp.domain.use_cases

import callofproject.dev.androidapp.data.remote.ICallOfProjectService
import callofproject.dev.androidapp.domain.dto.user_profile.course.CourseDTO
import callofproject.dev.androidapp.domain.dto.user_profile.course.CourseUpdateDTO
import callofproject.dev.androidapp.domain.preferences.IPreferences
import callofproject.dev.androidapp.util.Resource
import javax.inject.Inject

class UpdateCourseUseCase @Inject constructor(
    private val service: ICallOfProjectService,
    private val preferences: IPreferences
) {

    suspend operator fun invoke(courseUpdateDTO: CourseUpdateDTO): Resource<CourseDTO> {
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
}