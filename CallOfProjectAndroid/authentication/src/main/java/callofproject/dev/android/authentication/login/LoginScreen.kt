package callofproject.dev.android.authentication.login


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import callofproject.dev.adroid.app.authentication.login.presentation.LoginEvent.OnLoginButtonClick
import callofproject.dev.android.authentication.components.NormalTextField
import callofproject.dev.android.authentication.components.PasswordTextField
import callofproject.dev.core.R
import callofproject.dev.core.util.UiEvent


@Composable
fun LoginScreen(
    scaffoldState: SnackbarHostState,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: AuthenticationViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.showSnackbar(message = event.message.asString(context))
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
                painter = painterResource(id = R.drawable.cop_logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(250.dp)
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.FillWidth
            )

            NormalTextField(
                text = stringResource(R.string.username_text_field),
                value = viewModel.username,
                onValueChange = viewModel::onUsernameEnter
            )


            PasswordTextField(
                text = stringResource(R.string.password_text_field),
                value = viewModel.password,
                onValueChange = viewModel::onPasswordEnter
            )

            Button(
                onClick = {
                    viewModel.onEvent(
                        OnLoginButtonClick(
                            viewModel.username,
                            viewModel.password
                        )
                    )
                },
                modifier = Modifier
                    .width(250.dp)
                    .align(Alignment.CenterHorizontally)
            )
            {
                Text(stringResource(R.string.login_btn))
            }

            Button(
                onClick = { viewModel.onRegisterClick() },
                modifier = Modifier
                    .width(250.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(stringResource(R.string.register_btn))
            }
        }
    }
}
