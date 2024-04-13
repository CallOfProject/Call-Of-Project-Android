package callofproject.dev.androidapp.presentation.user_profile.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import callofproject.dev.androidapp.presentation.components.NotEditableCardComponent

@Composable
fun UserLinkEditComponent() {
    val context = LocalContext.current
    var linkName by remember { mutableStateOf("") }
    var link by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary), contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            NotEditableCardComponent(
                title = "Edit Link",
                modifier = Modifier.fillMaxWidth(),
                height = 500.dp
            ) {


                OutlinedTextField(
                    value = linkName,
                    onValueChange = { linkName = it },
                    label = { Text("Link Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )

                OutlinedTextField(
                    value = link,
                    onValueChange = { link = it },
                    label = { Text("Link") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )


                Button(
                    onClick = { }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Save")
                }

                Button(
                    onClick = { }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Close")
                }
            }
        }
    }

}
