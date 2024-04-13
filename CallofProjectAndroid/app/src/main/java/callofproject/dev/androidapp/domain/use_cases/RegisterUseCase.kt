package callofproject.dev.androidapp.domain.use_cases

import callofproject.dev.androidapp.data.remote.ICallOfProjectService
import callofproject.dev.androidapp.domain.dto.AuthenticationResponse
import callofproject.dev.androidapp.domain.dto.UserRegisterDTO
import callofproject.dev.androidapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val service: ICallOfProjectService) {
    operator fun invoke(userRegisterDTO: UserRegisterDTO): Flow<Resource<AuthenticationResponse>> {
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