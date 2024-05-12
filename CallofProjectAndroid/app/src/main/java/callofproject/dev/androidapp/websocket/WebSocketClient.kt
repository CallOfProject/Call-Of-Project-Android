package callofproject.dev.androidapp.websocket

import android.content.Context
import android.util.Log
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.R.string.websocket_channel
import callofproject.dev.androidapp.domain.dto.NotificationDTO
import callofproject.dev.androidapp.domain.preferences.IPreferences
import callofproject.dev.androidapp.domain.use_cases.NotificationUseCase
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.Stomp.ConnectionProvider
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.StompMessage
import java.util.concurrent.ExecutorService
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class WebSocketClient @Inject constructor(
    private val context: Context,
    private val preferences: IPreferences,
    private val gson: Gson,
    private val notificationUseCase: NotificationUseCase,
) {

    private val compositeDisposable = CompositeDisposable()
    private var stompClient: StompClient? = null

    private val _notificationFlow =
        MutableSharedFlow<NotificationDTO>(replay = 1, extraBufferCapacity = 1)
    val notificationFlow: SharedFlow<NotificationDTO> = _notificationFlow

    private fun errorCallback(throwable: Throwable) {
        Log.e("WebSocketClient", "Error", throwable)
    }

    private fun messageCallback(stompMessage: StompMessage) {
        val message = gson.fromJson(stompMessage.payload, NotificationDTO::class.java)
        notificationUseCase.createApprovalNotification(message)

        MainScope().launch {
            try {
                _notificationFlow.emit(message)
                Log.d("WebsocketClient", "success")
            } catch (ex: Exception) {
                Log.e("WebSocketClient", "Error emitting notification", ex)
            }
        }
    }

    fun connectWebSocket() {

        if (stompClient != null)
            return

        val channel = context.getString(websocket_channel).format(preferences.getUserId())
        val websocketUrl = context.getString(R.string.websocket_endpoint)

        stompClient = Stomp.over(ConnectionProvider.OKHTTP, websocketUrl)

        stompClient?.connect()

        stompClient?.topic(channel)?.let { stomp ->
            compositeDisposable.add(stomp.subscribe(::messageCallback, ::errorCallback))
        }
    }

    fun disconnectWebSocket() {
        stompClient?.let {
            compositeDisposable.clear()
            it.disconnect()
            stompClient = null
        }
    }
}