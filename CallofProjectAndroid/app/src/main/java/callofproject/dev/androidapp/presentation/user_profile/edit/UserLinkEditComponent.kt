package callofproject.dev.androidapp.presentation.user_profile.edit

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.domain.dto.user_profile.link.LinkDTO

@Composable
fun UserLinkEditComponent(
    onDismissRequest: () -> Unit,
    linkDTO: LinkDTO = LinkDTO(),
    confirmEvent: (LinkDTO) -> Unit
) {

    var linkName by remember { mutableStateOf(linkDTO.linkTitle) }
    var link by remember { mutableStateOf(linkDTO.link) }

    Dialog(onDismissRequest = onDismissRequest)
    {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = MaterialTheme.colorScheme.primary),
            colors = CardColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.primary,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = Color.Transparent
            ),
        )
        {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {

                Text(
                    text = stringResource(R.string.title_upsert_link),
                    style = MaterialTheme.typography.headlineSmall
                )

                OutlinedTextField(
                    value = linkName,
                    onValueChange = { linkName = it },
                    label = { Text(stringResource(R.string.title_profileLinkName)) },
                    textStyle = TextStyle(fontSize = 14.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )

                OutlinedTextField(
                    value = link,
                    onValueChange = { link = it },
                    label = { Text(stringResource(R.string.title_profileLink)) },
                    textStyle = TextStyle(fontSize = 14.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                )


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Button(onClick = { onDismissRequest() }) { Text(text = stringResource(R.string.btn_cancel)) }

                    Button(onClick = {
                        confirmEvent(linkDTO.copy(linkTitle = linkName, link = link))
                        onDismissRequest()
                    }) { Text(text = stringResource(R.string.btn_save)) }
                }

            }
        }
    }
}