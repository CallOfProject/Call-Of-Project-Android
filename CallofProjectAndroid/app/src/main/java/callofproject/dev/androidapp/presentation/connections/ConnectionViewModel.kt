package callofproject.dev.androidapp.presentation.connections

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import callofproject.dev.androidapp.domain.dto.connection.UserConnectionDTO
import callofproject.dev.androidapp.domain.use_cases.UseCaseFacade
import callofproject.dev.androidapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConnectionViewModel @Inject constructor(
    private val useCaseFacade: UseCaseFacade
) : ViewModel() {

    var state by mutableStateOf(ConnectionState())
        private set

    private var findAllJob: Job? = null

    init {
        findConnections()
    }

    fun onEvent(event: ConnectionEvent) = when (event) {
        is ConnectionEvent.FindConnections -> findConnections()
        is ConnectionEvent.FindPendingConnections -> findPendingConnections()
        is ConnectionEvent.FindBlockedConnections -> findBlockedConnections()
        is ConnectionEvent.AcceptConnection -> acceptConnection(event.userId)
        is ConnectionEvent.RejectConnection -> rejectConnection(event.userId)
        is ConnectionEvent.BlockConnection -> blockConnection(event.userId)
        is ConnectionEvent.UnblockConnection -> unblockConnection(event.userId)
        is ConnectionEvent.RemoveConnection -> removeConnection(event.userId)
    }

    private fun removeConnection(friendId: String) {
        viewModelScope.launch {
            useCaseFacade.communicationUseCase
                .removeConnection(friendId)
                .let { resourceCallbackForCRUD(it, friendId) }
        }
    }

    private fun unblockConnection(friendId: String) {
        viewModelScope.launch {
            useCaseFacade.communicationUseCase
                .unblockConnection(friendId)
                .let { resourceCallbackForCRUD(it, friendId) }
        }

    }

    private fun blockConnection(friendId: String) {
        viewModelScope.launch {
            useCaseFacade.communicationUseCase
                .blockConnection(friendId)
                .let { resourceCallbackForCRUD(it, friendId) }
        }
    }

    private fun rejectConnection(friendId: String) {
        viewModelScope.launch {
            useCaseFacade.communicationUseCase
                .answerConnectionRequest(friendId, false)
                .let { resourceCallbackForCRUD(it, friendId) }
        }
    }

    private fun acceptConnection(friendId: String) {
        viewModelScope.launch {
            useCaseFacade.communicationUseCase
                .answerConnectionRequest(friendId, true)
                .let { resourceCallbackForCRUD(it, friendId) }
        }

    }

    private fun findBlockedConnections() {
        findAllJob?.cancel()
        state = state.copy(isLoading = true)
        findAllJob = viewModelScope.launch {
            useCaseFacade.communicationUseCase.findBlockedConnections()
                .let(::resourceCallbackForFind)
        }
    }

    private fun findPendingConnections() {
        findAllJob?.cancel()

        state = state.copy(isLoading = true)

        findAllJob = viewModelScope.launch {
            useCaseFacade.communicationUseCase.findPendingConnections().let(::resourceCallbackForFind)
        }

    }


    private fun findConnections() {
        findAllJob?.cancel()

        state = state.copy(isLoading = true)

        findAllJob = viewModelScope.launch {
            useCaseFacade.communicationUseCase.findConnections().let(::resourceCallbackForFind)
        }
    }


    private fun resourceCallbackForFind(resource: Resource<List<UserConnectionDTO>>) {
        state = when (resource) {
            is Resource.Loading -> state.copy(isLoading = true, error = "")

            is Resource.Success -> state.copy(connections = resource.data ?: emptyList())

            is Resource.Error -> state.copy(error = resource.message ?: "An error occurred")
        }
    }

    private fun resourceCallbackForCRUD(res: Resource<Boolean>, friendId: String) {
        state = when (res) {
            is Resource.Loading -> state.copy(isLoading = true, error = "")

            is Resource.Success -> {
                state.copy(isLoading = false, error = "", connections = state.connections.filter { it.userId != friendId })
            }

            is Resource.Error -> state.copy(isLoading = false, error = res.message ?: "An error occurred")
        }
    }
}