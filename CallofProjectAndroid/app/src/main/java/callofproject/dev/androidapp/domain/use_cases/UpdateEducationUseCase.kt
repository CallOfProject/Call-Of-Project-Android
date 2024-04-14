package callofproject.dev.androidapp.domain.use_cases

import callofproject.dev.androidapp.data.remote.ICallOfProjectService
import callofproject.dev.androidapp.domain.dto.user_profile.education.EducationDTO
import callofproject.dev.androidapp.domain.dto.user_profile.education.EducationUpdateDTO
import callofproject.dev.androidapp.domain.preferences.IPreferences
import callofproject.dev.androidapp.util.Resource
import javax.inject.Inject

class UpdateEducationUseCase @Inject constructor(
    private val service: ICallOfProjectService,
    private val preferences: IPreferences
) {

    suspend operator fun invoke(educationCreateDTO: EducationUpdateDTO): Resource<EducationDTO> {
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
}