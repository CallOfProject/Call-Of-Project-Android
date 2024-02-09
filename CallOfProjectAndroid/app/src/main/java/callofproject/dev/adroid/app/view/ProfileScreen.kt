package callofproject.dev.adroid.app.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import callofproject.dev.adroid.app.R
import callofproject.dev.adroid.app.util.UPSERT_ABOUT_ME_PAGE
import callofproject.dev.adroid.app.util.UPSERT_COURSE_PAGE
import callofproject.dev.adroid.app.util.UPSERT_EDUCATION_PAGE
import callofproject.dev.adroid.app.util.UPSERT_EXPERIENCE_PAGE
import callofproject.dev.adroid.app.util.UPSERT_LINK_PAGE
import callofproject.dev.adroid.app.util.UPSERT_PROJECT_PAGE
import callofproject.dev.adroid.app.view.util.EditableCardComponent
import callofproject.dev.adroid.app.view.util.NotEditableCardComponent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreenComponent(navController: NavController) {

    Scaffold(
        topBar = { TopBarComponent() },
        bottomBar = { BottomBarComponent(navController) }) {
        Box(
            contentAlignment = Alignment.TopCenter, modifier = Modifier
                .fillMaxSize()
                .padding(it)
        )
        {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
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
private fun UserRatingComponent() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(0.2f)
        ) {
            Text(text = "User", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.star_icon),
                    contentDescription = ""
                )
                Text(text = "4.5", fontSize = 15.sp, fontWeight = FontWeight.Normal)
            }
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(0.4f)
        ) {
            Text(text = "Feedback", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.star_icon),
                    contentDescription = ""
                )
                Text(text = "4.5", fontSize = 15.sp, fontWeight = FontWeight.Normal)
            }
        }
    }
}

@Composable
private fun UserProfileTopComponent() {
    Image(
        painter = painterResource(id = R.drawable.account),
        contentDescription = "Logo",
        Modifier
            .width(200.dp)
            .height(150.dp)
    )
    Text(
        text = "Nuri Can ÖZTÜRK",
        fontSize = 20.sp,
        fontWeight = FontWeight(700),
        fontStyle = FontStyle.Normal
    )
    Text(
        text = "Software Engineering Student",
        fontSize = 15.sp,
        fontWeight = FontWeight.Normal
    )
}

@Composable
private fun UserAboutMeComponent(navController: NavController) {
    EditableCardComponent(
        title = "About me",
        onIconClick = { navController.navigate(UPSERT_ABOUT_ME_PAGE) }) {
        Text(
            text = "I am final year Software Engineering student.I am interesting with Backend and Android development",
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(5.dp)
        )
    }
}

@Composable
private fun UserExperienceComponent(navController: NavController) {
    EditableCardComponent("Experience",
        400.dp,
        imageVector = Icons.Filled.Add,
        imageDescription = "Add",
        onIconClick = { navController.navigate(UPSERT_EXPERIENCE_PAGE) }) {
        EditableCardComponent(
            title = "Kafein Technology",
            onIconClick = { navController.navigate(UPSERT_EXPERIENCE_PAGE) }) {
            Text(
                text = "Firma: Kafein Technology",
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(5.dp)
            )
            Text(
                text = "Pozisyon: Backend Developer",
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(5.dp)
            )
            Text(
                text = "Tarih: 2021-2022",
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}

@Composable
private fun UserEducationComponent(navController: NavController) {
    EditableCardComponent("Education",
        400.dp,
        imageVector = Icons.Filled.Add,
        imageDescription = "Add",
        onIconClick = { navController.navigate(UPSERT_EDUCATION_PAGE) }) {
        EditableCardComponent(
            title = "Yasar University",
            onIconClick = { navController.navigate(UPSERT_EDUCATION_PAGE) }) {
            Text(
                text = "Bölüm: Yazılım Mühendisliği",
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(5.dp)
            )
            Text(
                text = "Tarih: 2018-2024",
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(5.dp)
            )
            Text(
                text = "GPA: 2.95",
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}


@Composable
private fun UserCoursesComponent(navController: NavController) {
    EditableCardComponent("Courses",
        490.dp,
        imageVector = Icons.Filled.Add,
        imageDescription = "Add",
        onIconClick = { navController.navigate(UPSERT_COURSE_PAGE) }) {

        EditableCardComponent(
            title = "İleri Java",
            onIconClick = { navController.navigate(UPSERT_COURSE_PAGE) }) {
            Text(
                text = "Firma: CSD",
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(5.dp)
            )
            Text(
                text = "Tarih: 2020-2023",
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(5.dp)
            )
            Text(
                text = "Açıklama: Java öğrendik",
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(5.dp)
            )
        }


        EditableCardComponent(
            title = "Temel Java",
            onIconClick = { navController.navigate(UPSERT_COURSE_PAGE) }) {
            Text(
                text = "Firma: CSD",
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(5.dp)
            )
            Text(
                text = "Tarih: 2020-2023",
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(5.dp)
            )
            Text(
                text = "Açıklama: Java öğrendik",
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(5.dp)
            )
        }

    }
}

@Composable
private fun UserProjectsComponent(navController: NavController) {
    NotEditableCardComponent("Projects", 490.dp) {
        EditableCardComponent(
            title = "Project - 1",
            onIconClick = { navController.navigate(UPSERT_PROJECT_PAGE) }) {
            Text(
                text = "Açıklama: ",
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(5.dp)
            )
            Text(
                text = "Summary: Backend Developer",
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(5.dp)
            )
            Text(
                text = "Link: 2021-2022",
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(5.dp)
            )
        }

        EditableCardComponent(
            title = "Project - 2",
            onIconClick = { navController.navigate(UPSERT_PROJECT_PAGE) }) {
            Text(
                text = "Açıklama: ",
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(5.dp)
            )
            Text(
                text = "Summary: Backend Developer",
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(5.dp)
            )
            Text(
                text = "Link: 2021-2022",
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}

@Composable
private fun UserLinksComponent(navController: NavController) {

    NotEditableCardComponent("Links", 450.dp) {
        EditableCardComponent(
            title = "Github",
            height = 120.dp,
            padVal = PaddingValues(5.dp),
            onIconClick = { navController.navigate(UPSERT_LINK_PAGE) }) {
            Text(
                text = "https://www.github.com/nuricanozturk01",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(5.dp)
            )
        }

        EditableCardComponent(
            title = "Linkedln",
            height = 120.dp,
            padVal = PaddingValues(5.dp),
            onIconClick = { navController.navigate(UPSERT_LINK_PAGE) }) {
            Text(
                text = "https://www.linkedln.com/nuricanozturk",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(5.dp)
            )
        }

        EditableCardComponent(
            title = "Medium",
            height = 120.dp,
            padVal = PaddingValues(5.dp),
            onIconClick = { navController.navigate(UPSERT_LINK_PAGE) }) {
            Text(
                text = "http://medium.com/@nuricanozturk01/",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    CallOfProjectAndroidTheme {
        ProfileScreen.ProfileScreenComponent(rememberNavController())
    }
}
*/
