package callofproject.dev.adroid.app.view

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import callofproject.dev.adroid.app.R
import callofproject.dev.adroid.app.util.MAIN_PAGE
import callofproject.dev.adroid.app.util.PROJECT_DETAILS_PAGE
import callofproject.dev.adroid.app.util.PROJECT_OVERVIEW_PAGE

private const val PROJECT_OVERVIEW_TAB = "Project Overview"
private const val PROJECT_DETAILS_TAB = "Project Details"
private val TOP_NAVIGATION_ITEMS = arrayOf(PROJECT_OVERVIEW_TAB, PROJECT_DETAILS_TAB)

@Composable
fun TopNavigationBar(navController: NavController, projectId: String?) {
    val selectedItem = remember { mutableStateOf(0) }

    val onBackPressedCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navController.navigate(MAIN_PAGE) {
                    popUpTo(MAIN_PAGE) { inclusive = true }
                }
            }
        }
    }

    BackHandler(onBack = onBackPressedCallback::handleOnBackPressed)

    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.route) {
                PROJECT_OVERVIEW_PAGE -> selectedItem.value =
                    TOP_NAVIGATION_ITEMS.indexOf(PROJECT_OVERVIEW_TAB)

                PROJECT_DETAILS_PAGE -> selectedItem.value =
                    TOP_NAVIGATION_ITEMS.indexOf(PROJECT_DETAILS_TAB)
            }
        }
    }

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
    ) {
        TOP_NAVIGATION_ITEMS.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem.value == index,
                onClick = { // Tıklama işlemi ile manuel olarak selectedItem değerini ayarla
                    selectedItem.value = index
                    handleProjectsTopNavbarComponentClicked(navController, item, projectId)
                },
                label = { Text(text = item) },
                icon = {
                    when (item) {
                        PROJECT_OVERVIEW_TAB -> Image(
                            painter = painterResource(id = R.drawable.overview_icon),
                            contentDescription = "Overview",
                            modifier = Modifier.size(24.dp)
                        )

                        PROJECT_DETAILS_TAB -> Image(
                            painter = painterResource(id = R.drawable.baseline_details_24),
                            contentDescription = "Details",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                })
        }
    }
}

fun handleProjectsTopNavbarComponentClicked(
    navController: NavController,
    item: String,
    projectId: String?
) {
    when (item) {
        PROJECT_OVERVIEW_TAB -> navController.navigate(PROJECT_OVERVIEW_PAGE + "/${projectId}")
        PROJECT_DETAILS_TAB -> navController.navigate(PROJECT_DETAILS_PAGE + "/${projectId}")
    }
}
