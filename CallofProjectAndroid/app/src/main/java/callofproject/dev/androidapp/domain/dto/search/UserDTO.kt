package callofproject.dev.androidapp.domain.dto.search

import java.util.UUID

data class UserDTO(
    val userId: UUID,
    val username: String,
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val image: String
)
