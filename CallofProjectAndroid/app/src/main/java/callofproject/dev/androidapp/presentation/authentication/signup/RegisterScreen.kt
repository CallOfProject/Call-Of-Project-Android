package callofproject.dev.androidapp.presentation.authentication.signup


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.presentation.authentication.signup.RegisterEvent.OnBirthDateChange
import callofproject.dev.androidapp.presentation.authentication.signup.RegisterEvent.OnClickRegisterBtn
import callofproject.dev.androidapp.presentation.authentication.signup.RegisterEvent.OnEmailChange
import callofproject.dev.androidapp.presentation.authentication.signup.RegisterEvent.OnFirstNameChange
import callofproject.dev.androidapp.presentation.authentication.signup.RegisterEvent.OnLastNameChange
import callofproject.dev.androidapp.presentation.authentication.signup.RegisterEvent.OnMiddleNameChange
import callofproject.dev.androidapp.presentation.authentication.signup.RegisterEvent.OnPasswordChange
import callofproject.dev.androidapp.presentation.authentication.signup.RegisterEvent.OnUsernameChange
import callofproject.dev.androidapp.presentation.components.CustomDatePicker
import callofproject.dev.androidapp.util.route.UiEvent


@Composable
fun RegisterScreen(
    scaffoldState: SnackbarHostState,
    viewModel: RegisterViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit
) {
    var mIsOpenDateDialog by remember { mutableStateOf(false) }
    var confirmPassword by remember { mutableStateOf("") }
    var isIconVisible by remember { mutableStateOf(false) }

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)

                is UiEvent.ShowSnackbar -> {
                    scaffoldState.showSnackbar(
                        message = event.msg.asString(context),
                        withDismissAction = true,
                        duration = SnackbarDuration.Short
                    )
                }

                is UiEvent.ShowToastMessage -> {
                    Toast.makeText(context, event.message.asString(context), Toast.LENGTH_SHORT)
                        .show()
                }

                else -> Unit
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            OutlinedTextField(
                value = viewModel.state.userRegisterDTO.first_name,
                onValueChange = { viewModel.onEvent(OnFirstNameChange(it)) },
                label = { Text(stringResource(R.string.text_firstName)) },
                maxLines = 1,
                modifier = Modifier
                    .width(250.dp)
                    .align(Alignment.CenterHorizontally),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            OutlinedTextField(
                value = viewModel.state.userRegisterDTO.middle_name!!,
                onValueChange = { viewModel.onEvent(OnMiddleNameChange(it)) },
                label = { Text(stringResource(R.string.text_middleName)) },
                maxLines = 1,
                modifier = Modifier
                    .width(250.dp)
                    .align(Alignment.CenterHorizontally),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            OutlinedTextField(
                value = viewModel.state.userRegisterDTO.last_name,
                onValueChange = { viewModel.onEvent(OnLastNameChange(it)) },
                label = { Text(stringResource(R.string.text_lastName)) },
                maxLines = 1,
                modifier = Modifier
                    .width(250.dp)
                    .align(Alignment.CenterHorizontally),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            OutlinedTextField(
                value = viewModel.state.userRegisterDTO.username,
                onValueChange = { viewModel.onEvent(OnUsernameChange(it)) },
                label = { Text(stringResource(R.string.text_username)) },
                maxLines = 1,
                modifier = Modifier
                    .width(250.dp)
                    .align(Alignment.CenterHorizontally),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            OutlinedTextField(
                value = viewModel.state.userRegisterDTO.email,
                onValueChange = { viewModel.onEvent(OnEmailChange(it)) },
                label = { Text(stringResource(R.string.text_email)) },
                maxLines = 1,
                modifier = Modifier
                    .width(250.dp)
                    .align(Alignment.CenterHorizontally),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            OutlinedTextField(
                trailingIcon = {
                    if (isIconVisible)
                        Icon(
                            painter = painterResource(R.drawable.warning),
                            contentDescription = stringResource(R.string.default_image_description),
                        )
                },
                value = viewModel.state.userRegisterDTO.password,
                onValueChange = { viewModel.onEvent(OnPasswordChange(it)) },
                label = { Text(stringResource(R.string.text_password)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                maxLines = 1,
                modifier = Modifier
                    .width(250.dp)
                    .align(Alignment.CenterHorizontally)
            )

            OutlinedTextField(
                trailingIcon = {
                    if (isIconVisible)
                        Icon(
                            painter = painterResource(R.drawable.warning),
                            contentDescription = stringResource(R.string.default_image_description)
                        )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                value = confirmPassword,
                onValueChange = {
                    isIconVisible = it != viewModel.state.userRegisterDTO.password
                    confirmPassword = it
                },
                label = { Text(stringResource(R.string.text_confirmPassword)) },
                maxLines = 1,
                modifier = Modifier
                    .width(250.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(10.dp))
            OutlinedButton(
                onClick = { mIsOpenDateDialog = true }, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = viewModel.state.userRegisterDTO.birth_date.ifBlank { stringResource(R.string.text_birthDate) }
                )

                CustomDatePicker(
                    isOpenDateDialog = mIsOpenDateDialog,
                    onDateSelected = { date -> viewModel.onEvent(OnBirthDateChange(date)) },
                    onDismiss = { mIsOpenDateDialog = false })
            }
            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = { viewModel.onEvent(OnClickRegisterBtn) },
                modifier = Modifier
                    .width(180.dp)
                    .align(Alignment.CenterHorizontally),
            ) { Text(stringResource(R.string.text_register)) }
        }
    }
}