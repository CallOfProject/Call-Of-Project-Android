package callofproject.dev.androidapp.domain.use_cases

import android.util.Log
import callofproject.dev.androidapp.data.mapper.toEducationDTO
import callofproject.dev.androidapp.data.remote.ICallOfProjectService
import callofproject.dev.androidapp.domain.dto.user_profile.education.EducationDTO
import callofproject.dev.androidapp.domain.dto.user_profile.education.EducationCreateDTO
import callofproject.dev.androidapp.domain.preferences.IPreferences
import callofproject.dev.androidapp.util.Resource
import javax.inject.Inject

class SaveEducationUseCase @Inject constructor(
    private val service: ICallOfProjectService,
    private val preferences: IPreferences
) {

    suspend operator fun invoke(educationCreateDTO: EducationCreateDTO): Resource<EducationDTO> {
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
}