package callofproject.dev.androidapp.domain.use_cases

import android.content.Context
import android.util.Log
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.data.remote.ICallOfProjectService
import callofproject.dev.androidapp.domain.dto.AuthenticationResponse
import callofproject.dev.androidapp.domain.dto.UserLoginDTO
import callofproject.dev.androidapp.domain.dto.UserRegisterDTO
import callofproject.dev.androidapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class AuthenticationUseCase @Inject constructor(
    private val service: ICallOfProjectService,
    private val context: Context
) {

    fun login(userLoginDTO: UserLoginDTO): Flow<Resource<AuthenticationResponse>> {
        return flow {
            try {
                emit(Resource.Loading())

                val response = service.login(userLoginDTO)

                if (response.isSuccess)
                    emit(Resource.Success(response))

                else if (response.isBlocked)
                    emit(Resource.Error(context.getString(R.string.msg_blocked_user)));

            } catch (e: Exception) {
                emit(Resource.Error(context.getString(R.string.msg_loginInvalidUsernameOrPassword)))
            }
        }
    }

    fun register(userRegisterDTO: UserRegisterDTO): Flow<Resource<AuthenticationResponse>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = service.register(userRegisterDTO)
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "An unexpected error occurred"))
            }
        }
    }
}