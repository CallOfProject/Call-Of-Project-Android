package callofproject.dev.adroid.app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import callofproject.dev.adroid.app.navigation.navigate
import callofproject.dev.adroid.app.ui.theme.CallOfProjectAndroidTheme
import callofproject.dev.adroid.app.view.MainScreen
import callofproject.dev.adroid.app.view.MyProjectsScreen
import callofproject.dev.adroid.app.view.NotificationScreen
import callofproject.dev.adroid.app.view.ProfileScreenComponent
import callofproject.dev.adroid.app.view.ProjectDetailsScreen
import callofproject.dev.adroid.app.view.ProjectDiscoveryScreen
import callofproject.dev.adroid.app.view.ProjectOverviewScreen
import callofproject.dev.adroid.app.view.UserAboutMeEditComponent
import callofproject.dev.adroid.app.view.UserCourseEditComponent
import callofproject.dev.adroid.app.view.UserEducationEditComponent
import callofproject.dev.adroid.app.view.UserExperienceEditComponent
import callofproject.dev.adroid.app.view.UserLinkEditComponent
import callofproject.dev.adroid.app.view.UserProjectEditComponent
import callofproject.dev.android.authentication.login.LoginScreen
import callofproject.dev.android.authentication.register.RegisterScreen
import callofproject.dev.core.navigation.Route
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CallOfProjectAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppStartPoint()
                }
            }
        }
    }

    companion object {

        @Composable
        fun AppStartPoint() {
            val navController = rememberNavController()
            val scaffoldState = remember { SnackbarHostState() }
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                snackbarHost = { SnackbarHost(hostState = scaffoldState) }
            ) {
                NavHost(
                    navController = navController,
                    startDestination = Route.LOGIN_PAGE
                ) {

                    composable(Route.LOGIN_PAGE) {
                        LoginScreen(
                            scaffoldState = scaffoldState,
                            onNavigate = navController::navigate
                        )
                    }
                    composable(Route.MAIN_PAGE) { MainScreen(navController = navController) }
                    composable(Route.NOTIFICATION_PAGE) { NotificationScreen(navController = navController) }
                    composable(Route.REGISTER_PAGE) { RegisterScreen(navController = navController) }
                    composable(Route.MY_PROJECTS_PAGE) { MyProjectsScreen(navController = navController) }

                    composable(
                        "${Route.PROJECT_OVERVIEW_PAGE}/{projectId}", arguments = listOf(
                            navArgument("projectId") {
                                type = NavType.StringType
                            })
                    )
                    {
                        val projectId = remember { it.arguments?.getString("projectId") }
                        ProjectOverviewScreen(
                            navController = navController, projectId = projectId ?: ""
                        )
                    }
                    composable(Route.PROJECT_DISCOVERY_PAGE) { ProjectDiscoveryScreen(navController = navController) }

                    composable(
                        "${Route.PROJECT_DETAILS_PAGE}/{projectId}",
                        arguments = listOf(
                            navArgument("projectId") {
                                type = NavType.StringType
                            })
                    )
                    {

                        val projectId = remember {
                            it.arguments?.getString("projectId")
                        }
                        ProjectDetailsScreen(
                            onNavigate = navController::navigate,
                            projectId = projectId ?: ""
                        )
                    }

                    composable(Route.PROFILE_PAGE) { ProfileScreenComponent(navController = navController) }
                    composable(Route.UPSERT_ABOUT_ME_PAGE) { UserAboutMeEditComponent(navController) }
                    composable(Route.UPSERT_EDUCATION_PAGE) {
                        UserEducationEditComponent(
                            navController
                        )
                    }
                    composable(Route.UPSERT_EXPERIENCE_PAGE) {
                        UserExperienceEditComponent(
                            navController
                        )
                    }
                    composable(Route.UPSERT_COURSE_PAGE) { UserCourseEditComponent(navController) }
                    composable(Route.UPSERT_PROJECT_PAGE) { UserProjectEditComponent(navController) }
                    composable(Route.UPSERT_LINK_PAGE) { UserLinkEditComponent(navController) }
                }
            }

        }
    }
}

