package callofproject.dev.androidapp.presentation.main_page

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.presentation.components.DropDownComponent
import callofproject.dev.androidapp.presentation.components.LoadingComponent
import callofproject.dev.androidapp.presentation.main_page.MainPageEvent.OnSortType
import callofproject.dev.androidapp.util.route.UiEvent
import coil.compose.rememberAsyncImagePainter


@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    scaffoldState: SnackbarHostState,
    viewModel: MainPageViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit,
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit
) {
    val state = viewModel.state
    val context = androidx.compose.ui.platform.LocalContext.current
    val dropdownOptions = stringArrayResource(R.array.array_sortingOptions).toList()
    val sortTypeOptions = listOf(SORT_TYPE_DESC, SORT_TYPE_ASC)
    val expandedSortOptions by remember { mutableStateOf(false) }
    val expandedSortTypes by remember { mutableStateOf(false) }
    val selectedSortOption = remember { mutableStateOf(sortTypeOptions[0]) }
    val selectedSortType = remember { mutableStateOf(dropdownOptions[0]) }

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
        bottomBar = { bottomBar() }) { it ->
        if (state.isLoading)
            LoadingComponent(it)

        if (!state.isLoading && state.projectDiscoveryList.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = "No projects found", fontSize = 20.sp, fontWeight = FontWeight(700))
            }
        } else LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            stickyHeader {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    DropDownComponent(
                        options = dropdownOptions,
                        onClick = {
                            viewModel.onEvent(MainPageEvent.OnSortProjects(it))
                        },
                        isOpen = expandedSortOptions,
                        selectedItem = selectedSortOption
                    )

                    DropDownComponent(
                        options = sortTypeOptions,
                        onClick = {
                            viewModel.onEvent(OnSortType(it, selectedSortOption.value))
                        },
                        isOpen = expandedSortTypes,
                        selectedItem = selectedSortType
                    )
                }
            }

            items(state.projectDiscoveryList.size) {
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
                            viewModel.onEvent(MainPageEvent.OnClickProjectDiscoveryItem(state.projectDiscoveryList[it].projectId.toString()))
                        },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,

                        content = {
                            Image(
                                painter = rememberAsyncImagePainter(state.projectDiscoveryList[it].projectImagePath),
                                contentDescription = "",
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
                                        text = state.projectDiscoveryList[it].projectTitle,
                                        style = MaterialTheme.typography.titleMedium,
                                        textAlign = TextAlign.Center
                                    )
                                    Text(
                                        text = when (selectedSortOption.value) {
                                            SORT_CREATION_DATE -> state.projectDiscoveryList[it].creationDate
                                            SORT_START_DATE -> state.projectDiscoveryList[it].startDate
                                            SORT_APPLICATION_DEADLINE -> state.projectDiscoveryList[it].applicationDeadline
                                            SORT_EXPECTED_COMPLETION_DATE -> state.projectDiscoveryList[it].expectedCompletionDate
                                            else -> state.projectDiscoveryList[it].creationDate
                                        },
                                        style = MaterialTheme.typography.titleMedium,
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 12.sp,
                                    )
                                    Text(
                                        text = state.projectDiscoveryList[it].projectSummary,
                                        style = MaterialTheme.typography.bodySmall,
                                    )
                                })
                        })
                }
            }
        }
    }
}