package callofproject.dev.androidapp.domain.use_cases

import android.util.Log
import callofproject.dev.androidapp.data.remote.ICallOfProjectService
import callofproject.dev.androidapp.domain.dto.UserProfileUpdateDTO
import callofproject.dev.androidapp.domain.dto.user_profile.UserProfileDTO
import callofproject.dev.androidapp.domain.preferences.IPreferences
import callofproject.dev.androidapp.util.Resource
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.UUID
import javax.inject.Inject

class UpdateUserProfileUseCase @Inject constructor(
    private val service: ICallOfProjectService,
    private val preferences: IPreferences
) {

    suspend operator fun invoke(updateDTO: UserProfileUpdateDTO): Resource<UserProfileDTO> {
        return try {
            // Kullanıcı tokenini al
            val token = preferences.getToken() ?: throw IllegalStateException("Token is null")

            // Servis çağrısını yap ve cevabı al
            val responseMessage = service.updateUserProfile(
                token = token,
                userId = UUID.fromString(updateDTO.userId),
                aboutMe = updateDTO.aboutMe,
            )

            Resource.Success(responseMessage.`object`)
        } catch (e: Exception) {
            // Hata durumunda Resource.Error döndür
            Resource.Error(e.message ?: "An error occurred")
        }
    }

}