

package callofproject.dev.mobile.android.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation

import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import callofproject.dev.mobile.android.FORGOT_PASSWORD_PAGE
import callofproject.dev.mobile.android.MAIN_PAGE
import callofproject.dev.mobile.android.R
import callofproject.dev.mobile.android.REGISTER_PAGE
import callofproject.dev.mobile.android.viewmodel.LoginViewModel

@Composable
fun LoginScreen(loginViewModel : LoginViewModel = viewModel(), navController : NavController)
{
    val username by loginViewModel.username
    val password by loginViewModel.password

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(painter = painterResource(id = R.drawable.cop_logo), contentDescription = "Logo", modifier = Modifier
                .size(256.dp)
                .align(Alignment.CenterHorizontally), contentScale = ContentScale.FillWidth)

            OutlinedTextField(value = username, onValueChange = {
                loginViewModel.username.value = it
            }, label = {
                Text("username", color = Color.Gray)
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF295a8c), unfocusedBorderColor = Color.Gray))


            OutlinedTextField(value = password, onValueChange = {
                loginViewModel.password.value = it
            }, label = { Text("Password", color = Color.Gray) }, modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp), visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF295a8c), unfocusedBorderColor = Color.Gray))


            Button(onClick = { loginButton(loginViewModel, navController) }, modifier = Modifier
                .width(300.dp)
                .align(Alignment.CenterHorizontally), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF295a8c))) {
                Text("Login")
            }

            Button(onClick = { registerButton(navController) }, modifier = Modifier
                .width(300.dp)
                .align(Alignment.CenterHorizontally), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF295a8c))) {
                Text("Register")
            }

            Button(onClick = { clickForgotPasswordButton(navController) }, modifier = Modifier
                .width(300.dp)
                .align(Alignment.CenterHorizontally), colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF295a8c),
            )) {
                Text("Forgot Password")
            }
        }
    }

}

fun clickForgotPasswordButton(navController : NavController)
{
    navController.navigate(FORGOT_PASSWORD_PAGE)
}

fun registerButton(navController : NavController)
{
    navController.navigate(REGISTER_PAGE)
}

fun loginButton(loginViewModel : LoginViewModel, navController : NavController)
{
    loginViewModel.login()
    navController.navigate(MAIN_PAGE)
}
