package callofproject.dev.androidapp.presentation.project.my_projects

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import callofproject.dev.androidapp.domain.preferences.IPreferences
import callofproject.dev.androidapp.domain.use_cases.UseCaseFacade
import callofproject.dev.androidapp.util.Resource
import callofproject.dev.androidapp.util.route.Route
import callofproject.dev.androidapp.util.route.UiEvent
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
class ProjectViewModel @Inject constructor(
    private val useCases: UseCaseFacade,
    private val pref: IPreferences
) : ViewModel() {

    var state by mutableStateOf(MyProjectsState())
        private set


    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var findAllJob: Job? = null

    init {
        pref.clearFilterObjects()
    }

    fun onEvent(event: MyProjectsEvent) = when (event) {
        is MyProjectsEvent.OnClickProject -> handleClickProject(event.projectId)
    }

    private fun handleClickProject(projectId: String) {
        viewModelScope.launch {
            _uiEvent.send(UiEvent.Navigate("${Route.PROJECT_OVERVIEW}/$projectId/0"))
        }

    }

    fun findUserProjects() {
        viewModelScope.launch {
            findAllJob?.cancel()
            state = state.copy(isLoading = true)
            findAllJob = useCases.project.findOwnerOfProjects(1)
                .onStart { delay(50L) }
                .onEach { resource ->
                    when (resource) {

                        is Resource.Success -> {
                            state = state.copy(
                                myProjects = resource.data?.`object`?.projects ?: emptyList(),
                                isLoading = false,
                            )
                        }

                        is Resource.Loading -> {
                            state = state.copy(isLoading = true, myProjects = emptyList())
                        }

                        is Resource.Error -> {
                            state = state.copy(isLoading = false, myProjects = emptyList())
                        }
                    }

                }.launchIn(this)
        }
    }

}