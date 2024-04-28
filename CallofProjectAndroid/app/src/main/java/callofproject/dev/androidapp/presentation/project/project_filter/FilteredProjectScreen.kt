package callofproject.dev.androidapp.presentation.project.project_filter

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import callofproject.dev.androidapp.domain.dto.filter.ProjectFilterDTO
import callofproject.dev.androidapp.util.route.UiEvent
import coil.compose.rememberAsyncImagePainter

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FilteredProjectScreen(
    filterObj: ProjectFilterDTO,
    scaffoldState: SnackbarHostState,
    viewModel: ProjectFilterViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit,
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit
) {
    val state = viewModel.state
    val context = androidx.compose.ui.platform.LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(ProjectFilterEvent.OnClickFilterProjectBtn(filterObj))
    }


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
        bottomBar = { bottomBar() },
        snackbarHost = { SnackbarHost(scaffoldState) }) { it ->

        if (state.isLoading)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CircularProgressIndicator(strokeWidth = 2.dp)
            }
        if (!state.isLoading && state.projectFilterList.projects.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "No projects found",
                    fontSize = 20.sp,
                    fontWeight = FontWeight(600),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        } else LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            items(state.projectFilterList.projects.size) {
                if (state.projectFilterList.projects.isEmpty())
                    Text(
                        text = "No projects found",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                else
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
                                    ProjectFilterEvent.OnClickProjectDiscoveryItem(
                                        state.projectFilterList.projects[it].projectId.toString()
                                    )
                                )
                            },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,

                            content = {
                                Image(
                                    painter = rememberAsyncImagePainter(state.projectFilterList.projects[it].projectImage),
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
                                            text = state.projectFilterList.projects[it].projectName,
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                        Text(
                                            text = state.projectFilterList.projects[it].projectSummary,
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    })
                            })
                    }
            }
        }
    }
}