package callofproject.dev.androidapp.presentation.main_page

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.domain.preferences.IPreferences
import callofproject.dev.androidapp.domain.use_cases.UseCaseFacade
import callofproject.dev.androidapp.util.Resource
import callofproject.dev.androidapp.util.route.Route
import callofproject.dev.androidapp.util.route.UiEvent
import callofproject.dev.androidapp.util.route.UiText
import callofproject.dev.androidapp.websocket.WebSocketClient
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import okhttp3.WebSocketListener
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor(
    private val useCases: UseCaseFacade,
    private val webSocketClient: WebSocketClient,
    private val pref: IPreferences,
    private val gson: Gson
) : ViewModel() {
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var webSocketListener: WebSocketListener? = null

    var state by mutableStateOf(MainPageState())
        private set

    private var findAllJob: Job? = null

    init {
        findProjectDiscovery()
        startWebSocket()
    }

    private fun findProjectDiscovery() {
        findAllJob?.cancel()
        state = state.copy(isLoading = true)
        findAllJob = viewModelScope.launch {
            useCases.project.findProjectDiscovery(1)
                .onStart { delay(500L) }
                .onEach { result ->
                    when (result) {
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoading = true,
                                error = "",
                                projectDiscoveryList = emptyList()
                            )
                        }

                        is Resource.Success -> {
                            state = state.copy(
                                projectDiscoveryList = result.data?.`object`?.projects
                                    ?: emptyList(),
                                isLoading = false,
                                error = ""
                            )
                        }

                        is Resource.Error -> {
                            _uiEvent.send(UiEvent.ShowSnackbar(UiText.StringResource(R.string.msg_project_discovery_fail)))
                            state = state.copy(
                                projectDiscoveryList = emptyList(),
                                isLoading = false,
                                error = result.message ?: ""
                            )
                        }
                    }
                }.launchIn(this)
        }
    }

    fun onEvent(event: MainPageEvent) = when (event) {
        is MainPageEvent.OnClickProjectDiscoveryItem -> {
            viewModelScope.launch {
                _uiEvent.send(UiEvent.Navigate("${Route.PROJECT_OVERVIEW}/${event.projectId}"))
            }
        }
    }

    private fun startWebSocket() {
        webSocketClient.connectWebSocket()
    }
}