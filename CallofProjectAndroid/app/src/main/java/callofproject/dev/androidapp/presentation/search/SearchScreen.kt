package callofproject.dev.androidapp.presentation.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.presentation.components.LoadingComponent
import callofproject.dev.androidapp.util.route.UiEvent
import coil.compose.rememberAsyncImagePainter

@Composable
fun SearchScreen(
    keyword: String,
    scaffoldState: SnackbarHostState,
    viewModel: SearchViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit,
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit
) {
    val state = viewModel.state

    val context = LocalContext.current
    var isSelectedProjects by remember { mutableStateOf(false) }
    var isSelectedUsers by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = true) { viewModel.search(keyword) }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)

                is UiEvent.ShowSnackbar -> {
                    scaffoldState.showSnackbar(message = event.msg.asString(context))
                }

                else -> Unit
            }
        }
    }


    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { topBar() },
        bottomBar = { bottomBar() }
    ) { it ->

        if (state.isLoading)
            LoadingComponent(it)
        else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(it),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(
                            colors = ButtonColors(
                                containerColor = MaterialTheme.colorScheme.background,
                                contentColor = MaterialTheme.colorScheme.primary,
                                disabledContainerColor = Color.Transparent,
                                disabledContentColor = Color.Transparent
                            ),
                            onClick = {
                                isSelectedUsers = !isSelectedUsers
                                isSelectedProjects = !isSelectedProjects
                            },
                            modifier = Modifier.padding(10.dp)
                        ) {
                            Text(text = stringResource(R.string.search_users))
                        }

                        OutlinedButton(
                            onClick = {
                                isSelectedUsers = !isSelectedUsers
                                isSelectedProjects = !isSelectedProjects

                            },
                            modifier = Modifier.padding(10.dp)
                        ) {
                            Text(text = stringResource(R.string.search_projects))
                        }
                    }

                    Text(
                        text = if (isSelectedProjects)
                            stringResource(R.string.search_projects)
                        else stringResource(R.string.search_users),
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                if (isSelectedProjects)
                    items(state.searchResult.projects.projects.size) {
                        Card(
                            colors = CardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                                contentColor = MaterialTheme.colorScheme.onSurface,
                                disabledContainerColor = Color.Transparent,
                                disabledContentColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp), shape = RoundedCornerShape(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.clickable {
                                    viewModel.onEvent(SearchEvent.OnProjectClick(state.searchResult.projects.projects[it].projectId.toString()))
                                },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                content = {
                                    Image(
                                        painter = rememberAsyncImagePainter(state.searchResult.projects.projects[it].projectImage),
                                        contentDescription = "project",
                                        modifier = Modifier
                                            .size(100.dp)
                                            .align(Alignment.CenterVertically),
                                        alignment = Alignment.Center
                                    )

                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.padding(10.dp),
                                        content = {
                                            Text(
                                                text = state.searchResult.projects.projects[it].projectName,
                                                style = MaterialTheme.typography.titleMedium
                                            )

                                            Text(
                                                text = state.searchResult.projects.projects[it].projectSummary,
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                        })
                                })
                        }
                    }
                if (isSelectedUsers)
                    items(state.searchResult.users.users.size) {
                        val user = state.searchResult.users.users[it]
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
                                    .clickable {
                                        viewModel.onEvent(
                                            SearchEvent.OnUserClick(
                                                user.userId.toString()
                                            )
                                        )
                                    },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Absolute.SpaceAround,
                                content = {
                                    Image(
                                        painter = rememberAsyncImagePainter(user.image),
                                        contentDescription = "project",
                                        contentScale = ContentScale.FillBounds,
                                        modifier = Modifier
                                            .size(100.dp)
                                            .clip(RectangleShape),
                                        alignment = Alignment.Center
                                    )

                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.padding(10.dp),
                                        content = {
                                            Text(
                                                text = user.firstName + " " + user.middleName + " " + user.lastName,
                                                style = MaterialTheme.typography.titleMedium
                                            )

                                            Text(
                                                text = user.username,
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                        }
                                    )

                                    if (user.username != stringResource(R.string.username_root) &&
                                        user.username != stringResource(R.string.username_admin) &&
                                        user.username != viewModel.getCurrentUsername()
                                    )
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            modifier = Modifier.padding(5.dp),
                                            content = {
                                                OutlinedButton(onClick = { }) {
                                                    Text(text = stringResource(R.string.follow))
                                                }
                                            }
                                        )
                                    else if (user.username == stringResource(R.string.username_root) ||
                                        user.username == stringResource(R.string.username_admin)
                                    ) {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            modifier = Modifier.padding(5.dp),
                                            content = {
                                                OutlinedButton(
                                                    onClick = { },
                                                    enabled = false
                                                ) {
                                                    Text(text = stringResource(R.string.follow))
                                                }
                                            })
                                    } else
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            modifier = Modifier.padding(5.dp),
                                            content = {
                                                OutlinedButton(onClick = { }) {
                                                    Text(text = stringResource(R.string.unfollow))
                                                }
                                            })
                                }
                            )
                        }

                    }
            }
        }
    }
}