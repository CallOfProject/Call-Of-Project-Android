package callofproject.dev.adroid.app.viewmodel


import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import callofproject.dev.adroid.servicelib.di.ICallOfProjectService
import callofproject.dev.adroid.servicelib.dto.ApiResponse
import callofproject.dev.adroid.servicelib.dto.AuthenticationResponse
import callofproject.dev.adroid.servicelib.dto.UserLoginDTO
import callofproject.dev.adroid.servicelib.dto.UserRegisterDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val executor: ExecutorService,
    private val callOfProjectService: ICallOfProjectService,
) : ViewModel() {

    private val _loginResult = MutableStateFlow<Boolean?>(null)
    val loginResult: StateFlow<Boolean?> = _loginResult

    private val _registerResult = MutableStateFlow<Boolean?>(null)
    val registerResult: StateFlow<Boolean?> = _registerResult

    fun login(userLoginDTO: UserLoginDTO) {
        executor.execute {
            executeApiCall(callOfProjectService.login(userLoginDTO)) { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        Log.v("RegisterViewModel", response.data.toString())
                        _loginResult.value = true
                    }

                    is ApiResponse.Error -> {
                        Log.v("RegisterViewModel", "Register FAIL!")
                        _loginResult.value = false
                    }
                }
            }
        }
    }

    fun register(registerDTO: UserRegisterDTO) {
        executor.execute {
            executeApiCall(callOfProjectService.register(registerDTO)) { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        _registerResult.value = true
                    }

                    is ApiResponse.Error -> {
                        _registerResult.value = false
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


    /*private fun registerCallback(registerDTO: UserRegisterDTO) {
        val call = callOfProjectService.register(registerDTO)

        call.enqueue(object : Callback<AuthenticationResponse> {
            override fun onResponse(
                call: Call<AuthenticationResponse>,
                response: Response<AuthenticationResponse>
            ) {
                val authResponse = response.body()

                if (authResponse != null) {
                    Log.v("RegisterViewModel", "Register success!")
                    _registerResult.value = true

                } else {
                    Log.e("RegisterViewModel", "Register failed!")
                    _registerResult.value = false
                }
            }

            override fun onFailure(call: Call<AuthenticationResponse>, t: Throwable) {
                Log.e("RegisterViewModel", "Exception Occured!. Message: " + t.message)
                _registerResult.value = false
            }

        })
    }

     fun loginCallback(userLoginDTO: UserLoginDTO) {
        val call = callOfProjectService.login(userLoginDTO)

        call.enqueue(object : Callback<AuthenticationResponse> {
            override fun onResponse(
                call: Call<AuthenticationResponse>,
                response: Response<AuthenticationResponse>
            ) {
                val authResponse = response.body()

                if (authResponse != null) {
                    Log.v("LoginViewModel", "Login success!")
                    _loginResult.value = true

                } else {
                    Log.e("LoginViewModel", "Login failed!")
                    _loginResult.value = false
                }
            }

            override fun onFailure(call: Call<AuthenticationResponse>, t: Throwable) {
                Log.e("LoginViewModel", "Exception Occured!. Message: " + t.message)
                _loginResult.value = false
            }
        })
    }*/
}