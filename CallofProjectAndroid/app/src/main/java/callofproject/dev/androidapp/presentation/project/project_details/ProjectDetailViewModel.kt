package callofproject.dev.androidapp.presentation.project.project_details

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
class ProjectDetailViewModel @Inject constructor(
    private val useCases: UseCaseFacade,
    private val preferences: IPreferences
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(ProjectDetailsState())
        private set


    fun findProjectDetails(projectId: String) {
        viewModelScope.launch {
            useCases.projectDetails(projectId).let { result ->
                when (result) {
                    is Resource.Success -> {
                        state = state.copy(
                            projectDetailsDTO = result.data!!,
                            isOwner = true,
                            isParticipant = true
                        )
                    }

                    is Resource.Error -> {
                        if (result.message == "You are not the owner or participant of this project") {
                            state = state.copy(isOwner = false, isParticipant = false)
                            _uiEvent.send(UiEvent.ShowSnackbar(UiText.StringResource(R.string.msg_notOwnerOfProject)))
                        }
                    }

                    is Resource.Loading -> {
                        //_uiEvent.send(UiEvent.ShowSnackbar("Loading"))
                    }
                }
            }
        }
    }
}