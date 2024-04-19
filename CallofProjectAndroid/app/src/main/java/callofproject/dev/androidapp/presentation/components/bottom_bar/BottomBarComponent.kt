package callofproject.dev.androidapp.presentation.components.bottom_bar

import android.widget.Toast
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.util.route.Route.MAIN_PAGE
import callofproject.dev.androidapp.util.route.Route.NOTIFICATIONS
import callofproject.dev.androidapp.util.route.Route.PROFILE
import callofproject.dev.androidapp.util.route.Route.PROJECTS
import callofproject.dev.androidapp.util.route.UiEvent

private const val BOTTOM_NAVBAR_HOME = "Home"
private const val BOTTOM_NAVBAR_PROFILE = "Profile"
private const val BOTTOM_NAVBAR_NOTIFICATION = "Notification"
private const val BOTTOM_NAVBAR_PROJECT = "My Projects"

@Composable
fun BottomBarComponent(
    scaffoldState: SnackbarHostState,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: BottomBarViewModel = hiltViewModel(),
) {

    var isSelectedMainPage by remember { mutableStateOf(true) }
    var isSelectedProfile by remember { mutableStateOf(false) }
    var isSelectedProjects by remember { mutableStateOf(false) }
    var isSelectedNotifications by remember { mutableStateOf(false) }

    val context = LocalContext.current


    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)

                is UiEvent.ShowSnackbar -> {
                    scaffoldState.showSnackbar(message = event.msg.asString(context))
                }

                is UiEvent.ShowToastMessage -> {
                    Toast.makeText(context, event.message.asString(context), Toast.LENGTH_SHORT)
                        .show()
                }

                else -> Unit
            }
        }
    }


    NavigationBar {
        NavigationBarItem(
            selected = isSelectedMainPage,
            onClick = { viewModel.onEvent(BottomBarEvent.Navigate(MAIN_PAGE)) },
            label = { Text(text = BOTTOM_NAVBAR_HOME) },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.home_icon),
                    contentDescription = stringResource(R.string.default_image_description),
                    modifier = Modifier.size(24.dp)
                )
            }
        )

        NavigationBarItem(
            selected = isSelectedProfile,
            onClick = { viewModel.onEvent(BottomBarEvent.Navigate(PROFILE)) },
            label = { Text(text = BOTTOM_NAVBAR_PROFILE) },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.account),
                    contentDescription = stringResource(R.string.default_image_description),
                    modifier = Modifier.size(24.dp)
                )
            }
        )

        NavigationBarItem(
            selected = isSelectedProjects,
            onClick = { viewModel.onEvent(BottomBarEvent.Navigate(PROJECTS)) },
            label = { Text(text = BOTTOM_NAVBAR_PROJECT) },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.project_icon),
                    contentDescription = stringResource(R.string.default_image_description),
                    modifier = Modifier.size(24.dp)
                )
            }
        )


        NavigationBarItem(
            selected = isSelectedNotifications,
            onClick = { viewModel.onEvent(BottomBarEvent.Navigate(NOTIFICATIONS)) },
            label = { Text(text = BOTTOM_NAVBAR_NOTIFICATION) },
            icon = {
                /*Icon(
                    painter = painterResource(R.drawable.notification_icon),
                    contentDescription = stringResource(R.string.default_image_description),
                    modifier = Modifier.size(24.dp)
                )*/

                BadgedBox(
                    badge = {
                        //Text(text = viewModel.notificationCount.toString())
                        Badge()
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.notification_icon),
                        contentDescription = stringResource(R.string.default_image_description),
                        modifier = Modifier.size(24.dp)
                    )
                }
            },
        )
    }
}