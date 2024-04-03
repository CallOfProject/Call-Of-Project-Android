package callofproject.dev.adroid.app.authentication.login.presentation

import android.content.Context
import android.widget.Toast
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import callofproject.dev.adroid.app.R
import callofproject.dev.adroid.app.authentication.login.presentation.LoginEvent.OnLoginButtonClick
import callofproject.dev.adroid.app.util.MAIN_PAGE
import callofproject.dev.adroid.app.util.REGISTER_PAGE
import callofproject.dev.adroid.app.view.util.NormalTextField
import callofproject.dev.adroid.app.view.util.PasswordTextField
import callofproject.dev.adroid.servicelib.dto.UserLoginDTO


@Composable
fun ObserveLoginOperation(
    viewModel: AuthenticationViewModel,
    navController: NavController,
    context: Context
) {

    val loginResult = viewModel.state

    LaunchedEffect(loginResult) {
        loginResult.let { result ->
            if (result.isClickedLogin && result.isSuccess) {
                Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                navController.navigate(MAIN_PAGE)
            }
            if (result.isClickedLogin && !result.isSuccess) {
                Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthenticationViewModel = hiltViewModel()
) {

    val buttonColors = ButtonDefaults.buttonColors(containerColor = Color(0xFF295a8c))

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current

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
                onClick = {
                    viewModel.onEvent(OnLoginButtonClick(UserLoginDTO(username, password)))
                },
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
