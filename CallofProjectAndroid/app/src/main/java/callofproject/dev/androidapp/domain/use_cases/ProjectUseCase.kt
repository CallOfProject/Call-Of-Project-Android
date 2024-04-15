package callofproject.dev.androidapp.domain.use_cases

import callofproject.dev.androidapp.data.remote.ICallOfProjectService
import callofproject.dev.androidapp.domain.dto.MultipleResponseMessagePageable
import callofproject.dev.androidapp.domain.dto.project.ProjectDetailDTO
import callofproject.dev.androidapp.domain.dto.project.ProjectOverviewDTO
import callofproject.dev.androidapp.domain.dto.project.ProjectsDetailDTO
import callofproject.dev.androidapp.domain.dto.project.ProjectsDiscoveryDTO
import callofproject.dev.androidapp.domain.preferences.IPreferences
import callofproject.dev.androidapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID
import java.util.UUID.fromString
import javax.inject.Inject

class ProjectUseCase @Inject constructor(
    private val service: ICallOfProjectService,
    private val preferences: IPreferences
) {

    suspend fun findProjectDetails(id: String): Resource<ProjectDetailDTO> {
        var result: Resource<ProjectDetailDTO> = Resource.Loading()
        result = try {

            val token = preferences.getToken()!!
            val userId = preferences.getUserId()!!
            val username = preferences.getUsername()!!

            val responseMessage = service.findProjectDetailsById(
                projectId = fromString(id),
                userId = fromString(userId),
                token = token
            )

            val detailsDTO = responseMessage.`object`
            val isParticipant = detailsDTO.projectParticipants.any { it.user_id == userId }

            if (detailsDTO.projectOwnerName == username || isParticipant)
                return Resource.Success(responseMessage.`object`)

            return Resource.Error("You are not the owner or participant of this project")

        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
        return result
    }

    suspend fun findProjectDiscovery(page: Int): Flow<Resource<MultipleResponseMessagePageable<ProjectsDiscoveryDTO>>> {
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

    suspend fun findProjectOverview(id: String): Resource<ProjectOverviewDTO> {
        val token = preferences.getToken()!!
        return try {
            val responseMessage = service.findProjectOverviewsById(
                projectId = fromString(id),
                token = token
            )

            Resource.Success(responseMessage.`object`!!)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }


    suspend fun findOwnerOfProjects(page: Int): Flow<Resource<MultipleResponseMessagePageable<ProjectsDetailDTO>>> {
        val token = preferences.getToken()!!
        val userId = preferences.getUserId()!!
        return flow {
            emit(Resource.Loading())
            try {
                val response = service.findOwnerOfProjects(fromString(userId), page, token)
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "An unexpected error occurred"))
            }
        }
    }
}