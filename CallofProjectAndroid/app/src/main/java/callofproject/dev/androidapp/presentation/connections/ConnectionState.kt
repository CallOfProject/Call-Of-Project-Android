package callofproject.dev.androidapp.presentation.connections

import callofproject.dev.androidapp.domain.dto.connection.UserConnectionDTO

data class ConnectionState(
    val connections: List<UserConnectionDTO> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)