package callofproject.dev.androidapp.presentation.authentication.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.domain.preferences.IPreferences
import callofproject.dev.androidapp.domain.use_cases.UseCaseFacade
import callofproject.dev.androidapp.util.Resource
import callofproject.dev.androidapp.util.route.Route
import callofproject.dev.androidapp.util.route.Route.SIGN_UP
import callofproject.dev.androidapp.util.route.UiEvent
import callofproject.dev.androidapp.util.route.UiEvent.Navigate
import callofproject.dev.androidapp.util.route.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val preferences: IPreferences,
    private val useCases: UseCaseFacade
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(LoginState())
        private set


    fun onEvent(event: LoginEvent) = when (event) {
        is LoginEvent.OnLoginButtonClick -> login()

        is LoginEvent.OnRegisterButtonClick -> onRegisterClick()

        is LoginEvent.OnUsernameChange -> state =
            state.copy(userLoginDTO = state.userLoginDTO.copy(username = event.username))

        is LoginEvent.OnPasswordChange -> state =
            state.copy(userLoginDTO = state.userLoginDTO.copy(password = event.password))
    }


    private fun onRegisterClick() {
        viewModelScope.launch { _uiEvent.send(Navigate(SIGN_UP)) }
    }


    private fun login() {
        useCases.login(state.userLoginDTO).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        error = result.message ?: "An unexpected error occurred"
                    )
                    _uiEvent.send(
                        UiEvent.ShowSnackbar(
                            UiText.DynamicString(
                                result.message ?: "An unexpected error occurred"
                            )
                        )
                    )
                }

                is Resource.Success -> {
                    state = state.copy(isLoading = false)
                    _uiEvent.send(UiEvent.ShowToastMessage(UiText.StringResource(R.string.msg_loginSuccess)))
                    preferences.saveUsername(state.userLoginDTO.username)
                    preferences.saveToken(result.data!!.accessToken)
                    preferences.saveUserId(result.data.user_id.toString())
                    _uiEvent.send(Navigate(Route.MAIN_PAGE))
                }

                is Resource.Loading -> {
                    state = state.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}