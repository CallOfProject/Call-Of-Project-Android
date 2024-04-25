package callofproject.dev.androidapp.domain.use_cases

import android.util.Log
import callofproject.dev.androidapp.data.remote.ICallOfProjectService
import callofproject.dev.androidapp.domain.dto.MultipleResponseMessagePageable
import callofproject.dev.androidapp.domain.dto.NotificationDTO
import callofproject.dev.androidapp.domain.dto.filter.ProjectFilterDTO
import callofproject.dev.androidapp.domain.dto.project.ProjectDetailDTO
import callofproject.dev.androidapp.domain.dto.project.ProjectJoinRequestDTO
import callofproject.dev.androidapp.domain.dto.project.ProjectOverviewDTO
import callofproject.dev.androidapp.domain.dto.project.ProjectParticipantRequestDTO
import callofproject.dev.androidapp.domain.dto.project.ProjectsDetailDTO
import callofproject.dev.androidapp.domain.dto.project.ProjectsDiscoveryDTO
import callofproject.dev.androidapp.domain.dto.search.ProjectsDTO
import callofproject.dev.androidapp.domain.preferences.IPreferences
import callofproject.dev.androidapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID.fromString
import javax.inject.Inject

class ProjectUseCase @Inject constructor(
    private val service: ICallOfProjectService,
    private val preferences: IPreferences
) {

    suspend fun findProjectDetails(id: String): Resource<ProjectDetailDTO> {
        return try {

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
    }

    suspend fun findProjectDiscovery(page: Int): Flow<Resource<MultipleResponseMessagePageable<ProjectsDiscoveryDTO>>> {
        return flow {

            emit(Resource.Loading())

            try {

                val response = service.projectDiscovery(
                    page,
                    token = preferences.getToken()!!
                )

                emit(Resource.Success(response))

            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "An unexpected error occurred"))
            }
        }
    }

    suspend fun findProjectOverview(id: String): Resource<ProjectOverviewDTO> {

        return try {

            val responseMessage = service.findProjectOverviewsById(
                projectId = fromString(id),
                token = preferences.getToken()!!
            )

            Resource.Success(responseMessage.`object`!!)

        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }


    suspend fun findOwnerOfProjects(page: Int): Flow<Resource<MultipleResponseMessagePageable<ProjectsDetailDTO>>> {
        return flow {

            emit(Resource.Loading())

            try {

                val response = service.findOwnerOfProjects(
                    fromString(preferences.getUserId()!!),
                    page,
                    preferences.getToken()!!
                )

                emit(Resource.Success(response))

            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "An unexpected error occurred"))
            }
        }
    }


    suspend fun answerProjectJoinRequest(
        notificationDTO: NotificationDTO,
        accepted: Boolean
    ): Resource<Boolean> {

        return try {
            val dto = ProjectJoinRequestDTO(
                notificationDTO.requestId!!,
                notificationDTO.notificationId!!,
                accepted
            )
            val response = service.answerProjectJoinRequest(
                projectJoinRequestDTO = dto,
                token = preferences.getToken()!!
            )

            Resource.Success(response.`object`)

        } catch (e: Exception) {
            Log.e("ProjectUseCase", "answerProjectJoinRequest: ", e)
            Resource.Error(e.message ?: "An unexpected error occurred")
        }

    }


    suspend fun sendProjectJoinRequest(projectId: String): Resource<ProjectParticipantRequestDTO> {
        return try {
            val response = service.sendProjectJoinRequest(
                projectId = fromString(projectId),
                userId = fromString(preferences.getUserId()!!),
                token = preferences.getToken()!!
            )

            Resource.Success(response.`object`!!)

        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unexpected error occurred")
        }
    }


    suspend fun filterProjects(
        dto: ProjectFilterDTO,
        page: Int = 1
    ): Flow<Resource<MultipleResponseMessagePageable<ProjectsDTO>>> {
        return flow {

            emit(Resource.Loading())

            try {

                val response = service.filterProjects(
                    page = page,
                    filterDTO = dto,
                    token = preferences.getToken()!!
                )

                emit(Resource.Success(response))
                Log.d("ProjectUseCase", "filterProjects: ${response.`object`}")

            } catch (e: Exception) {
                Log.e("ProjectUseCase", "filterProjects: ", e)
                emit(Resource.Error(e.message ?: "An unexpected error occurred"))
            }
        }

    }
}