package callofproject.dev.androidapp.presentation.notifications

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.domain.dto.NotificationDTO
import callofproject.dev.androidapp.domain.preferences.IPreferences
import callofproject.dev.androidapp.domain.use_cases.UseCaseFacade
import callofproject.dev.androidapp.util.Resource
import callofproject.dev.androidapp.util.route.UiEvent
import callofproject.dev.androidapp.util.route.UiEvent.ShowSnackbar
import callofproject.dev.androidapp.util.route.UiText
import callofproject.dev.androidapp.util.route.UiText.StringResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val useCaseFacade: UseCaseFacade,
    private val pref: IPreferences
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    var state by mutableStateOf(NotificationState())
        private set

    private var job: Job? = null

    init {
        getNotifications()
        pref.clearFilterObjects()
        findAllUnReadNotifications()
    }

    fun onEvent(event: NotificationEvent) = when (event) {
        is NotificationEvent.OnAcceptProjectJoinRequest -> acceptProjectJoinRequest(event.notificationDTO)
        is NotificationEvent.OnRejectProjectJoinRequest -> rejectProjectJoinRequest(event.notificationDTO)
        is NotificationEvent.OnMarkAllAsReadClicked -> markAllAsRead()
        is NotificationEvent.OnClearAllClicked -> clearAll()
        is NotificationEvent.OnAcceptConnectionRequest -> acceptConnectionRequest(event.notificationDTO)
        is NotificationEvent.OnRejectConnectionRequest -> rejectConnectionRequest(event.notificationDTO)
    }

    private fun rejectConnectionRequest(notificationDTO: NotificationDTO) {
        answerConnectionRequest(notificationDTO, false, notificationDTO.notificationId!!)
    }

    private fun acceptConnectionRequest(notificationDTO: NotificationDTO) {
        answerConnectionRequest(notificationDTO, true, notificationDTO.notificationId!!)
    }

    private fun answerConnectionRequest(
        notificationDTO: NotificationDTO,
        isAccepted: Boolean,
        notificationId: String
    ) {
        viewModelScope.launch {
            useCaseFacade.communicationUseCase.answerConnectionRequest(
                notificationDTO.fromUserId.toString(),
                isAccepted,
                notificationId
            )
                .let {
                    when (it) {
                        is Resource.Success -> {
                            if (isAccepted)
                                _uiEvent.send(ShowSnackbar(UiText.DynamicString("Connection request accepted")))
                            else
                                _uiEvent.send(ShowSnackbar(UiText.DynamicString("Connection request rejected")))
                        }

                        is Resource.Error -> {
                            _uiEvent.send(ShowSnackbar(StringResource(R.string.error_occurred)))
                        }

                        is Resource.Loading -> {
                            //_uiEvent.send(UiEvent.ShowSnackbar("Loading"))
                        }
                    }

                }
        }
    }

   private fun findAllUnReadNotifications() {
        viewModelScope.launch {
            useCaseFacade.notification.findAllUnreadNotificationCount()
                .onStart { delay(500L) }
                .onEach { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            state = state.copy(
                                unReadNotificationsCount = resource.data ?: 0,
                                isLoading = false,
                            )
                        }

                        is Resource.Error -> {
                            state = state.copy(
                                isLoading = false,
                                notifications = emptyList()
                            )
                        }

                        is Resource.Loading -> {
                            state = state.copy(
                                isLoading = true,
                                notifications = emptyList()
                            )
                        }
                    }
                }.launchIn(this)
        }
    }

    private fun clearAll() {
        viewModelScope.launch {
            useCaseFacade.notification.deleteAllNotifications()
                .onStart { delay(50L) }
                .onEach { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            _uiEvent.send(ShowSnackbar(StringResource(R.string.notifications_cleared)))
                            getNotifications()
                        }

                        is Resource.Error -> {
                            _uiEvent.send(ShowSnackbar(StringResource(R.string.error_occurred)))
                        }

                        is Resource.Loading -> {
                            //_uiEvent.send(UiEvent.ShowSnackbar("Loading"))
                        }
                    }
                }.launchIn(this)
        }
    }

    private fun markAllAsRead() {
        viewModelScope.launch {
            useCaseFacade.notification.markAllNotificationsRead()
                .onStart { delay(100L) }
                .onEach { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            _uiEvent.send(ShowSnackbar(StringResource(R.string.notifications_marked_as_read)))
                            getNotifications()
                        }

                        is Resource.Error -> {
                            _uiEvent.send(ShowSnackbar(StringResource(R.string.error_occurred)))
                        }

                        is Resource.Loading -> {
                            //_uiEvent.send(UiEvent.ShowSnackbar("Loading"))
                        }
                    }
                }.launchIn(this)
        }
    }


    private fun rejectProjectJoinRequest(notificationDTO: NotificationDTO) {
        viewModelScope.launch {
            useCaseFacade.project.answerProjectJoinRequest(notificationDTO, false).let { result ->
                when (result) {
                    is Resource.Success -> {
                        _uiEvent.send(ShowSnackbar(StringResource(R.string.project_join_request_rejected)))
                    }

                    is Resource.Error -> {
                        _uiEvent.send(ShowSnackbar(StringResource(R.string.error_occurred)))
                    }

                    is Resource.Loading -> {
                        //_uiEvent.send(UiEvent.ShowSnackbar("Loading"))
                    }
                }
            }
        }
    }

    private fun acceptProjectJoinRequest(notificationDTO: NotificationDTO) {
        viewModelScope.launch {
            useCaseFacade.project.answerProjectJoinRequest(notificationDTO, true).let { result ->
                when (result) {
                    is Resource.Success -> {
                        _uiEvent.send(ShowSnackbar(StringResource(R.string.project_join_request_accepted)))
                    }

                    is Resource.Error -> {
                        _uiEvent.send(ShowSnackbar(StringResource(R.string.error_occurred)))
                    }

                    is Resource.Loading -> {
                        //_uiEvent.send(UiEvent.ShowSnackbar("Loading"))
                    }
                }
            }
        }
    }


    private fun getNotifications() {
        job?.cancel()
        state = state.copy(isLoading = true)
        job = viewModelScope.launch {
            useCaseFacade.notification.findAllNotificationsPageable(1)
                .onStart { delay(500L) }
                .onEach { resource ->
                    when (resource) {

                        is Resource.Success -> {
                            state = state.copy(
                                notifications = resource.data?.`object` ?: emptyList(),
                                isLoading = false,
                            )
                        }


                        is Resource.Loading -> {
                            state = state.copy(
                                isLoading = true,
                                notifications = emptyList()
                            )
                        }


                        is Resource.Error -> {
                            state = state.copy(
                                isLoading = false,
                                notifications = emptyList()
                            )
                        }
                    }

                }.launchIn(this)

        }
    }

}