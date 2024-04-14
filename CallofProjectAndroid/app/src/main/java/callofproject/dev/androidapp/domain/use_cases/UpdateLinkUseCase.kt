package callofproject.dev.androidapp.domain.use_cases

import callofproject.dev.androidapp.data.remote.ICallOfProjectService
import callofproject.dev.androidapp.domain.dto.user_profile.education.EducationDTO
import callofproject.dev.androidapp.domain.dto.user_profile.education.EducationUpdateDTO
import callofproject.dev.androidapp.domain.dto.user_profile.link.LinkDTO
import callofproject.dev.androidapp.domain.dto.user_profile.link.LinkUpdateDTO
import callofproject.dev.androidapp.domain.preferences.IPreferences
import callofproject.dev.androidapp.util.Resource
import javax.inject.Inject

class UpdateLinkUseCase @Inject constructor(
    private val service: ICallOfProjectService,
    private val preferences: IPreferences
) {

    suspend operator fun invoke(linkUpdateDTO: LinkUpdateDTO): Resource<LinkDTO> {
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
}