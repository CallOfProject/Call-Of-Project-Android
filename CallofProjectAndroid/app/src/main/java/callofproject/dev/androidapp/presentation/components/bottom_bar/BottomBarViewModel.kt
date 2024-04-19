package callofproject.dev.androidapp.presentation.components.bottom_bar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import callofproject.dev.androidapp.domain.preferences.IPreferences
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

    var notificationCount by mutableStateOf(3)
        private set

    fun onEvent(event: BottomBarEvent) = when (event) {
        is BottomBarEvent.Navigate -> onNavigate(event.route)
    }

    private fun onNavigate(route: String) {
        viewModelScope.launch {
            _uiEvent.send(Navigate(route))
        }
    }
}