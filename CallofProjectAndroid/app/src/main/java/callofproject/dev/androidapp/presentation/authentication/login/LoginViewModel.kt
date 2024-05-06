package callofproject.dev.androidapp.presentation.authentication.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.domain.dto.AuthenticationResponse
import callofproject.dev.androidapp.domain.dto.UserLoginDTO
import callofproject.dev.androidapp.domain.preferences.IEncryptedPreferences
import callofproject.dev.androidapp.domain.preferences.IPreferences
import callofproject.dev.androidapp.domain.use_cases.UseCaseFacade
import callofproject.dev.androidapp.util.Resource
import callofproject.dev.androidapp.util.route.Route
import callofproject.dev.androidapp.util.route.Route.SIGN_UP
import callofproject.dev.androidapp.util.route.UiEvent
import callofproject.dev.androidapp.util.route.UiEvent.Navigate
import callofproject.dev.androidapp.util.route.UiEvent.ShowSnackbar
import callofproject.dev.androidapp.util.route.UiEvent.ShowToastMessage
import callofproject.dev.androidapp.util.route.UiText.DynamicString
import callofproject.dev.androidapp.util.route.UiText.StringResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val preferences: IPreferences,
    private val encryptedPref: IEncryptedPreferences,
    private val useCases: UseCaseFacade
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(LoginState())
        private set


    fun onEvent(event: LoginEvent) = when (event) {

        is LoginEvent.OnLoginButtonClick -> login()

        is LoginEvent.OnRegisterButtonClick -> onRegisterClick()

        is LoginEvent.OnUsernameChange -> state = state.copy(userLoginDTO = state.userLoginDTO.copy(username = event.username))

        is LoginEvent.OnPasswordChange -> state = state.copy(userLoginDTO = state.userLoginDTO.copy(password = event.password))
    }

    private suspend fun loginCallback(result: Resource<AuthenticationResponse>) = when (result) {
        is Resource.Error -> {
            val message = result.message ?: "An unexpected error occurred"
            state = state.copy(isLoading = false, error = message)
            _uiEvent.send(ShowSnackbar(DynamicString(message)))
        }

        is Resource.Success -> {
            state = state.copy(isLoading = false)
            _uiEvent.send(ShowToastMessage(StringResource(R.string.msg_loginSuccess)))
            preferences.saveUsername(state.userLoginDTO.username)
            preferences.saveToken(result.data!!.accessToken)
            preferences.saveUserId(result.data.user_id.toString())
            preferences.saveLoginDate(LocalDate.now())
            encryptedPref.saveUserPassword(state.userLoginDTO.password)
            _uiEvent.send(Navigate(Route.MAIN_PAGE))
        }

        is Resource.Loading -> {
            state = state.copy(isLoading = true)
        }
    }


    private fun login() {
        useCases.authentication.login(state.userLoginDTO).onEach { loginCallback(it) }
            .launchIn(viewModelScope)
    }

    private fun onRegisterClick() {
        viewModelScope.launch { _uiEvent.send(Navigate(SIGN_UP)) }
    }


    fun check() {
        val willLastLogin = preferences.getLoginDate().plusDays(30)
        val now = LocalDate.now()

        if (now.isBefore(willLastLogin) || willLastLogin.isEqual(now)) {
            val username = preferences.getUsername()
            val password = encryptedPref.getUserPassword()
            if (username != null && password != null) {
                state = state.copy(userLoginDTO = UserLoginDTO(username, password))
                login()
            }
        } else
            viewModelScope.launch {
                _uiEvent.send(ShowSnackbar(DynamicString("Please login again")))
            }
    }
}