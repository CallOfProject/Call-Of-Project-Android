package callofproject.dev.androidapp.presentation.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.presentation.components.DropDownComponent
import callofproject.dev.androidapp.presentation.components.LoadingComponent
import callofproject.dev.androidapp.presentation.main_page.SORT_TYPE_ASC
import callofproject.dev.androidapp.presentation.main_page.SORT_TYPE_DESC
import callofproject.dev.androidapp.presentation.search.SearchEvent.OnAddConnectionClick
import callofproject.dev.androidapp.presentation.search.SearchEvent.OnProjectClick
import callofproject.dev.androidapp.presentation.search.SearchEvent.OnSortProjects
import callofproject.dev.androidapp.presentation.search.SearchEvent.OnSortType
import callofproject.dev.androidapp.presentation.search.SearchEvent.OnUserClick
import callofproject.dev.androidapp.util.route.UiEvent
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalFoundationApi::class)
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
    val dropdownOptions = stringArrayResource(R.array.array_sortingOptions).toList()
    val sortTypeOptions = listOf(SORT_TYPE_DESC, SORT_TYPE_ASC)
    val expandedSortOptions by remember { mutableStateOf(false) }
    val expandedSortTypes by remember { mutableStateOf(false) }
    val selectedSortOption = remember { mutableStateOf(sortTypeOptions[0]) }
    val selectedSortType = remember { mutableStateOf(dropdownOptions[0]) }
    LaunchedEffect(key1 = true) { viewModel.search(keyword) }

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
                                containerColor = if (!isSelectedUsers) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.primary,
                                contentColor = if (!isSelectedUsers) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onTertiary,
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
                            colors = ButtonColors(
                                containerColor = if (!isSelectedProjects) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.primary,
                                contentColor = if (!isSelectedProjects) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onTertiary,
                                disabledContainerColor = Color.Transparent,
                                disabledContentColor = Color.Transparent
                            ),
                            onClick = {
                                isSelectedUsers = !isSelectedUsers
                                isSelectedProjects = !isSelectedProjects

                            },
                            modifier = Modifier.padding(10.dp)
                        ) {
                            Text(text = stringResource(R.string.search_projects))
                        }
                    }

                }

                if (isSelectedProjects) {
                    stickyHeader {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.background),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            DropDownComponent(
                                options = dropdownOptions,
                                onClick = { viewModel.onEvent(OnSortProjects(it)) },
                                isOpen = expandedSortOptions,
                                selectedItem = selectedSortOption
                            )

                            DropDownComponent(
                                options = sortTypeOptions,
                                onClick = {
                                    viewModel.onEvent(
                                        OnSortType(
                                            it,
                                            selectedSortOption.value
                                        )
                                    )
                                },
                                isOpen = expandedSortTypes,
                                selectedItem = selectedSortType
                            )
                        }
                    }
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
                                    viewModel.onEvent(
                                        OnProjectClick(
                                            state.searchResult.projects.projects[it].projectId.toString()
                                        )
                                    )
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
                                            OnUserClick(
                                                user.userId.toString()
                                            )
                                        )
                                    },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                content = {
                                    Image(
                                        painter = rememberAsyncImagePainter(user.image),
                                        contentDescription = "project",
                                        contentScale = ContentScale.FillBounds,
                                        modifier = Modifier
                                            .size(90.dp)
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

                                    val currentUsername = viewModel.getCurrentUsername()
                                    val rootUsername = stringResource(R.string.username_root)
                                    val adminUsername = stringResource(R.string.username_admin)

                                    if (user.username != rootUsername && user.username != adminUsername && user.username != currentUsername)
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            modifier = Modifier.padding(5.dp),
                                            content = {
                                                IconButton(onClick = {
                                                    viewModel.onEvent(OnAddConnectionClick(user.userId))
                                                }) {
                                                    Icon(
                                                        painter = painterResource(R.drawable.add_connection),
                                                        contentDescription = "",
                                                        tint = MaterialTheme.colorScheme.primary,
                                                        modifier = Modifier
                                                            .size(30.dp)

                                                    )
                                                }
                                            }
                                        )
                                    else if (user.username == rootUsername || user.username == adminUsername)
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            modifier = Modifier.padding(5.dp),
                                            content = {}
                                        )
                                }
                            )
                        }

                    }
            }
        }
    }
}