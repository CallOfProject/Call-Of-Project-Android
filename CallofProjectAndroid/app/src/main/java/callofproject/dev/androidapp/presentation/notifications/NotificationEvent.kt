package callofproject.dev.androidapp.presentation.notifications

import callofproject.dev.androidapp.domain.dto.NotificationDTO

sealed class NotificationEvent {
    data class OnAcceptProjectJoinRequest(val notificationDTO: NotificationDTO) :
        NotificationEvent()

    data class OnRejectProjectJoinRequest(val notificationDTO: NotificationDTO) :
        NotificationEvent()

    data class OnAcceptConnectionRequest(val notificationDTO: NotificationDTO) :
        NotificationEvent()

    data class OnRejectConnectionRequest(val notificationDTO: NotificationDTO) : NotificationEvent()

    data object OnMarkAllAsReadClicked : NotificationEvent()

    data object OnClearAllClicked : NotificationEvent()
}