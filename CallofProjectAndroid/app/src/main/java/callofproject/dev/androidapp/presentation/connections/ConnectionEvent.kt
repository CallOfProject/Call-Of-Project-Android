package callofproject.dev.androidapp.presentation.connections

sealed class ConnectionEvent {
    data object FindConnections : ConnectionEvent()
    data object FindPendingConnections : ConnectionEvent()
    data object FindBlockedConnections : ConnectionEvent()
    data class AcceptConnection(val userId: String) : ConnectionEvent()
    data class RejectConnection(val userId: String) : ConnectionEvent()
    data class BlockConnection(val userId: String) : ConnectionEvent()
    data class UnblockConnection(val userId: String) : ConnectionEvent()
    data class RemoveConnection(val userId: String) : ConnectionEvent()
}