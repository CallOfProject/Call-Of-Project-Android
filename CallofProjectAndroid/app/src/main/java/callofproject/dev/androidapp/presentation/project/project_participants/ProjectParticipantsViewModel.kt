package callofproject.dev.androidapp.presentation.project.project_participants

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.domain.dto.ResponseMessage
import callofproject.dev.androidapp.domain.preferences.IPreferences
import callofproject.dev.androidapp.domain.use_cases.UseCaseFacade
import callofproject.dev.androidapp.util.Resource
import callofproject.dev.androidapp.util.route.UiEvent
import callofproject.dev.androidapp.util.route.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ProjectParticipantsViewModel @Inject constructor(
    private val useCaseFacade: UseCaseFacade,
    private val pref: IPreferences
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(ProjectParticipantsState())
        private set

    fun onEvent(event: ProjectParticipantsEvent) = when (event) {
        is ProjectParticipantsEvent.OnClickRemoveParticipant -> removeParticipant(
            event.userId,
            event.projectId
        )
    }


    private fun removeParticipant(userId: String, projectId: String) {
        viewModelScope.launch {
            useCaseFacade.project.removeProjectParticipant(
                UUID.fromString(userId),
                UUID.fromString(projectId)
            ).let { result ->
                when (result) {
                    is Resource.Success -> {
                        state = state.copy(
                            participants = state.participants.filter { it.user_id != userId }
                        )
                        _uiEvent.send(UiEvent.ShowToastMessage(UiText.StringResource(R.string.msg_participant_removed)))
                    }

                    is Resource.Error -> {
                        _uiEvent.send(UiEvent.ShowToastMessage(UiText.StringResource(R.string.msg_participant_removed_fail)))
                    }

                    else -> Unit
                }
            }
        }

    }


    fun findProjectParticipants(projectId: String) {
        viewModelScope.launch {
            useCaseFacade.project.findProjectOverview(projectId).let { result ->
                when (result) {
                    is Resource.Success -> {
                        val project = result.data!!
                        state = if (project.projectOwnerName == getUsername())
                            state.copy(
                                participants = project.projectParticipants,
                                isParticipantOrOwner = true,
                                isOwner = true
                            )
                        else if (project.projectOwnerName == getUsername() || project.projectParticipants.any { it.username == getUsername() })
                            state.copy(
                                participants = project.projectParticipants,
                                isParticipantOrOwner = true,

                                )
                        else state.copy(isParticipantOrOwner = false, isOwner = false)
                    }

                    is Resource.Error -> {}

                    is Resource.Loading -> {}
                }
            }
        }
    }


    private fun getUsername() = pref.getUsername()
}