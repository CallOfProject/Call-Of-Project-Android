package callofproject.dev.androidapp.presentation.project.project_overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.domain.preferences.IPreferences
import callofproject.dev.androidapp.domain.use_cases.UseCaseFacade
import callofproject.dev.androidapp.util.Resource
import callofproject.dev.androidapp.util.route.UiEvent
import callofproject.dev.androidapp.util.route.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectOverviewViewModel @Inject constructor(
    private val projectUseCase: UseCaseFacade,
    private val pref: IPreferences
) : ViewModel() {


    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(ProjectOverviewState())
        private set


    fun onEvent(event: ProjectOverviewEvent) {
        when (event) {
            is ProjectOverviewEvent.OnSendJoinRequestClick -> sendProjectParticipantRequest(event.projectId)
        }
    }

    private fun sendProjectParticipantRequest(projectId: String) {
        viewModelScope.launch {
            projectUseCase.project.sendProjectJoinRequest(projectId).let { result ->
                when (result) {
                    is Resource.Success -> {
                        _uiEvent.send(UiEvent.ShowSnackbar(UiText.StringResource(R.string.request_sent)))
                    }

                    is Resource.Error -> {
                        _uiEvent.send(UiEvent.ShowSnackbar(UiText.DynamicString(result.message!!)))
                    }

                    is Resource.Loading -> {}
                }
            }
        }
    }

    internal fun findProjectOverview(projectId: String) {
        viewModelScope.launch {
            projectUseCase.project.findProjectOverview(projectId).let { result ->
                when (result) {
                    is Resource.Success -> {
                        state = state.copy(projectOverviewDTO = result.data!!)
                    }

                    is Resource.Error -> {}

                    is Resource.Loading -> {}
                }
            }
        }
    }

    internal fun getUsername(): String = pref.getUsername()!!
}