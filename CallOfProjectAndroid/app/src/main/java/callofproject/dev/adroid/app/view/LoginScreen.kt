package callofproject.dev.adroid.app.view

import android.app.Activity
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import callofproject.dev.adroid.app.R
import callofproject.dev.adroid.app.util.FORGOT_PASSWORD_PAGE
import callofproject.dev.adroid.app.util.MAIN_PAGE
import callofproject.dev.adroid.app.util.REGISTER_PAGE
import callofproject.dev.adroid.app.view.util.NormalTextField
import callofproject.dev.adroid.app.view.util.PasswordTextField

// Buton renklerini ortak bir değişkende tanımlayarak tekrarı önleyin


@Composable
fun LoginScreen(navController : NavController)
{
    val buttonColors = ButtonDefaults.buttonColors(containerColor = Color(0xFF295a8c))

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current
    val onBackPressedCallback = remember {
        object : OnBackPressedCallback(true)
        {
            override fun handleOnBackPressed()
            {
                if (context is Activity) context.finish()
            }
        }
    }

    // Geri tuşu işleyicisini doğrudan BackHandler ile yönetin
    BackHandler(onBack = onBackPressedCallback::handleOnBackPressed)

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())) {
            Image(painter = painterResource(id = R.drawable.cop_logo), contentDescription = "Logo", modifier = Modifier
                .size(250.dp)
                .align(Alignment.CenterHorizontally), contentScale = ContentScale.FillWidth)

            NormalTextField(value = username, onValueChange = { username = it }, text = "Username")
            PasswordTextField(value = password, onValueChange = { password = it }, text = "Password")

            // Buton stillerini yeniden kullanılabilir şekilde uygulayın
            Button(onClick = { navController.navigate(MAIN_PAGE) }, modifier = Modifier
                .width(250.dp)
                .align(Alignment.CenterHorizontally), colors = buttonColors) {
                Text("Login")
            }

            Button(onClick = { navController.navigate(REGISTER_PAGE) }, modifier = Modifier
                .width(250.dp)
                .align(Alignment.CenterHorizontally), colors = buttonColors) {
                Text("Register")
            }

            Button(onClick = { navController.navigate(FORGOT_PASSWORD_PAGE) }, modifier = Modifier
                .width(250.dp)
                .align(Alignment.CenterHorizontally), colors = buttonColors) {
                Text("Forgot Password")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview()
{
    LoginScreen(rememberNavController())
}
