package callofproject.dev.androidapp.domain.use_cases

import android.content.Context
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.data.remote.ICallOfProjectService
import callofproject.dev.androidapp.domain.dto.AuthenticationResponse
import callofproject.dev.androidapp.domain.dto.UserLoginDTO
import callofproject.dev.androidapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class LoginUseCase @Inject constructor(
    private val service: ICallOfProjectService,
    private val context: Context
) {

    operator fun invoke(userLoginDTO: UserLoginDTO): Flow<Resource<AuthenticationResponse>> {
        return flow {
            try {
                emit(Resource.Loading())
                val response = service.login(userLoginDTO)
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(context.getString(R.string.msg_loginInvalidUsernameOrPassword)))
            }
        }
    }
}