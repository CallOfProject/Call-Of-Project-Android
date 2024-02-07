package callofproject.dev.adroid.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import callofproject.dev.adroid.app.ui.theme.CallOfProjectAndroidTheme
import callofproject.dev.adroid.app.util.FORGOT_PASSWORD_PAGE
import callofproject.dev.adroid.app.util.LOGIN_PAGE
import callofproject.dev.adroid.app.util.MAIN_PAGE
import callofproject.dev.adroid.app.util.NOTIFICATION_PAGE
import callofproject.dev.adroid.app.util.PROFILE_PAGE
import callofproject.dev.adroid.app.util.PROJECT_DETAILS_PAGE
import callofproject.dev.adroid.app.util.PROJECT_OVERVIEW_PAGE
import callofproject.dev.adroid.app.util.REGISTER_PAGE
import callofproject.dev.adroid.app.view.ForgotPasswordScreen
import callofproject.dev.adroid.app.view.LoginScreen
import callofproject.dev.adroid.app.view.MainScreen
import callofproject.dev.adroid.app.view.NotificationScreen
import callofproject.dev.adroid.app.view.ProfileScreen.Companion.ProfileScreenComponent
import callofproject.dev.adroid.app.view.ProjectDetailsScreen
import callofproject.dev.adroid.app.view.ProjectOverviewScreen
import callofproject.dev.adroid.app.view.RegisterScreen

class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContent {
            CallOfProjectAndroidTheme { // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    AppStartPoint()
                }
            }
        }
    }

    companion object
    {
        @Composable
        fun AppStartPoint()
        {
            val noEnterTransition : AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 0), initialAlpha = 0f)
            }

            val noExitTransition : AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 0), targetAlpha = 0f)
            }

            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = LOGIN_PAGE, enterTransition = { noEnterTransition() }, exitTransition = { noExitTransition() }) {
                composable(LOGIN_PAGE) { LoginScreen(navController = navController) }
                composable(MAIN_PAGE) { MainScreen(navController = navController) }
                composable(NOTIFICATION_PAGE) { NotificationScreen(navController = navController) }
                composable(REGISTER_PAGE) { RegisterScreen(navController = navController) }
                composable(PROJECT_OVERVIEW_PAGE) { ProjectOverviewScreen(navController = navController) }
                composable(PROJECT_DETAILS_PAGE) { ProjectDetailsScreen(navController = navController) }
                composable(FORGOT_PASSWORD_PAGE) { ForgotPasswordScreen(navController = navController) }
                composable(PROFILE_PAGE) { ProfileScreenComponent(navController = navController) }
            }
        }

        @Preview(showBackground = true)
        @Composable
        fun GreetingPreview()
        {
            CallOfProjectAndroidTheme {
                AppStartPoint()
            }
        }
    }
}

