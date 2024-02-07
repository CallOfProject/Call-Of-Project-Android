package callofproject.dev.adroid.app.view

import android.annotation.SuppressLint
import android.app.Activity
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
import callofproject.dev.adroid.app.view.util.LifeCycleObserver
import callofproject.dev.adroid.app.view.util.NormalTextField
import callofproject.dev.adroid.app.view.util.PasswordTextField


@SuppressLint("UnrememberedMutableState")
@Composable
fun LoginScreen(navController : NavController)
{
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current
    val onBackPressedCallback = remember {
        object : OnBackPressedCallback(true)
        {
            override fun handleOnBackPressed()
            {
                if (context is Activity)
                    context.finish()

            }
        }
    }

    BackHandler(onBack = onBackPressedCallback::handleOnBackPressed)

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())) {
            Image(painter = painterResource(id = R.drawable.cop_logo), contentDescription = "Logo", modifier = Modifier
                .size(250.dp)
                .align(Alignment.CenterHorizontally), contentScale = ContentScale.FillWidth)

            NormalTextField("username", username, { username = it })
            PasswordTextField("password", password, { password = it })

            Button(onClick = { navController.navigate(MAIN_PAGE) }, modifier = Modifier
                .width(250.dp)
                .align(Alignment.CenterHorizontally), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF295a8c))) {
                Text("Login")
            }


            Button(onClick = { navController.navigate(REGISTER_PAGE) }, modifier = Modifier
                .width(250.dp)
                .align(Alignment.CenterHorizontally), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF295a8c))) {
                Text("Register")
            }

            Button(onClick = { navController.navigate(FORGOT_PASSWORD_PAGE) }, modifier = Modifier
                .width(250.dp)
                .align(Alignment.CenterHorizontally), colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF295a8c),
            )) {
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
