package callofproject.dev.android.authentication.register


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import callofproject.dev.adroid.app.authentication.register.RegisterEvent
import callofproject.dev.adroid.app.authentication.register.RegisterState
import callofproject.dev.adroid.servicelib.di.ICallOfProjectService
import callofproject.dev.adroid.servicelib.dto.ApiResponse
import callofproject.dev.adroid.servicelib.dto.UserRegisterDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val callOfProjectService: ICallOfProjectService
) : ViewModel() {
    var state by mutableStateOf(RegisterState())
        private set
    var registerDto by mutableStateOf(UserRegisterDTO())
        private set

    fun onEvent(event: RegisterEvent) = when (event) {
        is RegisterEvent.OnClickRegisterBtn -> register(event.userRegisterDTO)

        is RegisterEvent.OnRegisterDtoChange -> {
            state.userRegisterDto.first_name = event.userRegisterDTO.first_name
            state.userRegisterDto.middle_name = event.userRegisterDTO.middle_name
            state.userRegisterDto.last_name = event.userRegisterDTO.last_name
        }

        else -> {}
    }


    private fun register(registerDTO: UserRegisterDTO) {
        viewModelScope.launch {
            executeApiCall(callOfProjectService.register(registerDTO)) { response ->
                state = when (response) {
                    is ApiResponse.Success -> {
                        state.copy(isSuccess = true, isClickedBtn = true)
                    }

                    is ApiResponse.Error -> {
                        state.copy(isClickedBtn = true)
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