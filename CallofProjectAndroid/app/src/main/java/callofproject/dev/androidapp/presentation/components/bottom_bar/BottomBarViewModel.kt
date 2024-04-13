package callofproject.dev.androidapp.presentation.components.bottom_bar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import callofproject.dev.androidapp.domain.preferences.IPreferences
import callofproject.dev.androidapp.util.route.Route
import callofproject.dev.androidapp.util.route.UiEvent
import callofproject.dev.androidapp.util.route.UiEvent.Navigate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomBarViewModel @Inject constructor(
    private val preferences: IPreferences
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: BottomBarEvent) = when (event) {
        is BottomBarEvent.Navigate -> onNavigate(event.route)
        is BottomBarEvent.NavigateToUserProfile -> onNavigateToUserProfile()
    }

    private fun onNavigateToUserProfile() {
        viewModelScope.launch {
            _uiEvent.send(Navigate("${Route.PROFILE}/${preferences.getUserId()!!}"))
        }
    }

    private fun onNavigate(route: String) {
        viewModelScope.launch { _uiEvent.send(Navigate(route)) }
    }
}