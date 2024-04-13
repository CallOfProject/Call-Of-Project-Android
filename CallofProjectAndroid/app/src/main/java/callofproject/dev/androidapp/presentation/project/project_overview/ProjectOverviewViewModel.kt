package callofproject.dev.androidapp.presentation.project.project_overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import callofproject.dev.androidapp.domain.use_cases.ProjectOverviewUseCase
import callofproject.dev.androidapp.util.Resource
import callofproject.dev.androidapp.util.route.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectOverviewViewModel @Inject constructor(
    private val projectOverviewUseCase: ProjectOverviewUseCase
) : ViewModel() {


    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(ProjectOverviewState())
        private set


    fun findProjectOverview(projectId: String) {
        viewModelScope.launch {
            projectOverviewUseCase(projectId).let { result ->
                when (result) {
                    is Resource.Success -> {
                        state = state.copy(
                            projectOverviewDTO = result.data!!
                        )
                    }

                    is Resource.Error -> {
                        //_uiEvent.send(UiEvent.ShowSnackbar(result.message!!))
                    }

                    is Resource.Loading -> {
                        //_uiEvent.send(UiEvent.ShowSnackbar("Loading"))
                    }
                }
            }
        }
    }

}