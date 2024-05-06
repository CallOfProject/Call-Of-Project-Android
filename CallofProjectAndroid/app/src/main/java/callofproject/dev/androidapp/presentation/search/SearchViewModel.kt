package callofproject.dev.androidapp.presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import callofproject.dev.androidapp.di.interceptor.LocalDateFormatterInterceptor
import callofproject.dev.androidapp.domain.dto.search.SearchUserAndProjectResponse
import callofproject.dev.androidapp.domain.preferences.IPreferences
import callofproject.dev.androidapp.domain.use_cases.UseCaseFacade
import callofproject.dev.androidapp.presentation.main_page.SORT_APPLICATION_DEADLINE
import callofproject.dev.androidapp.presentation.main_page.SORT_CREATION_DATE
import callofproject.dev.androidapp.presentation.main_page.SORT_EXPECTED_COMPLETION_DATE
import callofproject.dev.androidapp.presentation.main_page.SORT_START_DATE
import callofproject.dev.androidapp.presentation.main_page.SORT_TYPE_ASC
import callofproject.dev.androidapp.presentation.main_page.SORT_TYPE_DESC
import callofproject.dev.androidapp.util.Resource
import callofproject.dev.androidapp.util.route.Route
import callofproject.dev.androidapp.util.route.UiEvent
import callofproject.dev.androidapp.util.route.UiText
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
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val pref: IPreferences,
    private val useCaseFacade: UseCaseFacade,
    @LocalDateFormatterInterceptor private val dateTimeFormatter: DateTimeFormatter
) : ViewModel() {
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(SearchState())
        private set

    private var searchJob: Job? = null


    init {
        pref.clearFilterObjects()
    }

    fun onEvent(event: SearchEvent) = when (event) {
        is SearchEvent.OnProjectClick -> navigateToProjectOverview(event.projectId)
        is SearchEvent.OnUserClick -> navigateToUserOverview(event.userId)
        is SearchEvent.OnAddConnectionClick -> sendConnectionRequest(event.userId)
        is SearchEvent.OnSortProjects -> sortByOption(event.sortOption)
        is SearchEvent.OnSortType -> sortByType(event.sortType, event.sortOption)
    }

    private fun sortByOption(sortOption: String) {
        when (sortOption) {
            SORT_CREATION_DATE -> {
                state = state.copy(
                    searchResult = state.searchResult.copy(
                        projects = state.searchResult.projects.copy(
                            projects = state.searchResult.projects.projects.sortedByDescending {
                                toLocalDate(it.creationDate)
                            }
                        )
                    )
                )
            }

            SORT_START_DATE -> {
                state = state.copy(
                    searchResult = state.searchResult.copy(
                        projects = state.searchResult.projects.copy(
                            projects = state.searchResult.projects.projects.sortedByDescending {
                                toLocalDate(it.startDate)
                            }
                        )
                    )
                )
            }

            SORT_APPLICATION_DEADLINE -> {
                state = state.copy(
                    searchResult = state.searchResult.copy(
                        projects = state.searchResult.projects.copy(
                            projects = state.searchResult.projects.projects.sortedByDescending {
                                toLocalDate(it.applicationDeadline)
                            }
                        )
                    )
                )
            }

            SORT_EXPECTED_COMPLETION_DATE -> {
                state = state.copy(
                    searchResult = state.searchResult.copy(
                        projects = state.searchResult.projects.copy(
                            projects = state.searchResult.projects.projects.sortedByDescending {
                                toLocalDate(it.expectedCompletionDate)
                            }
                        )
                    )
                )
            }
        }
    }

    private fun sortByType(sortType: String, sortOption: String) {
        when (sortType) {
            SORT_TYPE_ASC -> {
                state = state.copy(
                    searchResult = state.searchResult.copy(
                        projects = state.searchResult.projects.copy(
                            projects = state.searchResult.projects.projects.sortedBy {
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
                    )
                )
            }

            SORT_TYPE_DESC -> {
                state = state.copy(
                    searchResult = state.searchResult.copy(
                        projects = state.searchResult.projects.copy(
                            projects = state.searchResult.projects.projects.sortedByDescending {
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
                    )
                )
            }
        }
    }

    private fun sendConnectionRequest(userId: UUID) {
        viewModelScope.launch {
            useCaseFacade.communicationUseCase.sendCommunicationRequest(userId).let {
                when (it) {
                    is Resource.Success -> {
                        _uiEvent.send(UiEvent.ShowSnackbar(UiText.DynamicString("Connection request sent")))
                    }

                    is Resource.Error -> {
                        _uiEvent.send(
                            UiEvent.ShowSnackbar(
                                UiText.DynamicString(
                                    it.message ?: "Connection request failed"
                                )
                            )
                        )
                    }

                    is Resource.Loading -> {
                        // do nothing
                    }
                }

            }
        }
    }

    fun search(keyword: String) {
        searchJob?.cancel()
        state = state.copy(isLoading = true)
        searchJob = viewModelScope.launch {
            useCaseFacade.search(keyword).onStart { delay(50L) }.onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false,
                            searchResult = result.data!!.`object`
                        )
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            error = result.message!!,
                            searchResult = SearchUserAndProjectResponse()
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    fun getCurrentUsername(): String = pref.getUsername()!!

    private fun navigateToUserOverview(userId: String) {
        viewModelScope.launch {
            _uiEvent.send(UiEvent.Navigate("${Route.USER_OVERVIEW}/$userId"))
        }
    }

    private fun navigateToProjectOverview(projectId: String) {
        viewModelScope.launch {
            _uiEvent.send(UiEvent.Navigate("${Route.PROJECT_OVERVIEW}/$projectId/0"))
        }
    }

    private fun toLocalDate(date: String): LocalDate = LocalDate.parse(date, dateTimeFormatter)
}