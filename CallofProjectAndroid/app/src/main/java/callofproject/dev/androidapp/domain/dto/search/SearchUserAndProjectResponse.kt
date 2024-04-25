package callofproject.dev.androidapp.domain.dto.search

data class SearchUserAndProjectResponse(
    val projects: ProjectsDTO = ProjectsDTO(),
    val users: UsersDTO = UsersDTO()
)
