package callofproject.dev.androidapp.presentation.notifications

import callofproject.dev.androidapp.domain.dto.NotificationDTO

data class NotificationState(
    val isLoading: Boolean = false,
    val notifications: List<NotificationDTO> = emptyList()
)
