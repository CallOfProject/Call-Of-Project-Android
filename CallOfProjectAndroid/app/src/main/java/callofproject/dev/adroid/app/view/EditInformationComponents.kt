package callofproject.dev.adroid.app.view

import android.content.Context
import android.widget.Toast
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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import callofproject.dev.adroid.app.ui.theme.CallOfProjectAndroidTheme
import callofproject.dev.adroid.app.view.util.NormalTextField
import callofproject.dev.adroid.app.view.util.NotEditableCardComponent

@Composable
fun UserEducationEditComponent(navController : NavController)
{
    val context = LocalContext.current
    var isOpenStartDateDialog by remember { mutableStateOf(false) }
    var isOpenFinishDateDialog by remember { mutableStateOf(false) }
    var school by remember { mutableStateOf("") }
    var department by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("Start Date") }
    var finishDate by remember { mutableStateOf("Finish Date") }
    var gpa by remember { mutableDoubleStateOf(0.0) }
    var year by remember { mutableStateOf(1) }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.secondary), contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            NotEditableCardComponent(title = "Edit Education", modifier = Modifier.fillMaxWidth(), height = 500.dp) {

                NormalTextField(text = "School Name", value = school, modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp), onValueChange = { school = it })
                NormalTextField(text = "Department", value = department, modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp), onValueChange = { department = it })
                Row {
                    NormalTextField(text = "GPA", value = gpa.toString(), modifier = Modifier
                        .width(180.dp)
                        .padding(10.dp), onValueChange = { gpa = it.toDouble() }, keyboardType = KeyboardType.Decimal)

                    NormalTextField(text = "Year", value = year.toString(), modifier = Modifier
                        .width(180.dp)
                        .padding(10.dp), onValueChange = { year = it.toInt() }, keyboardType = KeyboardType.Number)
                }
                Row {
                    OutlinedButton(onClick = { isOpenStartDateDialog = true }, modifier = Modifier
                        .width(180.dp)
                        .align(Alignment.CenterVertically)) {
                        Text(startDate)

                        CustomDatePicker(isOpenDateDialog = isOpenStartDateDialog, onDateSelected = { selectedDate ->
                            startDate = selectedDate
                        }, onDismiss = { isOpenStartDateDialog = false })
                    }
                    OutlinedButton(onClick = { isOpenFinishDateDialog = true }, modifier = Modifier
                        .width(180.dp)
                        .align(Alignment.CenterVertically)) {
                        Text(finishDate)

                        CustomDatePicker(isOpenDateDialog = isOpenFinishDateDialog, onDateSelected = { selectedDate ->
                            finishDate = selectedDate
                        }, onDismiss = { isOpenFinishDateDialog = false })
                    }
                }

                Button(onClick = { }, modifier = Modifier
                    .width(200.dp)
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally)) {
                    Text(text = "Save")
                }

                Button(onClick = { onClickClose(context, navController) }, modifier = Modifier
                    .width(200.dp)
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally)) {
                    Text(text = "Close")
                }
            }
        }
    }

}

@Composable
fun UserAboutMeEditComponent(navController : NavController)
{
    val context = LocalContext.current
    var aboutMe by remember { mutableStateOf("") }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.secondary), contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            NotEditableCardComponent(title = "Edit About me", modifier = Modifier.fillMaxWidth(), height = 500.dp) {
                TextField(value = aboutMe, onValueChange = { aboutMe = it }, modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(10.dp)
                    .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp)))

                Button(onClick = { }, modifier = Modifier
                    .width(200.dp)
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally)) {
                    Text(text = "Save")
                }

                Button(onClick = { onClickClose(context, navController) }, modifier = Modifier
                    .width(200.dp)
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally)) {
                    Text(text = "Close")
                }
            }
        }
    }
}


@Composable
fun UserExperienceEditComponent(navController : NavController)
{
    val context = LocalContext.current
    var isOpenStartDateDialog by remember { mutableStateOf(false) }
    var isOpenFinishDateDialog by remember { mutableStateOf(false) }
    var company by remember { mutableStateOf("") }
    var position by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("Start Date") }
    var finishDate by remember { mutableStateOf("Finish Date") }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.secondary), contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            NotEditableCardComponent(title = "Edit Education", modifier = Modifier.fillMaxWidth(), height = 500.dp) {

                NormalTextField(text = "School Name", value = company, modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp), onValueChange = { company = it })
                NormalTextField(text = "Department", value = position, modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp), onValueChange = { position = it })

                Row {
                    OutlinedButton(onClick = { isOpenStartDateDialog = true }, modifier = Modifier
                        .width(180.dp)
                        .align(Alignment.CenterVertically)) {
                        Text(startDate)

                        CustomDatePicker(isOpenDateDialog = isOpenStartDateDialog, onDateSelected = { selectedDate ->
                            startDate = selectedDate
                        }, onDismiss = { isOpenStartDateDialog = false })
                    }
                    OutlinedButton(onClick = { isOpenFinishDateDialog = true }, modifier = Modifier
                        .width(180.dp)
                        .align(Alignment.CenterVertically)) {
                        Text(finishDate)

                        CustomDatePicker(isOpenDateDialog = isOpenFinishDateDialog, onDateSelected = { selectedDate ->
                            finishDate = selectedDate
                        }, onDismiss = { isOpenFinishDateDialog = false })
                    }
                }

                Button(onClick = { }, modifier = Modifier
                    .width(200.dp)
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally)) {
                    Text(text = "Save")
                }

                Button(onClick = { onClickClose(context, navController) }, modifier = Modifier
                    .width(200.dp)
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally)) {
                    Text(text = "Close")
                }
            }
        }
    }

}


@Composable
fun UserCourseEditComponent(navController : NavController)
{
    val context = LocalContext.current
    var isOpenStartDateDialog by remember { mutableStateOf(false) }
    var isOpenFinishDateDialog by remember { mutableStateOf(false) }
    var firm by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var courseName by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("Start Date") }
    var finishDate by remember { mutableStateOf("Finish Date") }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.secondary), contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            NotEditableCardComponent(title = "Edit Education", modifier = Modifier.fillMaxWidth(), height = 500.dp) {

                NormalTextField(text = "Firm Name", value = firm, modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp), onValueChange = { firm = it })
                NormalTextField(text = "Course Name", value = courseName, modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp), onValueChange = { courseName = it })

                OutlinedTextField(value = description, onValueChange = { description = it }, modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(10.dp)
                    .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp)), placeholder = { Text(text = "Description") })

                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(onClick = { isOpenStartDateDialog = true }, modifier = Modifier
                        .width(150.dp)
                        .align(Alignment.CenterVertically)) {
                        Text(startDate)

                        CustomDatePicker(isOpenDateDialog = isOpenStartDateDialog, onDateSelected = { selectedDate ->
                            startDate = selectedDate
                        }, onDismiss = { isOpenStartDateDialog = false })
                    }

                    OutlinedButton(onClick = { isOpenFinishDateDialog = true }, modifier = Modifier
                        .width(150.dp)
                        .align(Alignment.CenterVertically)) {
                        Text(finishDate)

                        CustomDatePicker(isOpenDateDialog = isOpenFinishDateDialog, onDateSelected = { selectedDate ->
                            finishDate = selectedDate
                        }, onDismiss = { isOpenFinishDateDialog = false })
                    }
                }

                Button(onClick = { }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally)) {
                    Text(text = "Save")
                }

                Button(onClick = { onClickClose(context, navController) }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally)) {
                    Text(text = "Close")
                }
            }
        }
    }

}


@Composable
fun UserProjectEditComponent(navController : NavController)
{
    val context = LocalContext.current
    var isOpenStartDateDialog by remember { mutableStateOf(false) }
    var isOpenFinishDateDialog by remember { mutableStateOf(false) }
    var projectName by remember { mutableStateOf("") }
    var summary by remember { mutableStateOf("") }
    var link by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("Start Date") }
    var finishDate by remember { mutableStateOf("Finish Date") }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.secondary), contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            NotEditableCardComponent(title = "Edit Project", modifier = Modifier.fillMaxWidth(), height = 500.dp) {

                NormalTextField(text = "Project Name", value = projectName, modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp), onValueChange = { projectName = it })

                OutlinedTextField(value = summary, onValueChange = { summary = it }, modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(10.dp)
                    .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp)), placeholder = { Text(text = "Summary") })

                NormalTextField(text = "Project Link", value = link, modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp), onValueChange = { link = it })


                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(onClick = { isOpenStartDateDialog = true }, modifier = Modifier
                        .width(150.dp)
                        .align(Alignment.CenterVertically)) {
                        Text(startDate)

                        CustomDatePicker(isOpenDateDialog = isOpenStartDateDialog, onDateSelected = { selectedDate ->
                            startDate = selectedDate
                        }, onDismiss = { isOpenStartDateDialog = false })
                    }

                    OutlinedButton(onClick = { isOpenFinishDateDialog = true }, modifier = Modifier
                        .width(150.dp)
                        .align(Alignment.CenterVertically)) {
                        Text(finishDate)

                        CustomDatePicker(isOpenDateDialog = isOpenFinishDateDialog, onDateSelected = { selectedDate ->
                            finishDate = selectedDate
                        }, onDismiss = { isOpenFinishDateDialog = false })
                    }
                }

                Button(onClick = { }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally)) {
                    Text(text = "Save")
                }

                Button(onClick = { onClickClose(context, navController) }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally)) {
                    Text(text = "Close")
                }
            }
        }
    }

}


@Composable
fun UserLinkEditComponent(navController : NavController)
{
    val context = LocalContext.current
    var linkName by remember { mutableStateOf("") }
    var link by remember { mutableStateOf("") }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.secondary), contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            NotEditableCardComponent(title = "Edit Link", modifier = Modifier.fillMaxWidth(), height = 500.dp) {

                NormalTextField(text = "Link Title", value = linkName, modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp), onValueChange = { linkName = it })



                NormalTextField(text = "Link", value = link, modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp), onValueChange = { link = it })



                Button(onClick = { }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally)) {
                    Text(text = "Save")
                }

                Button(onClick = { onClickClose(context, navController) }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally)) {
                    Text(text = "Close")
                }
            }
        }
    }

}


private fun onClickClose(context : Context, navController : NavController)
{
    navController.popBackStack()
    Toast.makeText(context, "Changes Not Saved!", Toast.LENGTH_SHORT).show()
}

@Preview(showBackground = true)
@Composable
fun EditOverview()
{
    CallOfProjectAndroidTheme {
        UserLinkEditComponent(rememberNavController())
    }
}
