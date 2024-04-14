package callofproject.dev.androidapp.presentation.user_profile.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.domain.dto.user_profile.experience.ExperienceDTO
import callofproject.dev.androidapp.domain.dto.user_profile.link.LinkDTO
import callofproject.dev.androidapp.presentation.components.NotEditableCardComponent

@Composable
fun UserLinkEditComponent(
    onDismissRequest: () -> Unit,
    linkDTO: LinkDTO = LinkDTO(),
    confirmEvent: (LinkDTO) -> Unit
) {

    var linkName by remember { mutableStateOf(linkDTO.linkTitle) }
    var link by remember { mutableStateOf(linkDTO.link) }

    Dialog(onDismissRequest = onDismissRequest) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = stringResource(R.string.title_upsert_link),
                    style = MaterialTheme.typography.headlineSmall
                )

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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = { onDismissRequest() }) {
                        Text(text = stringResource(R.string.btn_cancel))
                    }

                    Button(onClick = {
                        confirmEvent(linkDTO.copy(linkTitle = linkName, link = link))
                        onDismissRequest()
                    }) {
                        Text(text = stringResource(R.string.btn_save))
                    }
                }

            }
        }
    }
}