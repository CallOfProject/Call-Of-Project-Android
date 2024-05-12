package callofproject.dev.androidapp.presentation.project.project_overview

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.presentation.components.NotEditableCardComponent
import callofproject.dev.androidapp.presentation.components.RowBasedCardComponent
import callofproject.dev.androidapp.presentation.components.TagItem
import callofproject.dev.androidapp.presentation.components.bottom_bar.BottomBarComponent
import callofproject.dev.androidapp.presentation.project.components.projects_topbar.ProjectTopBarComponent
import callofproject.dev.androidapp.util.route.UiEvent
import coil.compose.rememberAsyncImagePainter

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProjectOverviewScreen(
    viewModel: ProjectOverviewViewModel = hiltViewModel(),
    scaffoldState: SnackbarHostState,
    onNavigate: (UiEvent.Navigate) -> Unit,
    projectId: String,
    selectedNavBar: Int
) {
    LaunchedEffect(key1 = true) {
        viewModel.findProjectOverview(projectId)
    }

    val state = viewModel.state
    val context = LocalContext.current
    val tech = state.projectOverviewDTO.technicalRequirements.split(",")
    val spec = state.projectOverviewDTO.specialRequirements.split(",")

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.showSnackbar(
                        withDismissAction = true,
                        message = event.msg.asString(context),
                        duration = SnackbarDuration.Short
                    )
                }

                else -> Unit
            }
        }
    }
    Scaffold(
        topBar = {
            ProjectTopBarComponent(
                onNavigate = onNavigate,
                projectId = projectId,
                selectedNavBar = selectedNavBar
            )
        },
        bottomBar = { BottomBarComponent(scaffoldState, onNavigate) }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it), contentAlignment = Alignment.Center
        ) {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                content = {
                    Image(
                        painter = rememberAsyncImagePainter(state.projectOverviewDTO.projectImagePath),
                        contentDescription = "project",
                        modifier = Modifier
                            .size(160.dp)
                            .padding(1.dp),
                        alignment = Alignment.Center
                    )
                    Text(
                        text = state.projectOverviewDTO.projectTitle,
                        modifier = Modifier.padding(5.dp),
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = state.projectOverviewDTO.projectOwnerName,
                        modifier = Modifier.padding(5.dp),
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )



                    NotEditableCardComponent(
                        title = stringResource(R.string.title_projectSummary),
                        height = 270.dp
                    ) {
                        Text(
                            text = state.projectOverviewDTO.projectSummary.trimIndent(),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(5.dp)
                        )
                    }

                    NotEditableCardComponent(
                        title = stringResource(R.string.title_projectAim),
                        height = 270.dp
                    ) {
                        Text(
                            text = state.projectOverviewDTO.projectAim.trimIndent(),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(5.dp)
                        )
                    }




                    NotEditableCardComponent(
                        title = stringResource(R.string.title_projectTechRequirements),
                        height = 270.dp
                    ) {
                        LazyColumn {
                            items(tech.size) { index ->
                                Card(
                                    colors = CardColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                                        contentColor = MaterialTheme.colorScheme.primary,
                                        disabledContainerColor = Color.Transparent,
                                        disabledContentColor = Color.Transparent
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(5.dp)
                                        .border(
                                            BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                                            shape = RoundedCornerShape(5.dp)
                                        )
                                ) {
                                    Text(
                                        text = tech[index],
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Normal,
                                        modifier = Modifier.padding(5.dp)
                                    )
                                }
                            }
                        }
                    }

                    NotEditableCardComponent(
                        title = stringResource(R.string.title_projectSpecRequirements),
                        height = 270.dp
                    ) {
                        LazyColumn {
                            items(spec.size) { index ->
                                Card(
                                    colors = CardColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                                        contentColor = MaterialTheme.colorScheme.primary,
                                        disabledContainerColor = Color.Transparent,
                                        disabledContentColor = Color.Transparent
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(5.dp)
                                        .border(
                                            BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                                            shape = RoundedCornerShape(5.dp)
                                        )
                                ) {
                                    Text(
                                        text = spec[index],
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Normal,
                                        modifier = Modifier.padding(5.dp)
                                    )
                                }
                            }
                        }
                    }



                    NotEditableCardComponent(
                        title = stringResource(R.string.title_projectDateInformation),
                        height = 270.dp
                    ) {
                        RowBasedCardComponent(
                            title = stringResource(R.string.title_projectStartDate),
                            value = state.projectOverviewDTO.startDate
                        )
                        RowBasedCardComponent(
                            title = stringResource(R.string.title_projectExpectedCompletionDate),
                            value = state.projectOverviewDTO.expectedCompletionDate
                        )
                        RowBasedCardComponent(
                            title = stringResource(R.string.title_projectApplicationDeadline),
                            value = state.projectOverviewDTO.applicationDeadline
                        )
                        RowBasedCardComponent(
                            title = stringResource(R.string.title_projectFeedbackTimeRange),
                            value = state.projectOverviewDTO.feedbackTimeRange
                        )
                    }


                    NotEditableCardComponent(
                        title = stringResource(R.string.title_projectInformation),
                        height = 320.dp
                    ) {
                        RowBasedCardComponent(
                            title = stringResource(R.string.title_projectMaxParticipant),
                            value = "${state.projectOverviewDTO.projectParticipants.size}/${state.projectOverviewDTO.maxParticipant}"
                        )
                        RowBasedCardComponent(
                            title = stringResource(R.string.title_projectProfessionLevel),
                            value = state.projectOverviewDTO.professionLevel
                        )
                        RowBasedCardComponent(
                            title = stringResource(R.string.title_projectLevel),
                            value = state.projectOverviewDTO.projectLevel
                        )
                        RowBasedCardComponent(
                            title = stringResource(R.string.title_projectInterviewType),
                            value = state.projectOverviewDTO.interviewType
                        )
                        RowBasedCardComponent(
                            title = stringResource(R.string.title_projectStatus),
                            value = state.projectOverviewDTO.projectStatus
                        )
                    }

                    NotEditableCardComponent(
                        stringResource(R.string.title_projectTags),
                        400.dp,
                    ) {
                        FlowRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentSize(Alignment.CenterStart)
                                .verticalScroll(rememberScrollState())
                        ) {
                            (0..<state.projectOverviewDTO.projectTags.size).forEach { idx ->
                                TagItem(
                                    text = state.projectOverviewDTO.projectTags[idx].tagName,
                                    isRemovable = false
                                )
                            }
                        }
                    }


                    if (viewModel.isParticipantOrOwner())
                        Button(onClick = {
                            viewModel.onEvent(ProjectOverviewEvent.OnSendJoinRequestClick(projectId))
                        }, modifier = Modifier.padding(10.dp)) {
                            Text(text = stringResource(R.string.message_joinRequest))

                        }
                })

        }

    }
}


