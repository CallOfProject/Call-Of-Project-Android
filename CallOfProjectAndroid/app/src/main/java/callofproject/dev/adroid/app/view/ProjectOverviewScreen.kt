package callofproject.dev.adroid.app.view

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.DisposableEffectResult
import androidx.compose.runtime.DisposableEffectScope
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import callofproject.dev.adroid.app.view.util.LifeCycleObserver
import callofproject.dev.adroid.app.view.util.NotEditableCardComponent
import callofproject.dev.adroid.app.viewmodel.ProjectViewModel
import coil.compose.rememberAsyncImagePainter
import java.util.UUID

/*
@Composable
fun TagComponent(text : String)
{
    Text(text = text, fontSize = 15.sp, fontWeight = FontWeight.Bold, modifier = Modifier
        .padding(5.dp)
        .background(color = Color.LightGray, shape = RoundedCornerShape(50))
        .padding(horizontal = 8.dp, vertical = 4.dp))
}
*/

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProjectOverviewScreen(
    navController: NavController,
    projectId: String,
    viewModel: ProjectViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val overviewDTO by viewModel.overview.collectAsState()
    val painter: Painter = rememberAsyncImagePainter(overviewDTO?.projectImagePath)
    val isLoading by remember { viewModel.isLoading }


    DisposableEffect(Unit) {
        viewModel.findProjectOverviewByProjectId(UUID.fromString(projectId))
        onDispose { /* Dispose işlemi gerekli değil */ }
    }

    if (isLoading)
        CircularProgressIndicator()

    else
        Scaffold(
            topBar = { topNavigationBar(navController) },
            bottomBar = { BottomBarComponent(navController = navController) }) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it), contentAlignment = Alignment.Center
            ) {
                overviewDTO?.let {
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top,
                        content = {
                            Image(
                                painter = painter,
                                contentDescription = "project",
                                modifier = Modifier.size(120.dp),
                                alignment = Alignment.Center
                            )
                            Text(
                                text = overviewDTO!!.projectTitle,
                                modifier = Modifier.padding(5.dp),
                                style = MaterialTheme.typography.headlineMedium
                            )
                            Text(
                                text = overviewDTO!!.projectOwnerName,
                                modifier = Modifier.padding(5.dp),
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                            )





                            NotEditableCardComponent(title = "Project Summary", height = 270.dp) {
                                Text(
                                    text = overviewDTO!!.projectSummary.trimIndent(),
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Normal,
                                    modifier = Modifier.padding(5.dp)
                                )
                            }

                            NotEditableCardComponent(title = "Project Aim", height = 270.dp) {
                                Text(
                                    text = overviewDTO!!.projectAim.trimIndent(),
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Normal,
                                    modifier = Modifier.padding(5.dp)
                                )
                            }


                            val tech = overviewDTO!!.technicalRequirements.split(",")

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

                            val spec = overviewDTO!!.specialRequirements.split(",")
                            NotEditableCardComponent(title = "Specific Requirements", height = 270.dp) {
                                (0..spec.size - 1).forEachIndexed { index, _ ->
                                    Card(
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



                            NotEditableCardComponent(
                                title = "Project Date Information",
                                height = 270.dp
                            ) {
                                RowBasedCardComponent(
                                    title = "Start Date",
                                    value = overviewDTO!!.startDate
                                )
                                RowBasedCardComponent(
                                    title = "Expected Completion Date",
                                    value = overviewDTO!!.expectedCompletionDate
                                )
                                RowBasedCardComponent(
                                    title = "Application Deadline",
                                    value = overviewDTO!!.applicationDeadline
                                )
                                RowBasedCardComponent(
                                    title = "Feedback Time Range",
                                    value = overviewDTO!!.feedbackTimeRange
                                )
                            }


                            NotEditableCardComponent(title = "Project Information", height = 280.dp) {
                                RowBasedCardComponent(
                                    title = "Max Participant",
                                    value = overviewDTO!!.maxParticipant.toString()
                                )
                                RowBasedCardComponent(
                                    title = "Profession Level",
                                    value = overviewDTO!!.professionLevel
                                )
                                RowBasedCardComponent(
                                    title = "Project Level",
                                    value = overviewDTO!!.projectLevel
                                )
                                RowBasedCardComponent(
                                    title = "Interview Type",
                                    value = overviewDTO!!.interviewType
                                )
                                RowBasedCardComponent(
                                    title = "Project Status",
                                    value = overviewDTO!!.projectStatus
                                )
                            }

                            NotEditableCardComponent("Tags", 250.dp) {
                                FlowRow(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentSize(Alignment.TopCenter),
                                ) {
                                    (0..overviewDTO!!.projectTags!!.size - 1).forEach {
                                        TagComponent(text = overviewDTO!!.projectTags[it].tagName)
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
}


@Composable
fun RowBasedCardComponent(title: String, value: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .border(
                BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(5.dp)
            )
    ) {
        Row {
            Text(
                text = "-${title}:",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(5.dp)
            )
            Text(
                text = value,
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}

/*
@Preview
@Composable
fun ProjectOverviewScreenPreview() {
    CallOfProjectAndroidTheme {
        ProjectOverviewScreen(rememberNavController())
    }
}*/
