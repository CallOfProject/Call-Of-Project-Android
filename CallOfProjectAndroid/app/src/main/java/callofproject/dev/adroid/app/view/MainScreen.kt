package callofproject.dev.adroid.app.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import callofproject.dev.adroid.app.R
import callofproject.dev.adroid.app.ui.theme.CallOfProjectAndroidTheme
import callofproject.dev.adroid.app.util.*
import callofproject.dev.adroid.app.viewmodel.ProjectViewModel
import callofproject.dev.adroid.servicelib.dto.ProjectDiscoveryDTO
import coil.compose.rememberAsyncImagePainter

private const val BOTTOM_NAVBAR_HOME = "Home"
private const val BOTTOM_NAVBAR_PROFILE = "Profile"
private const val BOTTOM_NAVBAR_NOTIFICATION = "Notification"
private const val BOTTOM_NAVBAR_PROJECT = "My Projects"
private val BOTTOM_NAVBAR_COMPONENTS = arrayOf(
    BOTTOM_NAVBAR_HOME,
    BOTTOM_NAVBAR_PROFILE,
    BOTTOM_NAVBAR_NOTIFICATION,
    BOTTOM_NAVBAR_PROJECT,
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavController, viewModel: ProjectViewModel = hiltViewModel()) {
    val projects by viewModel.result.collectAsState()

    DisposableEffect(Unit) {
        viewModel.projectDiscovery()
        onDispose { /* Dispose işlemi gerekli değil */ }
    }

    Scaffold(
        bottomBar = { BottomBarComponent(navController) },
        topBar = { TopBarComponent("Main-Screen") }) {

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(projects?.size ?: 0) {
                projects?.get(it)?.let { project ->
                    ProjectCardComponent(navController, project)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    CallOfProjectAndroidTheme {
        MainScreen(rememberNavController())
    }
}


@Composable
fun ProjectCardComponent(
    navController: NavController,
    project: ProjectDiscoveryDTO
) {


    val painter: Painter = rememberAsyncImagePainter(project.projectImagePath)
    Card(modifier = Modifier
        .clickable {
            navController.navigate(PROJECT_OVERVIEW_PAGE + "/${project.projectId}")
        }
        .fillMaxWidth()
        .padding(10.dp), shape = RoundedCornerShape(10.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize(),
            content = {

                Image(
                    painter = painter,
                    contentDescription = "project",
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.CenterVertically),
                    alignment = Alignment.Center
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(10.dp),
                    content = {
                        Text(
                            text = project.projectTitle,
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            text = project.projectSummary,
                            style = MaterialTheme.typography.bodySmall
                        )
                    })

            })
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarComponent(title: String = "") {
    val isSearching = remember { mutableStateOf(false) }
    val isFiltering = remember { mutableStateOf(false) }
    val tf = remember { mutableStateOf("") }
    TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        navigationIconContentColor = MaterialTheme.colorScheme.secondaryContainer,
        titleContentColor = Color.Black
    ), title = {
        if (isSearching.value) {
            BasicTextField(
                value = tf.value,
                onValueChange = {
                    tf.value = it
                },
                maxLines = 1,
                singleLine = true,
                textStyle = TextStyle(color = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(5.dp, CircleShape)
                    .background(MaterialTheme.colorScheme.onPrimary, CircleShape)
                    .padding(horizontal = 20.dp, vertical = 12.dp)

            )

        } else if (isFiltering.value) {
            FilterScreen(isFiltering)

        } else {
            Text(text = title)
        }
    }, actions = {
        if (isSearching.value) {
            IconButton(onClick = {
                isSearching.value = false
                tf.value = ""
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.close_icon),
                    contentDescription = "",
                    tint = Color.Black
                )
            }
        } else {
            IconButton(onClick = {
                isSearching.value = true
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.search_icon),
                    contentDescription = "",
                    tint = Color.Black
                )
            }
        }
        IconButton(onClick = {
            isFiltering.value = true
        }) {
            Icon(
                painter = painterResource(id = R.drawable.filter_icon),
                contentDescription = "",
                tint = Color.Black
            )
        }

    })
}


@Composable
fun BottomBarComponent(navController: NavController) {
    val selectedItem = remember { mutableStateOf(0) }

    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.route) {
                MAIN_PAGE -> selectedItem.value =
                    BOTTOM_NAVBAR_COMPONENTS.indexOf(BOTTOM_NAVBAR_HOME)

                PROFILE_PAGE -> selectedItem.value =
                    BOTTOM_NAVBAR_COMPONENTS.indexOf(BOTTOM_NAVBAR_PROFILE)

                MY_PROJECTS_PAGE -> selectedItem.value =
                    BOTTOM_NAVBAR_COMPONENTS.indexOf(BOTTOM_NAVBAR_PROJECT)

                NOTIFICATION_PAGE -> selectedItem.value =
                    BOTTOM_NAVBAR_COMPONENTS.indexOf(BOTTOM_NAVBAR_NOTIFICATION)
            }
        }
    }


    NavigationBar {
        BOTTOM_NAVBAR_COMPONENTS.forEachIndexed { index, item ->
            NavigationBarItem(selected = selectedItem.value == index, onClick = {
                selectedItem.value = index
                handleNavbarComponentClicked(navController, item)
            }, label = { Text(text = item) }, icon = {
                when (item) {
                    BOTTOM_NAVBAR_HOME -> Image(
                        painter = painterResource(id = R.drawable.home_icon),
                        contentDescription = "Home",
                        modifier = Modifier.size(24.dp)
                    )

                    BOTTOM_NAVBAR_PROFILE -> Image(
                        painter = painterResource(id = R.drawable.account),
                        contentDescription = "Profile",
                        modifier = Modifier.size(24.dp)
                    )

                    BOTTOM_NAVBAR_PROJECT -> Image(
                        painter = painterResource(id = R.drawable.project_icon),
                        contentDescription = "Project",
                        modifier = Modifier.size(24.dp)
                    )

                    BOTTOM_NAVBAR_NOTIFICATION -> Image(
                        painter = painterResource(id = R.drawable.notification_icon),
                        contentDescription = "Notifications",
                        modifier = Modifier.size(24.dp)
                    )


                }
            })
        }
    }

}


fun handleNavbarComponentClicked(navController: NavController, selectedComponent: String) {
    when (selectedComponent) {
        BOTTOM_NAVBAR_HOME -> navController.navigate(MAIN_PAGE)
        BOTTOM_NAVBAR_PROFILE -> navController.navigate(PROFILE_PAGE)
        BOTTOM_NAVBAR_NOTIFICATION -> navController.navigate(NOTIFICATION_PAGE)
        BOTTOM_NAVBAR_PROJECT -> navController.navigate(MY_PROJECTS_PAGE)
    }
}
