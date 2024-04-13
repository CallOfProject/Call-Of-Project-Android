package callofproject.dev.androidapp.presentation.user_profile.edit

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import callofproject.dev.androidapp.presentation.components.NotEditableCardComponent

@Composable
fun UserAboutMeEditComponent() {
    val context = LocalContext.current
    var aboutMe by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp), contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            NotEditableCardComponent(
                title = "Edit About me",
                modifier = Modifier.fillMaxWidth(),
                height = 500.dp
            ) {
                TextField(
                    value = aboutMe,
                    onValueChange = { aboutMe = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(10.dp)
                        .border(
                            width = 1.dp, color = Color.Black,
                            shape = RoundedCornerShape(8.dp)
                        )
                )

                Button(
                    onClick = { },
                    modifier = Modifier
                        .width(200.dp)
                        .padding(10.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Save")
                }

                Button(
                    onClick = { },
                    modifier = Modifier
                        .width(200.dp)
                        .padding(10.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Close")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserAboutMeEditComponent() {
    UserAboutMeEditComponent()
}