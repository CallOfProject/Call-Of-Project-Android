package callofproject.dev.adroid.app.view

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import callofproject.dev.adroid.app.view.util.NotEditableCardComponent
import callofproject.dev.adroid.app.viewmodel.ProjectViewModel
import callofproject.dev.adroid.servicelib.dto.ProjectDetailDTO
import callofproject.dev.adroid.servicelib.dto.ProjectParticipantDTO
import callofproject.dev.adroid.servicelib.dto.ProjectTag
import callofproject.dev.core.util.UiEvent
import coil.compose.rememberAsyncImagePainter
import java.util.UUID

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProjectDetailsScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    projectId: String,
    viewModel: ProjectViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val detailDTO by viewModel.detail.collectAsState()
    val painter: Painter = rememberAsyncImagePainter(detailDTO?.projectImagePath)
    val isLoading by remember { viewModel.isLoading }



    DisposableEffect(Unit) {
        viewModel.findProjectDetailsByProjectId(UUID.fromString(projectId))
        onDispose { }
    }

    if (isLoading)
        CircularProgressIndicator()
    else
        Scaffold(
            /*topBar = { TopNavigationBar(navController, projectId) },
            bottomBar = { BottomBarComponent(navController = navController) }*/
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    ProjectHeader(painter, detailDTO)
                    ProjectInformationSections(detailDTO)
                    ProjectParticipants(detailDTO!!.projectParticipants)
                    ProjectTags(detailDTO!!.projectTags)
                }
            }
        }
}

@Composable
fun ProjectHeader(painter: Painter, detailDTO: ProjectDetailDTO?) {
    Image(
        painter = painter,
        contentDescription = "project",
        modifier = Modifier.size(120.dp),
        alignment = Alignment.Center
    )
    Text(
        text = detailDTO!!.projectOwnerName,
        modifier = Modifier.padding(5.dp),
        style = MaterialTheme.typography.headlineMedium
    )
    Text(
        text = detailDTO.projectOwnerName,
        modifier = Modifier.padding(5.dp),
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
fun ProjectInformationSections(detailDTO: ProjectDetailDTO?) {
    val sections = listOf(
        "Project Summary" to detailDTO!!.projectSummary,
        "Project Aim" to detailDTO.projectAim,
        "Project Description" to detailDTO.projectDescription,
        "Technical Requirements" to (0..detailDTO.technicalRequirements.size).joinToString("\n") { detailDTO.technicalRequirements[it] },
        "Specific Requirements" to (0..detailDTO.specialRequirements.size).joinToString("\n") { detailDTO.specialRequirements[it] },
        "Project Date Information" to "Start Date: ${detailDTO.startDate}\nExpected Completion Date: ${detailDTO.expectedCompletionDate}\n...",
        "Project Information" to "Max Participant: ${detailDTO.maxParticipant}\nProfession Level: ${detailDTO.projectProfessionLevel}\n...",
        "Admin Notes" to detailDTO.adminNote
    )

    sections.forEach { (title, content) ->
        NotEditableCardComponent(title = title, height = 270.dp) {
            if (title.contains("Requirements")) {
                (1..10).forEachIndexed { index, _ ->
                    RequirementCard("- $index")
                }
            } else {
                Text(
                    text = content,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
    }
}

@Composable
fun RequirementCard(text: String) {
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
            text = text,
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(5.dp)
        )
    }
}

@Composable
fun ProjectParticipants(participants: List<ProjectParticipantDTO>) {
    NotEditableCardComponent(title = "Project Participants", height = 280.dp) {
        participants.forEach {
            RowBasedCardComponent(title = it.full_name, value = it.username)
        }
    }
}

@Composable
fun TagComponent(text: String) {
    Card(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 2.dp)
            .border(BorderStroke(1.dp, MaterialTheme.colorScheme.primary), RoundedCornerShape(50)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(8.dp),
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface)
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProjectTags(projectTags: List<ProjectTag>) {


    NotEditableCardComponent(title = "Tags", height = 250.dp) {
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            //mainAxisSpacing = 8.dp,
            //crossAxisSpacing = 8.dp
        ) {
            projectTags.forEach { tag ->
                TagComponent(text = tag.tagName)
            }
        }
    }
}