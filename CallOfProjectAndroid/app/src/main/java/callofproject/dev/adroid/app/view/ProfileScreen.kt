package callofproject.dev.adroid.app.view

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import callofproject.dev.adroid.app.R
import callofproject.dev.adroid.app.ui.theme.CallOfProjectAndroidTheme
import callofproject.dev.adroid.app.util.EDIT_ABOUT_ME_PAGE
import callofproject.dev.adroid.app.util.EDIT_COURSE_PAGE
import callofproject.dev.adroid.app.util.EDIT_EDUCATION_PAGE
import callofproject.dev.adroid.app.util.EDIT_EXPERIENCE_PAGE
import callofproject.dev.adroid.app.util.EDIT_LINK_PAGE
import callofproject.dev.adroid.app.util.EDIT_PROJECT_PAGE
import callofproject.dev.adroid.app.view.util.EditableCardComponent
import callofproject.dev.adroid.app.view.util.NormalTextField
import callofproject.dev.adroid.app.view.util.NotEditableCardComponent

class ProfileScreen : ComponentActivity()
{
    companion object
    {
        @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
        @Composable
        fun ProfileScreenComponent(navController : NavController)
        {
            Scaffold(topBar = topBarComponent(), bottomBar = { bottomBarComponent(navController) }) {
                Box(contentAlignment = Alignment.TopCenter, modifier = Modifier
                    .fillMaxSize()
                    .padding(it)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.verticalScroll(rememberScrollState())) {
                        UserProfileTopComponent()
                        UserRatingComponent()
                        UserAboutMeComponent(navController)
                        UserEducationComponent(navController)
                        UserExperienceComponent(navController)
                        UserCoursesComponent(navController)
                        UserProjectsComponent(navController)
                        UserLinksComponent(navController)
                    }
                }
            }
        }

        @Composable
        private fun UserRatingComponent()
        {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)) {

                Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth(0.2f)) {
                    Text(text = "User", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                        Icon(painter = painterResource(id = R.drawable.star_icon), contentDescription = "")
                        Text(text = "4.5", fontSize = 15.sp, fontWeight = FontWeight.Normal)
                    }
                }

                Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth(0.4f)) {
                    Text(text = "Feedback", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                        Icon(painter = painterResource(id = R.drawable.star_icon), contentDescription = "")
                        Text(text = "4.5", fontSize = 15.sp, fontWeight = FontWeight.Normal)
                    }
                }
            }
        }

        @Composable
        private fun UserProfileTopComponent()
        {
            Image(painter = painterResource(id = R.drawable.account), contentDescription = "Logo", Modifier
                .width(200.dp)
                .height(150.dp))
            Text(text = "Nuri Can ÖZTÜRK", fontSize = 20.sp, fontWeight = FontWeight(700), fontStyle = FontStyle.Normal)
            Text(text = "Software Engineering Student", fontSize = 15.sp, fontWeight = FontWeight.Normal)
        }

        @Composable
        private fun UserAboutMeComponent(navController : NavController)
        {
            EditableCardComponent(title = "About me", onIconClick = { navController.navigate(EDIT_ABOUT_ME_PAGE) }) {
                Text(text = "I am final year Software Engineering student.I am interesting with Backend and Android development", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
            }
        }


        @Composable
        private fun UserExperienceComponent(navController : NavController)
        {
            NotEditableCardComponent("Experience", 400.dp) {
                EditableCardComponent(title = "", onIconClick = { navController.navigate(EDIT_EXPERIENCE_PAGE) }) {
                    Text(text = "Firma: Kafein Technology", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                    Text(text = "Pozisyon: Backend Developer", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                    Text(text = "Tarih: 2021-2022", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                }
            }
        }

        @Composable
        private fun UserEducationComponent(navController : NavController)
        {
            NotEditableCardComponent("Education", 400.dp) {
                EditableCardComponent(title = "Yasar University", onIconClick = { navController.navigate(EDIT_EDUCATION_PAGE) }) {
                    Text(text = "Bölüm: Yazılım Mühendisliği", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                    Text(text = "Tarih: 2018-2024", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                    Text(text = "GPA: 2.95", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                }
            }
        }


        @Composable
        private fun UserCoursesComponent(navController : NavController)
        {
            NotEditableCardComponent("Courses", 400.dp) {
                EditableCardComponent(title = "İleri Java", onIconClick = { navController.navigate(EDIT_COURSE_PAGE) }) {
                    Text(text = "Firma: CSD", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                    Text(text = "Tarih: 2020-2023", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                    Text(text = "Açıklama: Java öğrendik", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                }

                EditableCardComponent(title = "Temel Java", onIconClick = { navController.navigate(EDIT_COURSE_PAGE) }) {
                    Text(text = "Firma: CSD", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                    Text(text = "Tarih: 2020-2023", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                    Text(text = "Açıklama: Java öğrendik", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                }
            }
        }

        @Composable
        private fun UserProjectsComponent(navController : NavController)
        {
            NotEditableCardComponent("Projects", 400.dp) {
                EditableCardComponent(title = "Project - 1", onIconClick = { navController.navigate(EDIT_PROJECT_PAGE) }) {
                    Text(text = "Açıklama: ", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                    Text(text = "Summary: Backend Developer", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                    Text(text = "Link: 2021-2022", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                }

                EditableCardComponent(title = "Project - 2", onIconClick = { navController.navigate(EDIT_PROJECT_PAGE) }) {
                    Text(text = "Açıklama: ", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                    Text(text = "Summary: Backend Developer", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                    Text(text = "Link: 2021-2022", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                }
            }
        }

        @Composable
        private fun UserLinksComponent(navController : NavController)
        {
            NotEditableCardComponent("Links", 400.dp) {
                EditableCardComponent(title = "Github", height = 150.dp, onIconClick = { navController.navigate(EDIT_LINK_PAGE) }) {
                    Text(text = "https://www.github.com/nuricanozturk01", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                }

                EditableCardComponent(title = "Linkedln", height = 150.dp, onIconClick = { navController.navigate(EDIT_LINK_PAGE) }) {
                    Text(text = "https://www.linkedln.com/nuricanozturk", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                }

                EditableCardComponent(title = "Medium", height = 150.dp, onIconClick = { navController.navigate(EDIT_LINK_PAGE) }) {
                    Text(text = "", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview()
{
    CallOfProjectAndroidTheme {
        ProfileScreen.ProfileScreenComponent(rememberNavController())
    }
}
