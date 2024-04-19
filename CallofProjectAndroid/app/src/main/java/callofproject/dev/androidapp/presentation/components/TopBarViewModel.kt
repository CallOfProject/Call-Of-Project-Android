package callofproject.dev.androidapp.presentation.components

import androidx.lifecycle.ViewModel
import callofproject.dev.androidapp.websocket.WebSocketClient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopBarViewModel @Inject constructor(
    private val webSocketClient: WebSocketClient
) : ViewModel() {
    fun stopWebsocket() {
        webSocketClient.disconnectWebSocket()
    }
}