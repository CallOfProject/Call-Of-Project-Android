package callofproject.dev.androidapp.domain.use_cases

import callofproject.dev.androidapp.data.remote.ICallOfProjectService
import callofproject.dev.androidapp.domain.dto.ProjectDetailDTO

import callofproject.dev.androidapp.domain.preferences.IPreferences
import callofproject.dev.androidapp.util.Resource

import java.util.UUID
import javax.inject.Inject

class ProjectDetailsUseCase @Inject constructor(
    private val service: ICallOfProjectService,
    private val preferences: IPreferences
) {
    suspend operator fun invoke(id: String): Resource<ProjectDetailDTO> {
        var result: Resource<ProjectDetailDTO> = Resource.Loading()
        result = try {

            val token = preferences.getToken()!!
            val userId = preferences.getUserId()!!
            val username = preferences.getUsername()!!

            val responseMessage = service.findProjectDetailsById(
                projectId = UUID.fromString(id),
                userId = UUID.fromString(userId),
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
}