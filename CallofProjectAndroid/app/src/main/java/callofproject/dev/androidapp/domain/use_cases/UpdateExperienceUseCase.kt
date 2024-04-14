package callofproject.dev.androidapp.domain.use_cases

import callofproject.dev.androidapp.data.remote.ICallOfProjectService
import callofproject.dev.androidapp.domain.dto.user_profile.experience.ExperienceDTO
import callofproject.dev.androidapp.domain.dto.user_profile.experience.ExperienceUpdateDTO
import callofproject.dev.androidapp.domain.preferences.IPreferences
import callofproject.dev.androidapp.util.Resource
import javax.inject.Inject

class UpdateExperienceUseCase @Inject constructor(
    private val service: ICallOfProjectService,
    private val preferences: IPreferences
) {

    suspend operator fun invoke(experienceUpdateDTO: ExperienceUpdateDTO): Resource<ExperienceDTO> {
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
}