package callofproject.dev.androidapp.websocket

import android.content.Context
import android.util.Log
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.domain.dto.NotificationDTO
import callofproject.dev.androidapp.domain.preferences.IPreferences
import callofproject.dev.androidapp.domain.use_cases.NotificationUseCase
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.Stomp.ConnectionProvider
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.StompMessage
import javax.inject.Inject

class WebSocketClient @Inject constructor(
    private val context: Context,
    private val preferences: IPreferences,
    private val gson: Gson,
    private val notificationUseCase: NotificationUseCase
) {
    private val compositeDisposable = CompositeDisposable()
    private var stompClient: StompClient? = null

    private fun errorCallback(throwable: Throwable) {
        Log.e("WebSocketClient", "Error", throwable)
    }

    private fun messageCallback(stompMessage: StompMessage) {
        val message = gson.fromJson(stompMessage.payload, NotificationDTO::class.java)
        notificationUseCase.createApprovalNotification(message)
    }

    fun connectWebSocket() {

        if (stompClient != null)
            return

        stompClient =
            Stomp.over(ConnectionProvider.OKHTTP, context.getString(R.string.websocket_endpoint))

        stompClient?.connect()

        stompClient?.topic(
            context.getString(R.string.websocket_notificationChannel)
                .format(preferences.getUserId())
        )?.let { stomp ->
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