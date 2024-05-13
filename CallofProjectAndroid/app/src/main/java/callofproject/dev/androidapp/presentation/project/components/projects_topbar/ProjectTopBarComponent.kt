package callofproject.dev.androidapp.presentation.project.components.projects_topbar

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.presentation.project.components.projects_topbar.TopBarEvent.OnClickProjectDetailsBtn
import callofproject.dev.androidapp.presentation.project.components.projects_topbar.TopBarEvent.OnClickProjectOverviewBtn
import callofproject.dev.androidapp.presentation.project.components.projects_topbar.TopBarEvent.OnClickProjectParticipantsBtn
import callofproject.dev.androidapp.util.route.UiEvent

private const val BOTTOM_NAVBAR_PROJECT_OVERVIEW = "Project Overview"
private const val BOTTOM_NAVBAR_PROJECT_DETAILS = "Project Details"
private const val BOTTOM_NAVBAR_PROJECT_PARTICIPANTS = "Project Participants"

@Composable
fun ProjectTopBarComponent(
    projectId: String,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: TopBarViewModel = hiltViewModel(),
    selectedNavBar: Int = 0
) {

    val items = listOf(
        ProjectTopNavigationItem(
            title = BOTTOM_NAVBAR_PROJECT_OVERVIEW,
            selectedIcon = painterResource(R.drawable.overview_icon),
            unselectedIcon = painterResource(R.drawable.overview_icon),
        ),
        ProjectTopNavigationItem(
            title = BOTTOM_NAVBAR_PROJECT_DETAILS,
            selectedIcon = painterResource(R.drawable.baseline_details_24),
            unselectedIcon = painterResource(R.drawable.baseline_details_24),
        ),
        ProjectTopNavigationItem(
            title = BOTTOM_NAVBAR_PROJECT_PARTICIPANTS,
            selectedIcon = painterResource(R.drawable.people),
            unselectedIcon = painterResource(R.drawable.people),
        ),
    )


    LaunchedEffect(key1 = true) {
        viewModel.selectedItemIndex.value = selectedNavBar
    }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)

                else -> Unit
            }
        }
    }


    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = viewModel.selectedItemIndex.value == index,
                onClick = {
                    viewModel.selectedItemIndex.value = index
                    when (index) {
                        0 -> viewModel.onEvent(OnClickProjectOverviewBtn(projectId))
                        1 -> viewModel.onEvent(OnClickProjectDetailsBtn(projectId))
                        2 -> viewModel.onEvent(OnClickProjectParticipantsBtn(projectId))
                    }
                },
                label = { Text(text = item.title, fontSize = 10.sp) },
                icon = {
                    Icon(
                        painter = item.selectedIcon,
                        contentDescription = stringResource(R.string.default_image_description),
                        modifier = Modifier.size(24.dp)
                    )
                }
            )
        }
    }
}