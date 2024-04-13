package callofproject.dev.androidapp.presentation.user_profile.edit

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.domain.dto.ExperienceDTO
import callofproject.dev.androidapp.presentation.components.CustomDatePicker

@Composable
fun UserExperienceEditComponent(
    onDismissRequest: () -> Unit,
    experienceDTO: ExperienceDTO = ExperienceDTO(),
    confirmEvent: () -> Unit
) {
    var isOpenStartDateDialog by remember { mutableStateOf(false) }
    var isOpenFinishDateDialog by remember { mutableStateOf(false) }
    var company by remember { mutableStateOf("") }
    var position by remember { mutableStateOf("") }
    var description by remember { mutableStateOf(experienceDTO.description) }
    var startDate by remember { mutableStateOf("Start Date") }
    var finishDate by remember { mutableStateOf("Finish Date") }

    Dialog(onDismissRequest = onDismissRequest) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.title_upsert_experience),
                    style = MaterialTheme.typography.headlineSmall
                )

                OutlinedTextField(
                    value = company,
                    onValueChange = { company = it },
                    label = { Text(stringResource(R.string.title_companyName)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )

                OutlinedTextField(
                    value = position,
                    onValueChange = { position = it },
                    label = { Text(stringResource(R.string.title_positionName)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(10.dp)
                        .border(
                            width = 1.dp, color = Color.Black,
                            shape = RoundedCornerShape(8.dp)
                        )
                )

                OutlinedButton(
                    onClick = { isOpenStartDateDialog = true }, modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(150.dp)
                ) {
                    Text(startDate)

                    CustomDatePicker(
                        isOpenDateDialog = isOpenStartDateDialog,
                        onDateSelected = { selectedDate ->
                            startDate = selectedDate
                        },
                        onDismiss = { isOpenStartDateDialog = false })
                }
                OutlinedButton(
                    onClick = { isOpenFinishDateDialog = true }, modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(150.dp)
                ) {
                    Text(finishDate)

                    CustomDatePicker(
                        isOpenDateDialog = isOpenFinishDateDialog,
                        onDateSelected = { selectedDate ->
                            finishDate = selectedDate
                        },
                        onDismiss = { isOpenFinishDateDialog = false })
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = { onDismissRequest() }) {
                        Text(text = stringResource(R.string.btn_cancel))
                    }

                    Button(onClick = {
                        confirmEvent(

                        )
                        onDismissRequest()
                    }) {
                        Text(text = stringResource(R.string.btn_save))
                    }
                }
            }
        }
    }

}
