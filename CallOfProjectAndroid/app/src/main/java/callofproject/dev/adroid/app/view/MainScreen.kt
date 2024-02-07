package callofproject.dev.adroid.app.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import callofproject.dev.adroid.app.R
import callofproject.dev.adroid.app.ui.theme.CallOfProjectAndroidTheme
import callofproject.dev.adroid.app.util.*


private const val BOTTOM_NAVBAR_HOME = "Home"
private const val BOTTOM_NAVBAR_PROFILE = "Profile"
private const val BOTTOM_NAVBAR_NOTIFICATION = "Notification"
private const val BOTTOM_NAVBAR_PROJECT = "Project"
private val BOTTOM_NAVBAR_COMPONENTS = arrayOf(BOTTOM_NAVBAR_HOME, BOTTOM_NAVBAR_PROFILE, BOTTOM_NAVBAR_NOTIFICATION, BOTTOM_NAVBAR_PROJECT)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navController : NavController)
{
    Scaffold(bottomBar = bottomBarComponent(navController), topBar = topBarComponent()) {
        Column(modifier = Modifier
            .padding(it)
            .padding(10.dp)
            .verticalScroll(ScrollState(0)), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally, content = {

            (0..10).forEach { _ ->
                ProjectCardComponent(navController)
            }


        })
    }

}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview()
{
    CallOfProjectAndroidTheme {
        MainScreen(rememberNavController())
    }
}


@Composable
fun ProjectCardComponent(navController : NavController)
{
    Card(modifier = Modifier
        .clickable { navController.navigate(PROJECT_OVERVIEW_PAGE) }
        .fillMaxWidth()
        .height(150.dp)
        .padding(10.dp), shape = RoundedCornerShape(10.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxSize(), content = {
            Image(painter = painterResource(id = R.drawable.project_icon), contentDescription = "project", modifier = Modifier
                .size(80.dp)
                .align(Alignment.CenterVertically), alignment = Alignment.Center)

            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(10.dp), content = {
                Text(text = "Project", style = MaterialTheme.typography.headlineSmall)
                Text(text = "Project Descrip tionfds glkdso gksadgj sdagljnf dlgjldfkj glfdjkgdsfl şfjksdlfj dslfjdslkfk sdlfkdjsf dskjf jsldjf dskjf sdajkf sadjksa", style = MaterialTheme.typography.bodySmall)
            })

        })
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topBarComponent() : @Composable () -> Unit
{
    return {
        TopAppBar(title = { Text(text = "Main Screen") }, navigationIcon = {
            Image(painter = painterResource(id = R.drawable.search_icon), contentDescription = "Menu", modifier = Modifier.size(24.dp))
        }, actions = {
            Image(painter = painterResource(id = R.drawable.notification_icon), contentDescription = "Notification", modifier = Modifier.size(24.dp))
        })
    }
}


@Composable
fun bottomBarComponent(navController : NavController) : @Composable () -> Unit
{
    val selectedItem = remember { mutableIntStateOf(0) }
    return {
        NavigationBar(modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)) {
            BOTTOM_NAVBAR_COMPONENTS.forEachIndexed { index, item ->
                run {
                    NavigationBarItem(selected = false, onClick = {
                        selectedItem.intValue = index
                        handleNavbarComponentClicked(navController, BOTTOM_NAVBAR_COMPONENTS[index]);
                    }, label = { Text(text = item) }, icon = {
                        when (item)
                        {
                            BOTTOM_NAVBAR_HOME         -> Image(painter = painterResource(id = R.drawable.home_icon), contentDescription = "Home", modifier = Modifier.size(24.dp))
                            BOTTOM_NAVBAR_PROFILE      -> Image(painter = painterResource(id = R.drawable.account), contentDescription = "Profile", modifier = Modifier.size(24.dp))
                            BOTTOM_NAVBAR_PROJECT      -> Image(painter = painterResource(id = R.drawable.project_icon), contentDescription = "Project", modifier = Modifier.size(24.dp))
                            BOTTOM_NAVBAR_NOTIFICATION -> Image(painter = painterResource(id = R.drawable.notification_icon), contentDescription = "Notifications", modifier = Modifier.size(24.dp))
                        }
                    })
                }
            }
        }
    }
}


fun handleNavbarComponentClicked(navController : NavController, selectedComponent : String)
{
    when (selectedComponent)
    {
        BOTTOM_NAVBAR_HOME         -> navController.navigate(MAIN_PAGE)
        BOTTOM_NAVBAR_PROFILE      -> navController.navigate(PROFILE_PAGE)
        BOTTOM_NAVBAR_NOTIFICATION -> navController.navigate(NOTIFICATION_PAGE)
        BOTTOM_NAVBAR_PROJECT      -> navController.navigate(MY_PROJECTS_PAGE)
    }
}