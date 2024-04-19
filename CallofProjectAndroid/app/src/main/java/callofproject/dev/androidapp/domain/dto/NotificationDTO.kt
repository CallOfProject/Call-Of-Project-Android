package callofproject.dev.androidapp.domain.dto

import java.util.UUID

data class NotificationDTO(
    val notificationId: String?,
    val toUserId: UUID?,
    val fromUserId: UUID?,
    val message: String?,
    val notificationType: NotificationType?,
    val notificationData: Any?,
    val notificationLink: String?,
    val notificationImage: String?,
    val notificationTitle: String?,
    val createdAt: String?,
    val notificationDataType: NotificationDataType?,
    val requestId: UUID?,
    val notificationApproveLink: String?,
    val notificationRejectLink: String?
)
