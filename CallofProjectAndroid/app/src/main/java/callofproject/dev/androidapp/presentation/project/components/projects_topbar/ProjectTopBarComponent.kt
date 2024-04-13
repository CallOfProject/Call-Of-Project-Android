package callofproject.dev.androidapp.presentation.project.components.projects_topbar

import android.widget.Toast
import android.widget.Toast.makeText
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.presentation.project.components.projects_topbar.TopBarEvent.OnClickProjectDetailsBtn
import callofproject.dev.androidapp.presentation.project.components.projects_topbar.TopBarEvent.OnClickProjectOverviewBtn
import callofproject.dev.androidapp.util.route.UiEvent

private const val BOTTOM_NAVBAR_PROJECT_OVERVIEW = "Project Overview"
private const val BOTTOM_NAVBAR_PROJECT_DETAILS = "Project Details"

@Composable
fun ProjectTopBarComponent(
    scaffoldState: SnackbarHostState,
    projectId: String,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: TopBarViewModel = hiltViewModel(),
) {
    val isSelectedProjectOverview by remember { mutableStateOf(true) }
    val isSelectedProjectDetails by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)

                is UiEvent.ShowSnackbar -> {
                    scaffoldState.showSnackbar(message = event.msg.asString(context))
                }

                is UiEvent.ShowToastMessage -> {
                    makeText(context, event.message.asString(context), Toast.LENGTH_SHORT).show()
                }

                else -> Unit
            }
        }
    }


    NavigationBar {
        NavigationBarItem(
            selected = isSelectedProjectOverview,
            onClick = { viewModel.onEvent(OnClickProjectOverviewBtn(projectId)) },
            label = { Text(text = BOTTOM_NAVBAR_PROJECT_OVERVIEW) },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.overview_icon),
                    contentDescription = stringResource(R.string.default_image_description),
                    modifier = Modifier.size(24.dp)
                )
            }
        )

        NavigationBarItem(
            selected = isSelectedProjectDetails,
            onClick = { viewModel.onEvent(OnClickProjectDetailsBtn(projectId)) },
            label = { Text(text = BOTTOM_NAVBAR_PROJECT_DETAILS) },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_details_24),
                    contentDescription = stringResource(R.string.default_image_description),
                    modifier = Modifier.size(24.dp)
                )
            }
        )
    }
}