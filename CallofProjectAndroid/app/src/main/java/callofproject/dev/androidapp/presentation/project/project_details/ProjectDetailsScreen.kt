package callofproject.dev.androidapp.presentation.project.project_details

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
import callofproject.dev.androidapp.presentation.components.TagComponent
import callofproject.dev.androidapp.presentation.components.bottom_bar.BottomBarComponent
import callofproject.dev.androidapp.presentation.project.components.projects_topbar.ProjectTopBarComponent
import callofproject.dev.androidapp.util.route.UiEvent
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProjectDetailsScreen(
    scaffoldState: SnackbarHostState,
    onNavigate: (UiEvent.Navigate) -> Unit,
    projectId: String,
    viewModel: ProjectDetailViewModel = hiltViewModel(),
    selectedNavBar: Int
) {

    val state = viewModel.state
    val tech = state.projectDetailsDTO.technicalRequirements.split(",")
    val spec = state.projectDetailsDTO.specialRequirements.split(",")

    LaunchedEffect(key1 = true) {
        viewModel.findProjectDetails(projectId)
    }

    Scaffold(
        topBar = {
            ProjectTopBarComponent(
                onNavigate = onNavigate,
                projectId = projectId,
                selectedNavBar = selectedNavBar
            )
        },
        bottomBar = { BottomBarComponent(scaffoldState, onNavigate) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            if (state.isParticipant || state.isOwner)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {

                    Image(
                        painter = rememberAsyncImagePainter(state.projectDetailsDTO.projectImagePath),
                        contentDescription = "project",
                        modifier = Modifier
                            .size(160.dp)
                            .padding(1.dp),
                        alignment = Alignment.Center
                    )
                    Text(
                        text = state.projectDetailsDTO.projectTitle,
                        modifier = Modifier.padding(5.dp),
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = state.projectDetailsDTO.projectOwnerName,
                        modifier = Modifier.padding(5.dp),
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )

                    NotEditableCardComponent(
                        title = stringResource(R.string.title_projectSummary),
                        height = 270.dp
                    ) {
                        Text(
                            text = state.projectDetailsDTO.projectSummary.trimIndent(),
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
                            text = state.projectDetailsDTO.projectAim.trimIndent(),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(5.dp)
                        )
                    }

                    NotEditableCardComponent(
                        title = stringResource(R.string.title_projectDescription),
                        height = 270.dp
                    ) {
                        Text(
                            text = state.projectDetailsDTO.projectDescription.trimIndent(),
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
                        height = 270.dp,
                    ) {

                        RowBasedCardComponent(
                            title = stringResource(R.string.title_projectStartDate),
                            value = state.projectDetailsDTO.startDate
                        )
                        RowBasedCardComponent(
                            title = stringResource(R.string.title_projectExpectedCompletionDate),
                            value = state.projectDetailsDTO.expectedCompletionDate
                        )
                        RowBasedCardComponent(
                            title = stringResource(R.string.title_projectApplicationDeadline),
                            value = state.projectDetailsDTO.applicationDeadline
                        )
                        RowBasedCardComponent(
                            title = stringResource(R.string.title_projectFeedbackTimeRange),
                            value = state.projectDetailsDTO.feedbackTimeRange
                        )
                    }

                    NotEditableCardComponent(
                        title = stringResource(R.string.title_projectInformation),
                        height = 280.dp
                    ) {
                        RowBasedCardComponent(
                            title = stringResource(R.string.title_projectMaxParticipant),
                            value = state.projectDetailsDTO.maxParticipant.toString()
                        )
                        RowBasedCardComponent(
                            title = stringResource(R.string.title_projectProfessionLevel),
                            value = state.projectDetailsDTO.projectProfessionLevel
                        )
                        RowBasedCardComponent(
                            title = stringResource(R.string.title_projectLevel),
                            value = state.projectDetailsDTO.projectLevel
                        )
                        RowBasedCardComponent(
                            title = stringResource(R.string.title_projectInterviewType),
                            value = state.projectDetailsDTO.interviewType
                        )
                        RowBasedCardComponent(
                            title = stringResource(R.string.title_projectStatus),
                            value = state.projectDetailsDTO.projectStatus
                        )
                    }


                    NotEditableCardComponent(
                        title = stringResource(R.string.title_projectParticipants),
                        height = 280.dp
                    ) {
                        state.projectDetailsDTO.projectParticipants.forEach {
                            RowBasedCardComponent(title = it.full_name, value = it.username)
                        }
                    }

                    NotEditableCardComponent(
                        title = stringResource(R.string.title_projectAdminNotes),
                        height = 270.dp
                    ) {
                        Text(
                            text = state.projectDetailsDTO.adminNote,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(5.dp)
                        )
                    }


                    NotEditableCardComponent(stringResource(R.string.title_projectTags), 250.dp) {
                        FlowRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentSize(Alignment.TopCenter),
                        ) {
                            (0..state.projectDetailsDTO.projectTags.size - 1).forEach {
                                TagComponent(text = state.projectDetailsDTO.projectTags[it].tagName)
                            }
                        }
                    }

                }
            else {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        Icons.Filled.Lock,
                        contentDescription = stringResource(R.string.default_image_description),
                        modifier = Modifier.size(55.dp),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                    )
                    Text(
                        text = stringResource(R.string.warning_projectViewNotAllowed),
                        modifier = Modifier.padding(5.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight(400)
                    )
                }
            }
        }
    }
}