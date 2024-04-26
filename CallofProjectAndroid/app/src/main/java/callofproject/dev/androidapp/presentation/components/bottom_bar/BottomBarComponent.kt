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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.presentation.components.bottom_bar.BottomBarViewModel.Companion.selectedItemIndex
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
    val items = listOf(
        BottomNavigationItem(
            title = BOTTOM_NAVBAR_HOME,
            selectedIcon = painterResource(R.drawable.home_icon),
            unselectedIcon = painterResource(R.drawable.home_icon),
            hasNews = false,
        ),
        BottomNavigationItem(
            title = BOTTOM_NAVBAR_PROFILE,
            selectedIcon = painterResource(R.drawable.account),
            unselectedIcon = painterResource(R.drawable.account),
            hasNews = false,
        ),

        BottomNavigationItem(
            title = BOTTOM_NAVBAR_PROJECT,
            selectedIcon = painterResource(R.drawable.project_icon),
            unselectedIcon = painterResource(R.drawable.project_icon),
            hasNews = false,
        ),

        BottomNavigationItem(
            title = BOTTOM_NAVBAR_NOTIFICATION,
            selectedIcon = painterResource(R.drawable.notification_icon),
            unselectedIcon = painterResource(R.drawable.notification_icon),
            hasNews = true
        )
    )

    val context = LocalContext.current


    LaunchedEffect(key1 = true) {
        viewModel.findAllUnReadNotifications()
    }

    LaunchedEffect(key1 = viewModel.state.unReadNotificationsCount) {
        items[3].badgeCount = viewModel.state.unReadNotificationsCount.toInt()
    }

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
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex.value == index,
                onClick = {
                    selectedItemIndex.value = index
                    when (index) {
                        0 -> viewModel.onEvent(BottomBarEvent.Navigate(MAIN_PAGE))
                        1 -> viewModel.onEvent(BottomBarEvent.Navigate(PROFILE))
                        2 -> viewModel.onEvent(BottomBarEvent.Navigate(PROJECTS))
                        3 -> viewModel.onEvent(BottomBarEvent.Navigate(NOTIFICATIONS))
                    }
                },
                label = { Text(text = item.title) },
                icon = {
                    if (index != 3)
                        Icon(
                            painter = item.selectedIcon,
                            contentDescription = stringResource(R.string.default_image_description),
                            modifier = Modifier.size(24.dp)
                        )
                    else {
                        BadgedBox(
                            badge = {
                                if (viewModel.state.unReadNotificationsCount != 0L) {
                                    Badge {
                                        Text(text = viewModel.state.unReadNotificationsCount.toString())
                                    }
                                } else if (item.hasNews) {
                                    Badge()
                                }
                            }
                        ) {
                            Icon(
                                painter = item.selectedIcon,
                                contentDescription = item.title,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            )
        }
    }
}