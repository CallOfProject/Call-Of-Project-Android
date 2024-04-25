package callofproject.dev.androidapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import callofproject.dev.androidapp.domain.dto.filter.ProjectFilterDTO
import callofproject.dev.androidapp.presentation.authentication.login.LoginScreen
import callofproject.dev.androidapp.presentation.authentication.signup.RegisterScreen
import callofproject.dev.androidapp.presentation.components.topbar.TopAppBarComponent
import callofproject.dev.androidapp.presentation.components.bottom_bar.BottomBarComponent
import callofproject.dev.androidapp.presentation.main_page.MainScreen
import callofproject.dev.androidapp.presentation.notifications.NotificationScreen
import callofproject.dev.androidapp.presentation.project.my_projects.MyProjectsScreen
import callofproject.dev.androidapp.presentation.project.project_details.ProjectDetailsScreen
import callofproject.dev.androidapp.presentation.project.project_filter.FilteredProjectScreen
import callofproject.dev.androidapp.presentation.project.project_overview.ProjectOverviewScreen
import callofproject.dev.androidapp.presentation.search.SearchScreen
import callofproject.dev.androidapp.presentation.user_profile.UserProfileScreen
import callofproject.dev.androidapp.ui.theme.CallofProjectAndroidTheme
import callofproject.dev.androidapp.util.route.Route.FILTERED_PROJECTS
import callofproject.dev.androidapp.util.route.Route.LOGIN
import callofproject.dev.androidapp.util.route.Route.MAIN_PAGE
import callofproject.dev.androidapp.util.route.Route.NOTIFICATIONS
import callofproject.dev.androidapp.util.route.Route.PROFILE
import callofproject.dev.androidapp.util.route.Route.PROJECTS
import callofproject.dev.androidapp.util.route.Route.PROJECT_DETAILS
import callofproject.dev.androidapp.util.route.Route.PROJECT_OVERVIEW
import callofproject.dev.androidapp.util.route.Route.SEARCH_RESULT
import callofproject.dev.androidapp.util.route.Route.SIGN_UP
import callofproject.dev.androidapp.util.route.navigate
import com.google.gson.Gson
import com.onesignal.OneSignal
import com.onesignal.debug.LogLevel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //initializePushNotifications()
        setContent {
            CallofProjectAndroidTheme {
                val navController = rememberNavController()
                val scaffoldState = remember { SnackbarHostState() }

                Scaffold(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(hostState = scaffoldState) }) {
                    NavHost(
                        navController = navController,
                        startDestination = LOGIN
                    ) {
                        composable(LOGIN) {
                            LoginScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate
                            )
                        }

                        composable(SIGN_UP) {
                            RegisterScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate
                            )
                        }

                        composable(MAIN_PAGE) {
                            MainScreen(
                                topBar = {
                                    TopAppBarComponent(
                                        title = stringResource(R.string.title_mainPage),
                                        onNavigate = navController::navigate
                                    )
                                },
                                bottomBar = {
                                    BottomBarComponent(
                                        scaffoldState = scaffoldState,
                                        onNavigate = navController::navigate
                                    )
                                },
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate
                            )
                        }

                        composable(
                            "$FILTERED_PROJECTS/{filterObj}",
                            arguments = listOf(navArgument("filterObj") {
                                type = NavType.StringType
                            })
                        )
                        {
                            val filterObj = Gson().fromJson(
                                it.arguments?.getString("filterObj"),
                                ProjectFilterDTO::class.java
                            )
                            FilteredProjectScreen(
                                filterObj = filterObj,
                                topBar = {
                                    TopAppBarComponent(
                                        title = stringResource(R.string.title_filteredProjects),
                                        onNavigate = navController::navigate
                                    )
                                },
                                bottomBar = {
                                    BottomBarComponent(
                                        scaffoldState = scaffoldState,
                                        onNavigate = navController::navigate
                                    )
                                },
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate
                            )
                        }


                        composable(
                            "$SEARCH_RESULT/{keyword}",
                            arguments = listOf(navArgument("keyword") {
                                type = NavType.StringType
                            })
                        )
                        {
                            val keyword = it.arguments?.getString("keyword")!!
                            SearchScreen(
                                keyword = keyword,
                                topBar = {
                                    TopAppBarComponent(
                                        title = stringResource(R.string.title_search).format(keyword),
                                        onNavigate = navController::navigate
                                    )
                                },
                                bottomBar = {
                                    BottomBarComponent(
                                        scaffoldState = scaffoldState,
                                        onNavigate = navController::navigate
                                    )
                                },
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate
                            )
                        }

                        composable(
                            "$PROJECT_OVERVIEW/{projectId}", arguments =
                            listOf(navArgument("projectId") {
                                type = NavType.StringType
                            })
                        ) {
                            val projectId = it.arguments?.getString("projectId")
                            ProjectOverviewScreen(
                                projectId = projectId!!,
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate
                            )

                        }

                        composable(
                            "$PROJECT_DETAILS/{projectId}", arguments =
                            listOf(navArgument("projectId") {
                                type = NavType.StringType
                            })
                        ) {
                            val projectId = it.arguments?.getString("projectId")
                            ProjectDetailsScreen(
                                projectId = projectId!!,
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate
                            )
                        }

                        composable(PROFILE) {
                            UserProfileScreen(
                                scaffoldState = scaffoldState,
                                topBar = {
                                    TopAppBarComponent(
                                        title = stringResource(R.string.title_profile),
                                        onNavigate = navController::navigate
                                    )
                                },
                                bottomBar = {
                                    BottomBarComponent(
                                        scaffoldState = scaffoldState,
                                        onNavigate = navController::navigate
                                    )
                                },
                            )

                        }

                        composable(NOTIFICATIONS) {
                            NotificationScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate,
                                topBar = {
                                    TopAppBarComponent(
                                        title = stringResource(R.string.title_notification),
                                        onNavigate = navController::navigate
                                    )
                                },
                                bottomBar = {
                                    BottomBarComponent(
                                        scaffoldState = scaffoldState,
                                        onNavigate = navController::navigate
                                    )
                                }
                            )
                        }

                        composable(PROJECTS) {
                            MyProjectsScreen(
                                scaffoldState = scaffoldState,
                                topBar = {
                                    TopAppBarComponent(
                                        onNavigate = navController::navigate,
                                        title = stringResource(R.string.title_myProjects)
                                    )
                                },
                                onNavigate = navController::navigate,
                                bottomBar = {
                                    BottomBarComponent(
                                        scaffoldState = scaffoldState,
                                        onNavigate = navController::navigate
                                    )
                                },
                            )

                        }
                    }
                }
            }
        }
    }

    private fun initializePushNotifications() {
        OneSignal.Debug.logLevel = LogLevel.VERBOSE
        OneSignal.initWithContext(this, resources.getString(R.string.one_signal_key))
        CoroutineScope(Dispatchers.IO).launch {
            OneSignal.Notifications.requestPermission(true)
        }
    }
}
