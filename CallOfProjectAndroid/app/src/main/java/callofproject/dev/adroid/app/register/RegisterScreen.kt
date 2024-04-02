package callofproject.dev.adroid.app.register

import android.content.Context
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import callofproject.dev.adroid.app.R
import callofproject.dev.adroid.app.login.presentation.AuthenticationViewModel
import callofproject.dev.adroid.app.ui.theme.CallOfProjectAndroidTheme
import callofproject.dev.adroid.app.util.LOGIN_PAGE
import callofproject.dev.adroid.app.view.util.BoxAndColumnComponent
import callofproject.dev.adroid.app.view.util.NormalTextField
import callofproject.dev.adroid.app.view.util.PasswordTextField
import callofproject.dev.adroid.servicelib.dto.UserRegisterDTO
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePicker(
    isOpenDateDialog: Boolean,
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    if (isOpenDateDialog) {

        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val datePickerState = rememberDatePickerState()
        val confirmEnabled = remember {
            derivedStateOf { datePickerState.selectedDateMillis != null }
        }

        DatePickerDialog(onDismissRequest = onDismiss, confirmButton = {
            TextButton(onClick = {
                val selectedDate = formatter.format(
                    Instant.ofEpochMilli(datePickerState.selectedDateMillis!!)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                )
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
fun ObserveRegisterOperation(
    viewModel: RegisterViewModel,
    navController: NavController,
    context: Context
) {
    val loginResult = viewModel.state

    LaunchedEffect(loginResult) {
        loginResult.let { result ->
            if (result.isClickedBtn && result.isSuccess) {
                Toast.makeText(context, "Please check the email for verification", Toast.LENGTH_SHORT).show()
                navController.navigate(LOGIN_PAGE)
            }
            if (result.isClickedBtn && !result.isSuccess) {
                Toast.makeText(context, "Register failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    var mIsOpenDateDialog by remember { mutableStateOf(false) }
    val mConfirmPassword by remember { mutableStateOf("") }

    val context = LocalContext.current
    ObserveRegisterOperation(viewModel, navController, context)
    BoxAndColumnComponent {
        NormalTextField(
            "First Name",
            viewModel.registerDto.first_name,
            { viewModel.registerDto.first_name = it })

        NormalTextField(
            "Middle Name",
            viewModel.registerDto.middle_name!!,
            { viewModel.registerDto.middle_name = it })
        NormalTextField(
            "Last Name",
            viewModel.registerDto.last_name,
            { viewModel.registerDto.last_name = it })
        NormalTextField(
            "username",
            viewModel.registerDto.username,
            { viewModel.registerDto.username = it })

        NormalTextField(
            "Email",
            viewModel.registerDto.email,
            { viewModel.registerDto.email = it },
            keyboardType = KeyboardType.Email
        )
        PasswordTextField(
            "Password",
            viewModel.registerDto.password,
            { viewModel.registerDto.password = it })

        PasswordTextField("Confirm Password", mConfirmPassword, {

            // if (mConfirmPassword != viewModel.registerDto.password)


        })

        OutlinedButton(
            onClick = { mIsOpenDateDialog = true }, modifier = Modifier
                .width(180.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = stringResource(R.string.birth_date))

            CustomDatePicker(
                isOpenDateDialog = mIsOpenDateDialog,
                onDateSelected = { selectedDate ->
                    viewModel.registerDto.birth_date = selectedDate
                },
                onDismiss = { mIsOpenDateDialog = false })
        }
        Button(
            onClick = { viewModel.onEvent(RegisterEvent.OnClickRegisterBtn(viewModel.registerDto)) },
            modifier = Modifier
                .width(250.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF295a8c))
        ) {
            Text("Register")
        }
    }
}
