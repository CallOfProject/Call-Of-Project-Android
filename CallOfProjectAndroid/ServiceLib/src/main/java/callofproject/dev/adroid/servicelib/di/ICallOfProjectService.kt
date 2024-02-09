package callofproject.dev.adroid.servicelib.di

import callofproject.dev.adroid.servicelib.dto.AuthenticationResponse
import callofproject.dev.adroid.servicelib.dto.UserLoginDTO
import callofproject.dev.adroid.servicelib.dto.UserRegisterDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ICallOfProjectService {

    @POST("api/auth/authenticate/login")
    fun login(@Body userLoginDTO: UserLoginDTO): Call<AuthenticationResponse>

    @POST("api/auth/authenticate/register")
    fun register(@Body registerDTO: UserRegisterDTO): Call<AuthenticationResponse>
}