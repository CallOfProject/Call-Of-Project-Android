package callofproject.dev.mobile.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import callofproject.dev.mobile.android.view.ForgotPasswordScreen
import callofproject.dev.mobile.android.view.LoginScreen
import callofproject.dev.mobile.android.view.MainScreen
import callofproject.dev.mobile.android.view.RegisterScreen
import callofproject.dev.mobile.android.ui.theme.CallOfProjectTheme

class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)

        setContent {
            CallOfProjectTheme {
                AppStartPoint()
            }
        }
    }

    @Composable
    fun AppStartPoint()
    {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = LOGIN_PAGE) {
            composable(LOGIN_PAGE) { LoginScreen(navController = navController) }
            composable(MAIN_PAGE) { MainScreen(navController = navController) }
            composable(REGISTER_PAGE) { RegisterScreen(navController = navController) }
            composable(FORGOT_PASSWORD_PAGE) { ForgotPasswordScreen(navController = navController) }
        }
    }
}
