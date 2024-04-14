package callofproject.dev.androidapp.domain.use_cases

import callofproject.dev.androidapp.data.mapper.toLinkDTO
import callofproject.dev.androidapp.data.remote.ICallOfProjectService
import callofproject.dev.androidapp.domain.dto.user_profile.link.LinkCreateDTO
import callofproject.dev.androidapp.domain.dto.user_profile.link.LinkDTO
import callofproject.dev.androidapp.domain.preferences.IPreferences
import callofproject.dev.androidapp.util.Resource
import javax.inject.Inject

class SaveLinkUseCase @Inject constructor(
    private val service: ICallOfProjectService,
    private val preferences: IPreferences
) {

    suspend operator fun invoke(linkCreateDTO: LinkCreateDTO): Resource<LinkDTO> {
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
}