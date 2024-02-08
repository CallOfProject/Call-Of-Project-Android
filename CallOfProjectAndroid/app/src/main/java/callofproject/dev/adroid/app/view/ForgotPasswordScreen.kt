package callofproject.dev.adroid.app.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import callofproject.dev.adroid.app.R
import callofproject.dev.adroid.app.view.util.NormalTextField


@Composable
fun ForgotPasswordScreen()
{
    var mEmail by remember { mutableStateOf("") }
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier.padding(16.dp)) {
            Image(painter = painterResource(id = R.drawable.cop_logo), contentDescription = "Logo", modifier = Modifier
                .size(256.dp)
                .align(Alignment.CenterHorizontally), contentScale = ContentScale.FillWidth)

            NormalTextField(text = "email", keyboardType = KeyboardType.Email, value = mEmail, onValueChange = { mEmail = it })

            Button(onClick = { }, modifier = Modifier.align(Alignment.CenterHorizontally), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF295a8c))) {
                Text("Send Email")
            }

        }
    }
}