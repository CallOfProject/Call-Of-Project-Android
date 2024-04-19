package callofproject.dev.androidapp.domain.use_cases

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.domain.dto.NotificationDTO
import callofproject.dev.androidapp.websocket.ApprovalReceiver
import javax.inject.Inject

class NotificationUseCase @Inject constructor(private val context: Context) {

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Channel Name"
            val descriptionText = "Channel Description"
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

    fun createApprovalNotification(message: NotificationDTO) {
        createNotificationChannel()

        val approveIntent = Intent(context, ApprovalReceiver::class.java)
        approveIntent.action = "APPROVE_ACTION"
        val approvePendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            approveIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )


        val approveAction = NotificationCompat.Action.Builder(
            R.drawable.completed_task_icon,
            "Onayla",
            approvePendingIntent
        ).build()

        val rejectIntent = Intent(context, ApprovalReceiver::class.java)
        rejectIntent.action = "REJECT_ACTION"
        val rejectPendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            rejectIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val rejectAction = NotificationCompat.Action.Builder(
            R.drawable.close_icon,
            "Reddet",
            rejectPendingIntent
        ).build()

        val builder =
            NotificationCompat.Builder(context, context.getString(R.string.channel_id))
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(message.notificationTitle)
                .setContentText(message.message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(approveAction)
                .addAction(rejectAction)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(/*notificationId*/0, builder.build())

    }
}