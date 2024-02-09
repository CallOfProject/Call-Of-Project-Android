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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import callofproject.dev.adroid.app.R
import callofproject.dev.adroid.app.ui.theme.CallOfProjectAndroidTheme
import callofproject.dev.adroid.app.view.util.NotEditableCardComponent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProjectDetailsScreen(navController: NavController) {
    Scaffold(
        topBar = { topNavigationBar(navController) },
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
                projectHeader()
                projectInformationSections()
                projectParticipants()
                projectTags()
            }
        }
    }
}

@Composable
fun projectHeader() {
    Image(
        painter = painterResource(id = R.drawable.project_icon),
        contentDescription = "project",
        modifier = Modifier.size(120.dp),
        alignment = Alignment.Center
    )
    Text(text = "Call-Of-Project", modifier = Modifier.padding(5.dp), style = MaterialTheme.typography.headlineMedium)
    Text(text = "Nuri Can OZTURK", modifier = Modifier.padding(5.dp), style = MaterialTheme.typography.bodyMedium)
}

@Composable
fun projectInformationSections() {
    val sections = listOf(
        "Project Summary" to "Call-Of-Project is a platform that allows you to create and manage your projects...",
        "Project Aim" to "The aim of the project is to create a platform where you can create and manage your projects...",
        "Project Description" to "The description of the project is to create a platform where you can create and manage your projects...",
        "Technical Requirements" to (1..10).joinToString("\n") { "- Req-$it" },
        "Specific Requirements" to (1..10).joinToString("\n") { "- Req-$it" },
        "Project Date Information" to "Start Date: 25/02/2024\nExpected Completion Date: 25/02/2024\n...",
        "Project Information" to "Max Participant: 5\nProfession Level: EXPERT\n...",
        "Admin Notes" to "Max Participant: 5\nProfession Level: EXPERT\n..."
    )

    sections.forEach { (title, content) ->
        NotEditableCardComponent(title = title, height = 270.dp) {
            if (title.contains("Requirements")) {
                (1..10).forEachIndexed { index, _ ->
                    requirementCard("- Req-$index")
                }
            } else {
                Text(text = content, fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
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
            .border(BorderStroke(1.dp, MaterialTheme.colorScheme.primary), shape = RoundedCornerShape(5.dp))
    ) {
        Text(text = text, fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
    }
}

@Composable
fun projectParticipants() {
    // Kod burada, örnek olarak sadece bir katılımcı için
    NotEditableCardComponent(title = "Project Participants", height = 280.dp) {
        RowBasedCardComponent(title = "Nuri Can ÖZTÜRK", value = "Project Owner")
        RowBasedCardComponent(title = "Ahmet KOÇ", value = "Participant")
        RowBasedCardComponent(title = "Emir KAFADAR", value = "Participant")
        // Diğer katılımcılar için benzer şekilde ekleme yapılabilir
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
fun projectTags() {
    val tags = listOf("JAVA", "Spring Boot", "Angular 16.0.x", "RESTFul API", "Python", "Kotlin", "Android")

    NotEditableCardComponent(title = "Tags", height = 250.dp) {
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            //mainAxisSpacing = 8.dp,
            //crossAxisSpacing = 8.dp
        ) {
            tags.forEach { tag ->
                TagComponent(text = tag)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DetailPreview()
{
    CallOfProjectAndroidTheme {
        ProjectDetailsScreen(navController = rememberNavController())

    }
}