package callofproject.dev.androidapp.presentation.authentication.signup


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.domain.use_cases.RegisterUseCase
import callofproject.dev.androidapp.util.Resource
import callofproject.dev.androidapp.util.route.Route.LOGIN
import callofproject.dev.androidapp.util.route.UiEvent
import callofproject.dev.androidapp.util.route.UiEvent.ShowSnackbar
import callofproject.dev.androidapp.util.route.UiText
import callofproject.dev.androidapp.util.route.UiText.StringResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    var state by mutableStateOf(RegisterState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: RegisterEvent) = when (event) {
        is RegisterEvent.OnClickRegisterBtn -> register()

        is RegisterEvent.OnBirthDateChange -> state =
            state.copy(userRegisterDTO = state.userRegisterDTO.copy(birth_date = event.birthDate))

        is RegisterEvent.OnFirstNameChange -> state =
            state.copy(userRegisterDTO = state.userRegisterDTO.copy(first_name = event.firstName))

        is RegisterEvent.OnLastNameChange -> state =
            state.copy(userRegisterDTO = state.userRegisterDTO.copy(last_name = event.lastName))

        is RegisterEvent.OnMiddleNameChange -> state =
            state.copy(userRegisterDTO = state.userRegisterDTO.copy(middle_name = event.middleName))

        is RegisterEvent.OnPasswordChange -> state =
            state.copy(userRegisterDTO = state.userRegisterDTO.copy(password = event.password))

        is RegisterEvent.OnUsernameChange -> state =
            state.copy(userRegisterDTO = state.userRegisterDTO.copy(username = event.username))

        is RegisterEvent.OnEmailChange -> state =
            state.copy(userRegisterDTO = state.userRegisterDTO.copy(email = event.email))
    }


    private fun register() {
        viewModelScope.launch {
            registerUseCase(state.userRegisterDTO).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _uiEvent.send(ShowSnackbar(StringResource(R.string.text_successRegister)))
                        _uiEvent.send(UiEvent.Navigate(LOGIN))
                    }

                    is Resource.Error -> {
                        _uiEvent.send(ShowSnackbar(UiText.DynamicString(resource.message!!)))
                    }

                    is Resource.Loading -> {
                        //_uiEvent.send(ShowSnackbar(StringResource(R.string.text_loadingRegister)))
                    }
                }
            }
        }
    }
}