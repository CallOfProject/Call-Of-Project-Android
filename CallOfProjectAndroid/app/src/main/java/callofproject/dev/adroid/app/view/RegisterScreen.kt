package callofproject.dev.adroid.app.view

import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import callofproject.dev.adroid.app.ui.theme.CallOfProjectAndroidTheme
import callofproject.dev.adroid.app.view.util.BoxAndColumnComponent
import callofproject.dev.adroid.app.view.util.NormalTextField
import callofproject.dev.adroid.app.view.util.PasswordTextField
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePicker(isOpenDateDialog : Boolean,
                     onDateSelected : (String) -> Unit,
                     onDismiss : () -> Unit)
{
    if (isOpenDateDialog)
    {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val datePickerState = rememberDatePickerState()
        val confirmEnabled = remember {
            derivedStateOf { datePickerState.selectedDateMillis != null }
        }

        DatePickerDialog(onDismissRequest = onDismiss, confirmButton = {
            TextButton(onClick = {
                val selectedDate =
                    formatter.format(Instant.ofEpochMilli(datePickerState.selectedDateMillis!!)
                        .atZone(ZoneId.systemDefault()).toLocalDate())
                onDateSelected(selectedDate)
                onDismiss()
            }, enabled = confirmEnabled.value) {
                Text("OK")
            }
        }, dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }) {
            DatePicker(state = datePickerState)
        }
    }
}

@Composable
fun RegisterScreen(navController : NavController)
{
    var mIsOpenDateDialog by remember { mutableStateOf(false) }
    var mFirstName by remember { mutableStateOf("") }
    var mMiddleName by remember { mutableStateOf("") }
    var mLastName by remember { mutableStateOf("") }
    var mUsername by remember { mutableStateOf("") }
    var mEmail by remember { mutableStateOf("") }
    var mPassword by remember { mutableStateOf("") }
    var mConfirmPassword by remember { mutableStateOf("") }
    var mBirthDate by remember { mutableStateOf("Select Birth Date") }

    BoxAndColumnComponent {

        NormalTextField("First Name", mFirstName, { mFirstName = it })
        NormalTextField("Middle Name", mMiddleName, { mMiddleName = it })
        NormalTextField("Last Name", mLastName, { mLastName = it })
        NormalTextField("username", mUsername, { mUsername = it })
        NormalTextField("Email", mEmail, { mEmail = it }, keyboardType = KeyboardType.Email)
        PasswordTextField("Password", mPassword, { mPassword = it })
        PasswordTextField("Confirm Password", mConfirmPassword, { mConfirmPassword = it })

        OutlinedButton(onClick = { mIsOpenDateDialog = true }, modifier = Modifier
            .width(180.dp)
            .align(Alignment.CenterHorizontally)) {
            Text(mBirthDate)

            CustomDatePicker(isOpenDateDialog = mIsOpenDateDialog, onDateSelected = { selectedDate ->
                mBirthDate = selectedDate
            }, onDismiss = { mIsOpenDateDialog = false })
        }
        Button(onClick = {}, modifier = Modifier
            .width(250.dp)
            .align(Alignment.CenterHorizontally), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF295a8c))) {
            Text("Register")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview()
{
    CallOfProjectAndroidTheme {
        RegisterScreen(rememberNavController())
    }
}