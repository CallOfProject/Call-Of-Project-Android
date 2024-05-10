package callofproject.dev.androidapp.presentation.main_page

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.di.interceptor.LocalDateFormatterInterceptor
import callofproject.dev.androidapp.domain.dto.project.ProjectDiscoveryDTO
import callofproject.dev.androidapp.domain.preferences.IPreferences
import callofproject.dev.androidapp.domain.use_cases.UseCaseFacade
import callofproject.dev.androidapp.util.Resource
import callofproject.dev.androidapp.util.route.Route
import callofproject.dev.androidapp.util.route.UiEvent
import callofproject.dev.androidapp.util.route.UiText
import callofproject.dev.androidapp.websocket.WebSocketClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

const val SORT_CREATION_DATE = "Creation Date"
const val SORT_START_DATE = "Start Date"
const val SORT_APPLICATION_DEADLINE = "Application Deadline"
const val SORT_EXPECTED_COMPLETION_DATE = "Expected Comp. Date"

const val SORT_TYPE_ASC = "Ascending"
const val SORT_TYPE_DESC = "Descending"

@HiltViewModel
class MainPageViewModel @Inject constructor(
    private val useCases: UseCaseFacade,
    private val webSocketClient: WebSocketClient,
    private val pref: IPreferences,
    @LocalDateFormatterInterceptor private val dateTimeFormatter: DateTimeFormatter
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(MainPageState())
        private set

    private var findAllJob: Job? = null


    init {
        findProjectDiscovery()
        startWebSocket()
        pref.clearFilterObjects()
    }


    fun onEvent(event: MainPageEvent) = when (event) {
        is MainPageEvent.OnClickProjectDiscoveryItem -> navigateToProjectOverview(event.projectId)
        is MainPageEvent.OnSortProjects -> sortProjects(event.sortOption)
        is MainPageEvent.OnSortType -> sortByType(event.sortType, event.sortOption)
    }

    private fun sortByType(sortType: String, sortOption: String) {
        when (sortType) {
            SORT_TYPE_ASC -> {
                state = state.copy(
                    projectDiscoveryList = state.projectDiscoveryList.sortedBy {
                        toLocalDate(
                            when (sortOption) {
                                SORT_CREATION_DATE -> it.creationDate
                                SORT_START_DATE -> it.startDate
                                SORT_APPLICATION_DEADLINE -> it.applicationDeadline
                                SORT_EXPECTED_COMPLETION_DATE -> it.expectedCompletionDate
                                else -> it.creationDate
                            }
                        )
                    }
                )
            }

            SORT_TYPE_DESC -> {
                state = state.copy(
                    projectDiscoveryList = state.projectDiscoveryList.sortedByDescending {
                        toLocalDate(
                            when (sortOption) {
                                SORT_CREATION_DATE -> it.creationDate
                                SORT_START_DATE -> it.startDate
                                SORT_APPLICATION_DEADLINE -> it.applicationDeadline
                                SORT_EXPECTED_COMPLETION_DATE -> it.expectedCompletionDate
                                else -> it.creationDate
                            }
                        )
                    }
                )
            }
        }
    }

    // Default sort is by creation date and descending
    private fun sortProjects(sortType: String) {
        when (sortType) {
            SORT_CREATION_DATE -> {
                state = state.copy(
                    projectDiscoveryList = state.projectDiscoveryList.sortedByDescending {
                        toLocalDate(it.creationDate)
                    }
                )
            }

            SORT_START_DATE -> {
                state = state.copy(
                    projectDiscoveryList = state.projectDiscoveryList.sortedByDescending {
                        toLocalDate(it.startDate)
                    }
                )
            }

            SORT_APPLICATION_DEADLINE -> {
                state = state.copy(
                    projectDiscoveryList = state.projectDiscoveryList.sortedByDescending {
                        toLocalDate(it.applicationDeadline)
                    }
                )
            }

            SORT_EXPECTED_COMPLETION_DATE -> {
                state = state.copy(
                    projectDiscoveryList = state.projectDiscoveryList.sortedByDescending {
                        toLocalDate(it.expectedCompletionDate)
                    }
                )
            }
        }

    }

    private fun navigateToProjectOverview(projectId: String) {
        viewModelScope.launch {
            _uiEvent.send(UiEvent.Navigate("${Route.PROJECT_OVERVIEW}/${projectId}/0"))
        }
    }


    private fun findProjectDiscovery() {
        findAllJob?.cancel()
        state = state.copy(isLoading = true)
        findAllJob = viewModelScope.launch {
            useCases.project.findProjectDiscovery(1)
                .onStart { delay(50L) }
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


    private fun startWebSocket() = webSocketClient.connectWebSocket()

    private fun toLocalDate(date: String): LocalDate = LocalDate.parse(date, dateTimeFormatter)
}