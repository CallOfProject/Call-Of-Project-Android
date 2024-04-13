package callofproject.dev.androidapp.domain.use_cases

import callofproject.dev.androidapp.data.remote.ICallOfProjectService
import callofproject.dev.androidapp.domain.dto.UserWithProfileDTO
import callofproject.dev.androidapp.domain.preferences.IPreferences
import callofproject.dev.androidapp.util.Resource
import java.util.UUID
import javax.inject.Inject

class FindUserProfileUseCase @Inject constructor(
    private val service: ICallOfProjectService,
    private val preferences: IPreferences
) {
    suspend operator fun invoke(userId: String): Resource<UserWithProfileDTO> {
        val token = preferences.getToken()!!
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