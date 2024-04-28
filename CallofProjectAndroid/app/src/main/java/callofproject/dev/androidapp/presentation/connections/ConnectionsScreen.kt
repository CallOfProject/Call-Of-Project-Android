package callofproject.dev.androidapp.presentation.connections

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.domain.dto.connection.UserConnectionDTO
import callofproject.dev.androidapp.util.route.UiEvent
import coil.compose.rememberAsyncImagePainter


@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ConnectionsScreen(
    scaffoldState: SnackbarHostState,
    viewModel: ConnectionViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit,
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit
) {
    val list = listOf("Connections", "Pending Requests", "Blocked Connections")
    val selectedItem = remember {
        mutableStateOf(list[0])
    }

    val state = viewModel.state
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { topBar() },
        bottomBar = { bottomBar() },
        snackbarHost = { SnackbarHost(scaffoldState) }) { it ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            stickyHeader {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    TopFilterChip(list = list, selectedItem = selectedItem, viewModel = viewModel)
                }
            }

            when (selectedItem.value) {
                "Connections" -> items(state.connections) {
                    PersonCard(
                        user = it,
                        isConnections = true,
                        isPending = false,
                        isBlocked = false,
                        viewModel = viewModel
                    )
                }

                "Pending Requests" -> items(state.connections) {
                    PersonCard(
                        isConnections = false,
                        isPending = true,
                        isBlocked = false,
                        user = it,
                        viewModel = viewModel
                    )
                }

                else -> items(state.connections) {
                    PersonCard(
                        isConnections = false,
                        isPending = false,
                        isBlocked = true,
                        user = it,
                        viewModel = viewModel
                    )
                }
            }
        }

    }

}

@Composable
private fun TopFilterChip(
    list: List<String>,
    selectedItem: MutableState<String>,
    viewModel: ConnectionViewModel
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        items(list) { item ->
            val isSelected = item == selectedItem.value
            FilterChip(
                modifier = Modifier.padding(horizontal = 6.dp),
                selected = isSelected,
                onClick = {
                    if (isSelected)
                        selectedItem.value = ""
                    else selectedItem.value = item

                    when (item) {
                        "Connections" -> {
                            viewModel.onEvent(ConnectionEvent.FindConnections)
                        }

                        "Pending Requests" -> {
                            viewModel.onEvent(ConnectionEvent.FindPendingConnections)
                        }

                        else -> {
                            viewModel.onEvent(ConnectionEvent.FindBlockedConnections)
                        }
                    }
                },
                label = { Text(text = item, fontSize = 9.5.sp) }
            )
        }
    }
}

@Composable
private fun PersonCard(
    isConnections: Boolean,
    isPending: Boolean,
    isBlocked: Boolean,
    user: UserConnectionDTO,
    viewModel: ConnectionViewModel
) {
    Card(
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(10.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically

        ) {
            Image(
                painter = rememberAsyncImagePainter(user.profilePhoto),
                contentDescription = "project",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                alignment = Alignment.Center
            )

            Column(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = user.username,
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight(600)

                )
            }
            if (!isPending && !isBlocked && isConnections) {
                IconButton(onClick = {
                    viewModel.onEvent(ConnectionEvent.BlockConnection(user.userId))
                }) {
                    Icon(
                        painter = painterResource(R.drawable.block_person),
                        contentDescription = "",
                        modifier = Modifier
                            .size(30.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                IconButton(onClick = {
                    viewModel.onEvent(ConnectionEvent.RemoveConnection(user.userId))
                }) {
                    Icon(
                        painter = painterResource(R.drawable.trash),
                        contentDescription = "",
                        tint = Color.Red,
                        modifier = Modifier
                            .size(30.dp)
                    )
                }
            }
            if (isPending && !isBlocked && !isConnections) {
                IconButton(onClick = {
                    viewModel.onEvent(ConnectionEvent.AcceptConnection(user.userId))
                }) {
                    Icon(
                        Icons.Filled.Check,
                        contentDescription = "",
                        modifier = Modifier
                            .size(30.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                IconButton(onClick = {
                    viewModel.onEvent(ConnectionEvent.RejectConnection(user.userId))
                }) {
                    Icon(
                        Icons.Filled.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .size(30.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            if (!isConnections && !isPending && isBlocked) {
                IconButton(onClick = {
                    viewModel.onEvent(ConnectionEvent.UnblockConnection(user.userId))
                }) {
                    Icon(
                        painter = painterResource(R.drawable.add_connection),
                        contentDescription = "",
                        modifier = Modifier
                            .size(30.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }


    }
}
