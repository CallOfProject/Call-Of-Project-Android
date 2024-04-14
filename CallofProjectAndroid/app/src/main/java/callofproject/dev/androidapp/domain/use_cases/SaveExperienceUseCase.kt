package callofproject.dev.androidapp.domain.use_cases

import callofproject.dev.androidapp.data.mapper.toExperienceDTO
import callofproject.dev.androidapp.data.remote.ICallOfProjectService
import callofproject.dev.androidapp.domain.dto.user_profile.experience.ExperienceCreateDTO
import callofproject.dev.androidapp.domain.dto.user_profile.experience.ExperienceDTO
import callofproject.dev.androidapp.domain.preferences.IPreferences
import callofproject.dev.androidapp.util.Resource
import javax.inject.Inject

class SaveExperienceUseCase @Inject constructor(
    private val service: ICallOfProjectService,
    private val preferences: IPreferences
) {

    suspend operator fun invoke(experienceCreateDTO: ExperienceCreateDTO): Resource<ExperienceDTO> {
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
}