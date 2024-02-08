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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import callofproject.dev.adroid.app.R
import callofproject.dev.adroid.app.view.util.NotEditableCardComponent


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProjectDetailsScreen(navController : NavController)
{
    Scaffold(topBar = topNavigationBar(navController), bottomBar = bottomBarComponent(navController = navController)) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it), contentAlignment = Alignment.Center) {
            Column(modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top, content = {
                Image(painter = painterResource(id = R.drawable.project_icon), contentDescription = "project", modifier = Modifier.size(120.dp), alignment = Alignment.Center)
                Text(text = "Call-Of-Project", modifier = Modifier.padding(5.dp), style = MaterialTheme.typography.headlineMedium)
                Text(text = "Nuri Can OZTURK", modifier = Modifier.padding(5.dp), style = MaterialTheme.typography.bodyMedium)





                NotEditableCardComponent(title = "Project Summary", height = 270.dp) {
                    Text(text = """
                    Call-Of-Project is a platform that allows you to create and manage your projects. You can create a project and add your friends to your project. You can also join your friends' projects
                """.trimIndent(), fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                }

                NotEditableCardComponent(title = "Project Aim", height = 270.dp) {
                    Text(text = """
                    The aim of the project is to create a platform where you can create and manage your projects. You can create a project and add your friends to your project. You can also join your friends' projects                   
                """.trimIndent(), fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                }

                NotEditableCardComponent(title = "Project Description", height = 270.dp) {
                    Text(text = """
                    The description of the project is to create a platform where you can create and manage your projects. You can create a project and add your friends to your project. You can also join your friends' projects          
                """.trimIndent(), fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                }



                NotEditableCardComponent(title = "Technical Requirements", height = 270.dp) {
                    (1..10).forEachIndexed { index, _ ->
                        Card(modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .border(BorderStroke(1.dp, MaterialTheme.colorScheme.primary), shape = RoundedCornerShape(5.dp))) {
                            Text(text = "- Req-${index}", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                        }
                    }
                }

                NotEditableCardComponent(title = "Specific Requirements", height = 270.dp) {
                    (1..10).forEachIndexed { index, _ ->
                        Card(modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .border(BorderStroke(1.dp, MaterialTheme.colorScheme.primary), shape = RoundedCornerShape(5.dp))) {
                            Text(text = "- Req-${index}", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                        }
                    }
                }



                NotEditableCardComponent(title = "Project Date Information", height = 270.dp) {
                    RowBasedCardComponent(title = "Start Date", value = "25/02/2024")
                    RowBasedCardComponent(title = "Expected Completion Date", value = "25/02/2024")
                    RowBasedCardComponent(title = "Application Deadline", value = "25/02/2024")
                    RowBasedCardComponent(title = "Feedback Time Range", value = "25/02/2024")
                }


                NotEditableCardComponent(title = "Project Information", height = 280.dp) {
                    RowBasedCardComponent(title = "Max Participant", value = "5")
                    RowBasedCardComponent(title = "Profession Level", value = "EXPERT")
                    RowBasedCardComponent(title = "Project Level", value = "EXPERT")
                    RowBasedCardComponent(title = "Interview Type", value = "CODE_INTERVIEW")
                    RowBasedCardComponent(title = "Project Status", value = "NOT_STARTED")
                }

                NotEditableCardComponent(title = "Admin Notes", height = 280.dp) {
                    RowBasedCardComponent(title = "Max Participant", value = "5")
                    RowBasedCardComponent(title = "Profession Level", value = "EXPERT")
                    RowBasedCardComponent(title = "Project Level", value = "EXPERT")
                    RowBasedCardComponent(title = "Interview Type", value = "CODE_INTERVIEW")
                    RowBasedCardComponent(title = "Project Status", value = "NOT_STARTED")
                }


                NotEditableCardComponent(title = "Project Participants", height = 280.dp) {
                    RowBasedCardComponent(title = "Nuri Can ÖZTÜRK", value = "Project Owner")
                    RowBasedCardComponent(title = "Emir KAFADAR", value = "Participant")
                    RowBasedCardComponent(title = "Ahmet KOÇ", value = "Participant")
                }




                NotEditableCardComponent("Tags", 250.dp) {
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(Alignment.TopCenter),
                    ) {
                        Tag(text = "JAVA")
                        Tag(text = "Spring Boot")
                        Tag(text = "Angular 16.0.x")
                        Tag(text = "RESTFul API")
                        Tag(text = "Python")
                        Tag(text = "Python")
                        Tag(text = "Kotlin")
                        Tag(text = "Kotlin")
                        Tag(text = "Android")
                        Tag(text = "Android")
                    }
                }


            })
        }
    }
}

