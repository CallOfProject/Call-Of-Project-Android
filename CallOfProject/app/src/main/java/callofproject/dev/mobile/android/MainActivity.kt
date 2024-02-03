package callofproject.dev.mobile.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import callofproject.dev.mobile.android.screen.ForgotPasswordScreen
import callofproject.dev.mobile.android.screen.LoginScreen
import callofproject.dev.mobile.android.screen.MainScreen
import callofproject.dev.mobile.android.screen.RegisterScreen
import callofproject.dev.mobile.android.ui.theme.CallOfProjectTheme
import callofproject.dev.mobile.android.viewmodel.LoginViewModel

class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)


        setContent {
            CallOfProjectTheme {
                App()
            }
        }
    }

    @Composable
    fun App()
    {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "login") {
            composable("login") { // ViewModel sağlama
                val loginViewModel : LoginViewModel = viewModel()
                LoginScreen(loginViewModel = loginViewModel, navController = navController)
            }
            composable("otherScreen") { // "otherScreen" rota adı
                MainScreen(navController = navController)
            }

            composable("registerScreen") { // "otherScreen" rota adı
                RegisterScreen(navController = navController)
            }

            composable("forgotPasswordScreen") { // "otherScreen" rota adı
                ForgotPasswordScreen(navController = navController)
            }
        }

    }
}
