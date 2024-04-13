package callofproject.dev.androidapp.presentation.project.project_overview

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import callofproject.dev.androidapp.presentation.components.NotEditableCardComponent
import callofproject.dev.androidapp.presentation.components.RowBasedCardComponent
import callofproject.dev.androidapp.presentation.components.TagComponent
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
) {
    LaunchedEffect(key1 = true) {
        viewModel.findProjectOverview(projectId)
    }

    val state = viewModel.state
    val context = LocalContext.current
    val tech = state.projectOverviewDTO.technicalRequirements.split(",")
    val spec = state.projectOverviewDTO.specialRequirements.split(",")


    Scaffold(
        topBar = {
            ProjectTopBarComponent(
                scaffoldState = scaffoldState,
                onNavigate = onNavigate,
                projectId = projectId
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
                .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                content = {
                    Image(
                        painter = rememberAsyncImagePainter(state.projectOverviewDTO.projectImagePath),
                        contentDescription = "project",
                        modifier = Modifier.size(120.dp),
                        alignment = Alignment.Center
                    )
                    Text(
                        text = state.projectOverviewDTO.projectTitle,
                        modifier = Modifier.padding(5.dp),
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = state.projectOverviewDTO.projectOwnerName,
                        modifier = Modifier.padding(5.dp),
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )



                    NotEditableCardComponent(title = "Project Summary", height = 270.dp) {
                        Text(
                            text = state.projectOverviewDTO.projectSummary.trimIndent(),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(5.dp)
                        )
                    }

                    NotEditableCardComponent(title = "Project Aim", height = 270.dp) {
                        Text(
                            text = state.projectOverviewDTO.projectAim.trimIndent(),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(5.dp)
                        )
                    }




                    NotEditableCardComponent(
                        title = "Technical Requirements",
                        height = 270.dp
                    ) {
                        (0..tech.size - 1).forEachIndexed { index, _ ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp)
                                    .border(
                                        BorderStroke(
                                            1.dp,
                                            MaterialTheme.colorScheme.primary
                                        ),
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

                    NotEditableCardComponent(
                        title = "Specific Requirements",
                        height = 270.dp
                    ) {
                        LazyColumn {
                            items(spec.size) { index ->
                                Text(
                                    text = spec[index],
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Normal,
                                    modifier = Modifier.padding(5.dp)
                                )
                            }
                        }
                    }



                    NotEditableCardComponent(
                        title = "Project Date Information",
                        height = 270.dp
                    ) {
                        RowBasedCardComponent(
                            title = "Start Date",
                            value = state.projectOverviewDTO.startDate
                        )
                        RowBasedCardComponent(
                            title = "Expected Completion Date",
                            value = state.projectOverviewDTO.expectedCompletionDate
                        )
                        RowBasedCardComponent(
                            title = "Application Deadline",
                            value = state.projectOverviewDTO.applicationDeadline
                        )
                        RowBasedCardComponent(
                            title = "Feedback Time Range",
                            value = state.projectOverviewDTO.feedbackTimeRange
                        )
                    }


                    NotEditableCardComponent(
                        title = "Project Information",
                        height = 280.dp
                    ) {
                        RowBasedCardComponent(
                            title = "Max Participant",
                            value = state.projectOverviewDTO.maxParticipant.toString()
                        )
                        RowBasedCardComponent(
                            title = "Profession Level",
                            value = state.projectOverviewDTO.professionLevel
                        )
                        RowBasedCardComponent(
                            title = "Project Level",
                            value = state.projectOverviewDTO.projectLevel
                        )
                        RowBasedCardComponent(
                            title = "Interview Type",
                            value = state.projectOverviewDTO.interviewType
                        )
                        RowBasedCardComponent(
                            title = "Project Status",
                            value = state.projectOverviewDTO.projectStatus
                        )
                    }

                    NotEditableCardComponent("Tags", 250.dp) {
                        FlowRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentSize(Alignment.TopCenter),
                        ) {
                            (0..state.projectOverviewDTO.projectTags.size - 1).forEach {
                                TagComponent(text = state.projectOverviewDTO.projectTags[it].tagName)
                            }
                        }
                    }


                    Button(onClick = {
                        Toast.makeText(
                            context,
                            "Project Join Request sent to Owner!",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }, modifier = Modifier.padding(10.dp)) {
                        Text(text = "Join Project")

                    }
                })

        }

    }
}


