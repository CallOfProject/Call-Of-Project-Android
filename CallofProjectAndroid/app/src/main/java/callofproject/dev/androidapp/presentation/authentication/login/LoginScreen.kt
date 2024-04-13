package callofproject.dev.androidapp.presentation.authentication.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.presentation.authentication.login.LoginEvent.OnLoginButtonClick
import callofproject.dev.androidapp.presentation.authentication.login.LoginEvent.OnRegisterButtonClick
import callofproject.dev.androidapp.util.route.UiEvent

@Composable
fun LoginScreen(
    scaffoldState: SnackbarHostState,
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit
) {

    val state = viewModel.state
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)

                is UiEvent.ShowSnackbar -> {
                    scaffoldState.showSnackbar(message = event.msg.asString(context))
                }

                else -> Unit
            }
        }
    }

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Image(
                painter = painterResource(R.drawable.cop_logo),
                contentDescription = stringResource(R.string.default_image_description),
                modifier = Modifier
                    .size(250.dp)
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.FillWidth
            )


            state.userLoginDTO.username
            OutlinedTextField(
                value = "cop_admin",
                maxLines = 1,
                onValueChange = { viewModel.onEvent(LoginEvent.OnUsernameChange("cop_admin")) },
                label = { Text(text = stringResource(R.string.text_username)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )


            //state.userLoginDTO.password
            OutlinedTextField(
                value = "cop123",
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                onValueChange = { viewModel.onEvent(LoginEvent.OnPasswordChange("cop123")) },
                maxLines = 1,
                label = { Text(text = stringResource(R.string.text_password)) },
            )

            Button(
                onClick = { viewModel.onEvent(OnLoginButtonClick) },
                modifier = Modifier
                    .width(180.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(text = stringResource(R.string.text_login))
            }

            Button(
                onClick = { viewModel.onEvent(OnRegisterButtonClick) },
                modifier = Modifier
                    .width(180.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(text = stringResource(R.string.text_register))
            }

        }
    }
}
