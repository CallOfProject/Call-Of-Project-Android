package callofproject.dev.androidapp.domain.use_cases

import android.util.Log
import callofproject.dev.androidapp.data.remote.ICallOfProjectService
import callofproject.dev.androidapp.domain.dto.connection.UserConnectionDTO
import callofproject.dev.androidapp.domain.preferences.IPreferences
import callofproject.dev.androidapp.util.Resource
import java.util.UUID
import javax.inject.Inject

class CommunicationUseCase @Inject constructor(
    private val pref: IPreferences,
    private val service: ICallOfProjectService,
) {

    suspend fun sendCommunicationRequest(friendId: UUID): Resource<Boolean> {
        return try {
            val responseMessage = service.sendConnectionRequest(
                userId = UUID.fromString(pref.getUserId()!!),
                connectionId = friendId,
                token = pref.getToken()!!
            )

            if (responseMessage.statusCode == 2002)
                return Resource.Error(responseMessage.message)

            return Resource.Success(responseMessage.`object`)

        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun answerConnectionRequest(
        friendId: String,
        isAccepted: Boolean,
        notificationId: String? = ""
    ): Resource<Boolean> {
        return try {

            val responseMessage = notificationId?.let {
                service.answerConnectionRequest(
                    userId = UUID.fromString(pref.getUserId()!!),
                    connectionId = UUID.fromString(friendId),
                    isAccepted = isAccepted,
                    notificationId = it,
                    token = pref.getToken()!!
                )
            } ?: run {
                service.answerConnectionRequest(
                    userId = UUID.fromString(pref.getUserId()!!),
                    connectionId = UUID.fromString(friendId),
                    isAccepted = isAccepted,
                    token = pref.getToken()!!
                )
            }

            Resource.Success(responseMessage.`object`)

        } catch (e: Exception) {
            Log.d("ErrorInC", e.message.toString())
            Resource.Error(e.message ?: "An error occurred")
        }
    }


    suspend fun removeConnection(friendId: String): Resource<Boolean> {
        return try {

            val responseMessage = service.removeConnection(
                userId = UUID.fromString(pref.getUserId()!!),
                connectionId = UUID.fromString(friendId),
                token = pref.getToken()!!
            )

            Resource.Success(responseMessage.`object`)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unexpected error occurred")
        }
    }

    suspend fun blockConnection(friendId: String): Resource<Boolean> {
        return try {

            val responseMessage = service.blockConnection(
                userId = UUID.fromString(pref.getUserId()!!),
                connectionId = UUID.fromString(friendId),
                token = pref.getToken()!!
            )

            Resource.Success(responseMessage.`object`)

        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unexpected error occurred")
        }
    }

    suspend fun unblockConnection(friendId: String): Resource<Boolean> {
        return try {

            val responseMessage = service.unblockConnection(
                userId = UUID.fromString(pref.getUserId()!!),
                connectionId = UUID.fromString(friendId),
                token = pref.getToken()!!
            )

            Resource.Success(responseMessage.`object`)

        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun findConnections(): Resource<List<UserConnectionDTO>> {
        return try {

            val responseMessage = service.findConnections(
                userId = UUID.fromString(pref.getUserId()!!),
                token = pref.getToken()!!
            )

            Resource.Success(responseMessage.`object`.users)

        } catch (e: Exception) {
            Log.d("Error", e.message.toString())
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun findBlockedConnections(): Resource<List<UserConnectionDTO>> {
        return try {

            val responseMessage = service.findBlockedConnections(
                userId = UUID.fromString(pref.getUserId()!!),
                token = pref.getToken()!!
            )

            Resource.Success(responseMessage.`object`.users)

        } catch (e: Exception) {
            Log.d("Error", e.message.toString())
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun findPendingConnections(): Resource<List<UserConnectionDTO>> {
        return try {

            val responseMessage = service.findConnectionRequests(
                userId = UUID.fromString(pref.getUserId()!!),
                token = pref.getToken()!!
            )

            Resource.Success(responseMessage.`object`.users)

        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }
}