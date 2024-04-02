package callofproject.dev.adroid.app.login.presentation


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import callofproject.dev.adroid.servicelib.di.ICallOfProjectService
import callofproject.dev.adroid.servicelib.dto.ApiResponse
import callofproject.dev.adroid.servicelib.dto.UserLoginDTO
import callofproject.dev.adroid.servicelib.dto.UserRegisterDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val callOfProjectService: ICallOfProjectService,
) : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    fun onEvent(event: LoginEvent) = when (event) {
        is LoginEvent.OnLoginButtonClick -> login(event.loginDTO)

        is LoginEvent.OnRegisterButtonClick -> {
            //....
        }
    }

    fun login(userLoginDTO: UserLoginDTO) {
        viewModelScope.launch {
            executeApiCall(callOfProjectService.login(userLoginDTO)) { response ->
                state = when (response) {
                    is ApiResponse.Success -> {
                        Log.v("RegisterViewModel", response.data.toString())
                        state.copy(
                            isClickedLogin = true,
                            isSuccess = true,
                            authResponse = response.data
                        )
                    }

                    is ApiResponse.Error -> {
                        Log.v("RegisterViewModel", "Register FAIL!")
                        state.copy(isSuccess = false, isClickedLogin = true)
                    }
                }
            }
        }
    }


    private fun <T> executeApiCall(call: Call<T>, callback: (ApiResponse<T>) -> Unit) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        callback(ApiResponse.Success(it))
                    } ?: callback(ApiResponse.Error("Empty Response"))
                } else {
                    callback(ApiResponse.Error(response.message()))
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                callback(ApiResponse.Error(t.message ?: "Unknown Error"))
            }
        })
    }
}