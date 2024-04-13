package callofproject.dev.androidapp.presentation.user_profile.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import callofproject.dev.androidapp.presentation.components.CustomDatePicker
import callofproject.dev.androidapp.presentation.components.NotEditableCardComponent

@Composable
fun UserCourseEditComponent(title: String = "Edit Education") {
    val context = LocalContext.current
    var isOpenStartDateDialog by remember { mutableStateOf(false) }
    var isOpenFinishDateDialog by remember { mutableStateOf(false) }
    var firm by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var courseName by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("Start Date") }
    var finishDate by remember { mutableStateOf("Finish Date") }

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
                title = title,
                modifier = Modifier.fillMaxWidth(),
                height = 500.dp
            ) {

                OutlinedTextField(
                    value = firm,
                    onValueChange = { firm = it },
                    label = { Text("Company Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )

                OutlinedTextField(
                    value = courseName,
                    onValueChange = { courseName = it },
                    label = { Text("Course Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )

                OutlinedTextField(value = description,
                    onValueChange = { description = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(10.dp)
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    placeholder = { Text(text = "Description") })

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedButton(
                        onClick = { isOpenStartDateDialog = true }, modifier = Modifier
                            .width(150.dp)
                            .align(Alignment.CenterVertically)
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
                            .width(150.dp)
                            .align(Alignment.CenterVertically)
                    ) {
                        Text(finishDate)

                        CustomDatePicker(
                            isOpenDateDialog = isOpenFinishDateDialog,
                            onDateSelected = { selectedDate ->
                                finishDate = selectedDate
                            },
                            onDismiss = { isOpenFinishDateDialog = false })
                    }
                }

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