package callofproject.dev.androidapp.domain.use_cases

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.data.remote.ICallOfProjectService
import callofproject.dev.androidapp.domain.dto.MultipleResponseMessagePageable
import callofproject.dev.androidapp.domain.dto.NotificationDTO
import callofproject.dev.androidapp.domain.preferences.IPreferences
import callofproject.dev.androidapp.util.Resource
import callofproject.dev.androidapp.util.Resource.Error
import callofproject.dev.androidapp.util.Resource.Loading
import callofproject.dev.androidapp.util.Resource.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject

class NotificationUseCase @Inject constructor(
    private val context: Context,
    private val pref: IPreferences,
    private val service: ICallOfProjectService,
) {

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.channel_name)
            val descriptionText = context.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(
                context.getString(R.string.channel_id),
                name,
                importance
            ).apply {
                description = descriptionText
            }
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    suspend fun findAllNotificationsPageable(page: Int): Flow<Resource<MultipleResponseMessagePageable<List<NotificationDTO>>>> {
        return flow {

            emit(Loading())

            try {

                val response = service.findNotifications(
                    userId = UUID.fromString(pref.getUserId()!!),
                    page = page,
                    token = pref.getToken()!!
                )

                emit(Success(response))

            } catch (e: Exception) {
                emit(Error(e.message ?: "An unexpected error occurred"))
            }
        }
    }


    fun createApprovalNotification(message: NotificationDTO) {
        createNotificationChannel()

        val builder =
            NotificationCompat.Builder(context, context.getString(R.string.channel_id))
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(message.notificationTitle)
                .setContentText(message.message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSmallIcon(R.drawable.cop_logo)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(/*notificationId*/0, builder.build())
    }


    suspend fun markAllNotificationsRead(): Flow<Resource<Boolean>> = flow {
        emit(Loading())
        try {
            val response = service.markAllNotificationsRead(
                userId = UUID.fromString(pref.getUserId()!!),
                token = pref.getToken()!!
            )
            emit(Success(response.`object`))
        } catch (e: Exception) {
            emit(Error(e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun deleteAllNotifications(): Flow<Resource<Boolean>> = flow {
        emit(Loading())
        try {
            val response = service.deleteAllNotifications(
                userId = UUID.fromString(pref.getUserId()!!),
                token = pref.getToken()!!
            )
            emit(Success(response.`object`))
        } catch (e: Exception) {
            emit(Error(e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun findAllUnreadNotificationCount(): Flow<Resource<Long>> = flow {
        emit(Loading())
        try {
            val response = service.findAllUnreadNotificationCount(
                userId = UUID.fromString(pref.getUserId()!!),
                token = pref.getToken()!!
            )
            emit(Success(response.`object`))
        } catch (e: Exception) {
            Log.e("NotificationUseCase", "Error", e)
            emit(Error(e.message ?: "An unexpected error occurred"))
        }
    }
}