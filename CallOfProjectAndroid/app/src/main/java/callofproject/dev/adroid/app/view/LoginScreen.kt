package callofproject.dev.adroid.app.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import callofproject.dev.adroid.app.R
import callofproject.dev.adroid.app.util.MAIN_PAGE
import callofproject.dev.adroid.app.util.REGISTER_PAGE
import callofproject.dev.adroid.app.view.util.NormalTextField
import callofproject.dev.adroid.app.view.util.PasswordTextField
import callofproject.dev.adroid.app.viewmodel.AuthenticationViewModel
import callofproject.dev.adroid.servicelib.dto.UserLoginDTO


@Composable
fun ObserveLoginOperation(
    viewModel: AuthenticationViewModel,
    navController: NavController,
    context: Context
) {
    val loginResult by viewModel.loginResult.collectAsState(initial = null)

    LaunchedEffect(loginResult) {
        loginResult?.let { result ->
            if (result) {
                Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                navController.navigate(MAIN_PAGE)
            } else {
                Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(navController: NavController, viewModel: AuthenticationViewModel = hiltViewModel()) {

    val buttonColors = ButtonDefaults.buttonColors(containerColor = Color(0xFF295a8c))

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current

    BackPressHandler(context)
    ObserveLoginOperation(viewModel, navController, context)

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(id = R.drawable.cop_logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(250.dp)
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.FillWidth
            )

            NormalTextField("Username", username, { username = it.trim() })
            PasswordTextField("Password", password, { password = it.trim() })

            Button(
                onClick = { handleClickLoginButton(username, password, viewModel) },
                modifier = Modifier
                    .width(250.dp)
                    .align(Alignment.CenterHorizontally),
                colors = buttonColors
            ) {
                Text("Login")
            }

            Button(
                onClick = { navController.navigate(REGISTER_PAGE) }, modifier = Modifier
                    .width(250.dp)
                    .align(Alignment.CenterHorizontally), colors = buttonColors
            ) {
                Text("Register")
            }
        }
    }
}

@Composable
fun BackPressHandler(context: Context) {

    val onBackPressedCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (context is Activity) context.finish()
            }
        }
    }

    BackHandler(onBack = onBackPressedCallback::handleOnBackPressed)
}

fun handleClickLoginButton(username: String, password: String, viewModel: AuthenticationViewModel) {
    viewModel.login(UserLoginDTO(username, password))
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    LoginScreen(rememberNavController())
}
