package callofproject.dev.androidapp.presentation.project.components.projects_topbar

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import callofproject.dev.androidapp.util.route.Route
import callofproject.dev.androidapp.util.route.UiEvent
import callofproject.dev.androidapp.util.route.UiEvent.Navigate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopBarViewModel @Inject constructor() : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    var selectedItemIndex = mutableStateOf(0)
    fun onEvent(event: TopBarEvent) = when (event) {
        is TopBarEvent.OnClickProjectOverviewBtn -> onNavigate("${Route.PROJECT_OVERVIEW}/${event.projectId}/${selectedItemIndex.value}")
        is TopBarEvent.OnClickProjectDetailsBtn -> onNavigate("${Route.PROJECT_DETAILS}/${event.projectId}/${selectedItemIndex.value}")
        is TopBarEvent.OnClickProjectParticipantsBtn -> onNavigate("${Route.PROJECT_PARTICIPANTS}/${event.projectId}/${selectedItemIndex.value}")
    }

    private fun onNavigate(route: String) {
        viewModelScope.launch { _uiEvent.send(Navigate(route)) }
    }
}