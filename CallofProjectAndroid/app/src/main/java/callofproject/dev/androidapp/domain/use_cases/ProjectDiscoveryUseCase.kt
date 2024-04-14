package callofproject.dev.androidapp.domain.use_cases

import callofproject.dev.androidapp.data.remote.ICallOfProjectService
import callofproject.dev.androidapp.domain.dto.MultipleResponseMessagePageable
import callofproject.dev.androidapp.domain.dto.project.ProjectsDiscoveryDTO
import callofproject.dev.androidapp.domain.preferences.IPreferences
import callofproject.dev.androidapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProjectDiscoveryUseCase @Inject constructor(
    private val service: ICallOfProjectService,
    private val preferences: IPreferences
) {

    suspend operator fun invoke(page: Int): Flow<Resource<MultipleResponseMessagePageable<ProjectsDiscoveryDTO>>> {
        val token = preferences.getToken()!!
        return flow {
            emit(Resource.Loading())
            try {
                val response = service.projectDiscovery(
                    page,
                    token = token
                )
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "An unexpected error occurred"))
            }
        }
    }
}