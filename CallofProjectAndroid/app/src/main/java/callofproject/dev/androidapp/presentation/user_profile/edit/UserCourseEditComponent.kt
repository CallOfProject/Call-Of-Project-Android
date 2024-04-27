package callofproject.dev.androidapp.presentation.user_profile.edit

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.domain.dto.user_profile.course.CourseDTO
import callofproject.dev.androidapp.presentation.components.CustomDatePicker

@Composable
fun UserCourseEditComponent(
    onDismissRequest: () -> Unit,
    courseDTO: CourseDTO = CourseDTO(),
    confirmEvent: (CourseDTO) -> Unit
) {
    var isOpenStartDateDialog by remember { mutableStateOf(false) }
    var isOpenFinishDateDialog by remember { mutableStateOf(false) }
    var firm by remember { mutableStateOf(courseDTO.organization) }
    var description by remember { mutableStateOf(courseDTO.description) }
    var courseName by remember { mutableStateOf(courseDTO.courseName) }
    var startDate by remember { mutableStateOf(courseDTO.startDate) }
    var finishDate by remember { mutableStateOf(courseDTO.finishDate) }

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
                modifier = Modifier.padding(10.dp)
            ) {

                Text(
                    text = stringResource(R.string.title_upsert_course),
                    style = MaterialTheme.typography.headlineSmall
                )

                OutlinedTextField(
                    value = firm,
                    onValueChange = { firm = it },
                    textStyle = TextStyle(fontSize = 14.sp),
                    label = { Text(stringResource(R.string.title_companyName)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )

                OutlinedTextField(
                    value = courseName,
                    onValueChange = { courseName = it },
                    textStyle = TextStyle(fontSize = 14.sp),
                    label = { Text(stringResource(R.string.title_courseName)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )



                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    textStyle = TextStyle(fontSize = 14.sp),
                    label = { Text(stringResource(R.string.title_description)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(10.dp)
                )


                OutlinedButton(
                    onClick = { isOpenStartDateDialog = true },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(150.dp)
                ) {
                    Text(startDate)

                    CustomDatePicker(
                        isOpenDateDialog = isOpenStartDateDialog,
                        onDateSelected = { selectedDate -> startDate = selectedDate },
                        onDismiss = { isOpenStartDateDialog = false })
                }



                OutlinedButton(
                    onClick = { isOpenFinishDateDialog = true },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(150.dp)
                ) {
                    Text(finishDate)

                    CustomDatePicker(
                        isOpenDateDialog = isOpenFinishDateDialog,
                        onDateSelected = { selectedDate -> finishDate = selectedDate },
                        onDismiss = { isOpenFinishDateDialog = false })
                }


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = { onDismissRequest() }) { Text(text = stringResource(R.string.btn_cancel)) }

                    Button(onClick = {
                        confirmEvent(
                            courseDTO.copy(
                                courseId = courseDTO.courseId,
                                organization = firm,
                                courseName = courseName,
                                description = description,
                                startDate = startDate,
                                finishDate = finishDate,
                                isContinue = courseDTO.isContinue
                            )
                        )
                        onDismissRequest()
                    }) { Text(text = stringResource(R.string.btn_save)) }
                }
            }
        }

    }
}