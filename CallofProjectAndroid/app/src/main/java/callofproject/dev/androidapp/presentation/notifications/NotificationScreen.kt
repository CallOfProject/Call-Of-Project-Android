package callofproject.dev.androidapp.presentation.notifications

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.domain.dto.NotificationDataType.CONNECTION_REQUEST
import callofproject.dev.androidapp.domain.dto.NotificationDataType.PROJECT_JOIN_REQUEST
import callofproject.dev.androidapp.presentation.notifications.NotificationEvent.OnAcceptConnectionRequest
import callofproject.dev.androidapp.presentation.notifications.NotificationEvent.OnAcceptProjectJoinRequest
import callofproject.dev.androidapp.presentation.notifications.NotificationEvent.OnRejectConnectionRequest
import callofproject.dev.androidapp.presentation.notifications.NotificationEvent.OnRejectProjectJoinRequest
import callofproject.dev.androidapp.util.route.UiEvent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NotificationScreen(
    scaffoldState: SnackbarHostState,
    viewModel: NotificationViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit,
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit
) {

    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)

                is UiEvent.ShowSnackbar -> {
                    scaffoldState.showSnackbar(
                        message = event.msg.asString(context),
                        withDismissAction = true,
                        duration = SnackbarDuration.Short
                    )
                }

                else -> Unit
            }
        }
    }
    Scaffold(
        topBar = topBar,
        bottomBar = bottomBar
    ) {
        Box(
            contentAlignment = Alignment.TopCenter, modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            if (state.isLoading)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) { CircularProgressIndicator(strokeWidth = 2.dp) }
            else LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = if (state.notifications.isEmpty()) Modifier.fillMaxSize() else Modifier
            ) {
                if (state.notifications.isNotEmpty())
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            OutlinedButton(onClick = {
                                viewModel.onEvent(NotificationEvent.OnMarkAllAsReadClicked)
                            }) {
                                Text(text = stringResource(R.string.btn_markAllAsRead))
                            }

                            OutlinedButton(onClick = {
                                viewModel.onEvent(NotificationEvent.OnClearAllClicked)
                            }) {
                                Text(text = stringResource(R.string.btn_clearAll))
                            }
                        }
                    }
                if (state.notifications.isEmpty()) {
                    item {
                        Text(
                            text = stringResource(R.string.msg_notificationNotFound),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(10.dp),
                            textAlign = TextAlign.Center,
                        )

                        Icon(
                            painter = painterResource(id = R.drawable.clear),
                            contentDescription = ""
                        )
                    }
                } else items(state.notifications.size) { index ->
                    val notification = state.notifications[index]
                    when (notification.notificationDataType) {

                        PROJECT_JOIN_REQUEST -> NotificationCardDialog(
                            msg = state.notifications[index].message!!,
                            onAccept = { viewModel.onEvent(OnAcceptProjectJoinRequest(notification)) },
                            onReject = { viewModel.onEvent(OnRejectProjectJoinRequest(notification)) }
                        )

                        CONNECTION_REQUEST -> NotificationCardDialog(
                            msg = state.notifications[index].message!!,
                            onAccept = { viewModel.onEvent(OnAcceptConnectionRequest(notification)) },
                            onReject = { viewModel.onEvent(OnRejectConnectionRequest(notification)) }
                        )

                        else -> NotificationCard(state.notifications[index].message!!)
                    }
                }
            }
        }

    }
}

@Composable
private fun NotificationCard(msg: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(15.dp), shape = RoundedCornerShape(15.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.Left,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.notification_icon),
                contentDescription = "project",
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.CenterVertically),
                alignment = Alignment.Center
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(10.dp),
                content = {
                    Text(text = msg, style = MaterialTheme.typography.bodyMedium)
                })
        }
    }
}

@Composable
private fun NotificationCardDialog(msg: String, onAccept: () -> Unit, onReject: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 160.dp)
            .padding(15.dp),
        shape = RoundedCornerShape(15.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.Left,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.notification_icon),
                    contentDescription = "project",
                    modifier = Modifier
                        .size(60.dp)
                        .align(Alignment.CenterVertically),
                    alignment = Alignment.Center
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(10.dp),
                    content = {
                        Text(text = msg, style = MaterialTheme.typography.bodyMedium)
                    })
            }


            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Button(onClick = onAccept) {
                    Icon(Icons.Filled.Done, contentDescription = "")
                }
                Spacer(modifier = Modifier.width(20.dp))
                Button(onClick = onReject) {
                    Icon(Icons.Filled.Clear, contentDescription = "")
                }
            }
        }
    }
}
