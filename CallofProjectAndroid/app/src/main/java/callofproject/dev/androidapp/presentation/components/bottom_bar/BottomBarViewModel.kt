package callofproject.dev.androidapp.presentation.components.bottom_bar

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import callofproject.dev.androidapp.domain.dto.NotificationDTO
import callofproject.dev.androidapp.domain.use_cases.UseCaseFacade
import callofproject.dev.androidapp.util.Resource
import callofproject.dev.androidapp.util.route.UiEvent
import callofproject.dev.androidapp.util.route.UiEvent.Navigate
import callofproject.dev.androidapp.websocket.WebSocketClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomBarViewModel @Inject constructor(
    private val useCaseFacade: UseCaseFacade,
    private val webSocketClient: WebSocketClient
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(BottomBarState())
        private set

    val notificationFlow: SharedFlow<NotificationDTO> = webSocketClient.notificationFlow

    companion object {
        var selectedItemIndex = mutableStateOf(0)
    }

    init {
        receiveNotification()
    }

    private fun receiveNotification() {
        viewModelScope.launch {
            notificationFlow.collect { _ ->
                state = state.copy(unReadNotificationsCount = state.unReadNotificationsCount + 1)
            }
        }
    }

    fun onEvent(event: BottomBarEvent) = when (event) {
        is BottomBarEvent.Navigate -> onNavigate(event.route)
    }

    private fun onNavigate(route: String) {
        viewModelScope.launch {
            _uiEvent.send(Navigate(route))
        }
    }

    fun findAllUnReadNotifications() {
        viewModelScope.launch {
            useCaseFacade.notification.findAllUnreadNotificationCount()
                .onStart { delay(50L) }
                .onEach { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            state = state.copy(unReadNotificationsCount = resource.data ?: 0)
                        }

                        is Resource.Error -> {
                            state = state.copy(unReadNotificationsCount = 0)
                        }

                        is Resource.Loading -> {

                        }
                    }
                }.launchIn(this)
        }
    }
}