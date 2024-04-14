package callofproject.dev.androidapp.presentation.user_profile.edit

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import callofproject.dev.androidapp.R

@Composable
fun UserAboutMeEditComponent(
    onDismissRequest: () -> Unit,
    defaultAboutMe: String = "",
    confirmEvent: (String) -> Unit
) {
    var aboutMe by remember { mutableStateOf(defaultAboutMe) }
    Dialog(onDismissRequest = onDismissRequest)
    {
        Card(modifier = Modifier.fillMaxWidth())
        {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(10.dp))
            {
                Text(text = stringResource(R.string.title_aboutMe), style = MaterialTheme.typography.headlineSmall)

                TextField(
                    value = aboutMe,
                    onValueChange = { aboutMe = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(10.dp)
                        .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp))
                )


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = { onDismissRequest() }) { Text(text = stringResource(R.string.btn_cancel)) }

                    Button(onClick = {
                        confirmEvent(aboutMe.takeIf { it.isNotBlank() } ?: defaultAboutMe)
                        onDismissRequest()
                    }) { Text(text = stringResource(R.string.btn_save)) }
                }
            }
        }
    }
}