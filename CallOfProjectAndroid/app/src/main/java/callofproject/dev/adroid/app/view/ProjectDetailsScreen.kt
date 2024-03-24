package callofproject.dev.adroid.app.view

import android.annotation.SuppressLint
import android.util.Log
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
import coil.compose.rememberAsyncImagePainter
import java.util.UUID

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProjectDetailsScreen(
    navController: NavController,
    projectId: String,
    viewModel: ProjectViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val detailDTO by viewModel.detail.collectAsState()
    val painter: Painter = rememberAsyncImagePainter(detailDTO?.project_image_path)
    val isLoading by remember { viewModel.isLoading }


    DisposableEffect(Unit) {
        viewModel.findProjectDetailsByProjectId(UUID.fromString(projectId))
        onDispose { /* Dispose işlemi gerekli değil */ }
    }

    if (isLoading)
        CircularProgressIndicator()

    else
        Scaffold(
            topBar = { topNavigationBar(navController, projectId) },
            bottomBar = { BottomBarComponent(navController = navController) }
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
                    projectHeader(painter, detailDTO)
                    projectInformationSections(detailDTO)
                    projectParticipants(detailDTO!!.project_participants)
                    projectTags(detailDTO!!.project_tags)
                }
            }
        }
}

@Composable
fun projectHeader(painter: Painter, detailDTO: ProjectDetailDTO?) {
    Image(
        painter = painter,
        contentDescription = "project",
        modifier = Modifier.size(120.dp),
        alignment = Alignment.Center
    )
    Text(
        text = detailDTO!!.project_owner_name,
        modifier = Modifier.padding(5.dp),
        style = MaterialTheme.typography.headlineMedium
    )
    Text(
        text = detailDTO.project_owner_name,
        modifier = Modifier.padding(5.dp),
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
fun projectInformationSections(detailDTO: ProjectDetailDTO?) {
    val sections = listOf(
        "Project Summary" to detailDTO!!.project_summary,
        "Project Aim" to detailDTO.project_aim,
        "Project Description" to detailDTO.project_description,
        "Technical Requirements" to (0..detailDTO.technical_requirements.size).joinToString("\n") { detailDTO.technical_requirements[it] },
        "Specific Requirements" to (0..detailDTO.special_requirements.size).joinToString("\n") { detailDTO.special_requirements[it] },
        "Project Date Information" to "Start Date: ${detailDTO.start_date}\nExpected Completion Date: ${detailDTO.expected_completion_date}\n...",
        "Project Information" to "Max Participant: ${detailDTO.max_participant}\nProfession Level: ${detailDTO.project_profession_level}\n...",
        "Admin Notes" to detailDTO.admin_note
    )

    sections.forEach { (title, content) ->
        NotEditableCardComponent(title = title, height = 270.dp) {
            if (title.contains("Requirements")) {
                (1..10).forEachIndexed { index, _ ->
                    requirementCard("- $index")
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
fun requirementCard(text: String) {
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
fun projectParticipants(participants: List<ProjectParticipantDTO>) {
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
fun projectTags(projectTags: List<ProjectTag>) {


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