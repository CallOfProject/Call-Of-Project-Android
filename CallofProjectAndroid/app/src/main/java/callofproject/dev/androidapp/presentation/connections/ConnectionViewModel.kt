package callofproject.dev.androidapp.presentation.connections

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
            useCaseFacade.communicationUseCase.removeConnection(friendId).let { res ->
                when (res) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true, error = "")
                    }

                    is Resource.Success -> {
                        state = state.copy(isLoading = false, error = "",
                            connections = state.connections.filter { it.userId != friendId })
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            error = res.message ?: "An error occurred"
                        )
                    }
                }
            }
        }
    }

    private fun unblockConnection(friendId: String) {
        viewModelScope.launch {
            useCaseFacade.communicationUseCase.unblockConnection(friendId).let { res ->
                when (res) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true, error = "")
                    }

                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false,
                            error = "",
                            connections = state.connections.filter { it.userId != friendId }
                        )
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            error = res.message ?: "An error occurred"
                        )
                    }
                }
            }
        }

    }

    private fun blockConnection(friendId: String) {
        viewModelScope.launch {
            useCaseFacade.communicationUseCase.blockConnection(friendId).let { res ->
                when (res) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true, error = "")
                    }

                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false,
                            error = "",
                            connections = state.connections.filter { it.userId != friendId }
                        )
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            error = res.message ?: "An error occurred"
                        )
                    }
                }
            }
        }

    }

    private fun rejectConnection(friendId: String) {

        viewModelScope.launch {
            useCaseFacade.communicationUseCase.answerConnectionRequest(friendId, false).let { res ->
                when (res) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true, error = "")
                    }

                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false,
                            error = "",
                            connections = state.connections.filter { it.userId != friendId }
                        )
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            error = res.message ?: "An error occurred"
                        )
                    }
                }
            }
        }
    }

    private fun acceptConnection(friendId: String) {
        viewModelScope.launch {
            useCaseFacade.communicationUseCase.answerConnectionRequest(friendId, true).let { res ->
                when (res) {
                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = true,
                            error = ""
                        )
                    }

                    is Resource.Success -> {
                        state = state.copy(isLoading = false, error = "",
                            connections = state.connections.filter { it.userId != friendId })
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            error = res.message ?: "An error occurred"
                        )
                    }
                }
            }

        }

    }

    private fun findBlockedConnections() {
        findAllJob?.cancel()
        state = state.copy(isLoading = true)
        findAllJob = viewModelScope.launch {
            useCaseFacade.communicationUseCase.findBlockedConnections().let {
                when (it) {
                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = true,
                            error = ""
                        )
                    }

                    is Resource.Success -> {
                        state = state.copy(connections = it.data ?: emptyList())
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            error = it.message ?: "An error occurred"
                        )
                    }
                }
            }
        }
    }

    private fun findPendingConnections() {
        findAllJob?.cancel()
        state = state.copy(isLoading = true)
        findAllJob = viewModelScope.launch {
            useCaseFacade.communicationUseCase.findPendingConnections().let {
                when (it) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true, error = "")
                    }

                    is Resource.Success -> {
                        state = state.copy(connections = it.data ?: emptyList())
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            error = it.message ?: "An error occurred"
                        )
                    }
                }
            }
        }

    }

    private fun findConnections() {
        findAllJob?.cancel()
        state = state.copy(isLoading = true)
        findAllJob = viewModelScope.launch {
            useCaseFacade.communicationUseCase.findConnections().let {
                when (it) {
                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = true,
                            error = ""
                        )
                    }

                    is Resource.Success -> {
                        state = state.copy(
                            connections = it.data ?: emptyList(),
                        )
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            error = it.message ?: "An error occurred"
                        )
                    }
                }
            }
        }
    }
}