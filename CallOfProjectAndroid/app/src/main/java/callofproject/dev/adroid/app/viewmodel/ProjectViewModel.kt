package callofproject.dev.adroid.app.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import callofproject.dev.adroid.servicelib.di.ICallOfProjectService
import callofproject.dev.adroid.servicelib.dto.ApiResponse
import callofproject.dev.adroid.servicelib.dto.ProjectDetailDTO
import callofproject.dev.adroid.servicelib.dto.ProjectDiscoveryDTO
import callofproject.dev.adroid.servicelib.dto.ProjectOverviewDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.UUID
import java.util.UUID.fromString
import java.util.concurrent.ExecutorService
import javax.inject.Inject

@HiltViewModel
class ProjectViewModel @Inject constructor(
    private val executor: ExecutorService,
    private val callOfProjectService: ICallOfProjectService
) : ViewModel() {

    private val _projectDiscoveries = MutableStateFlow<List<ProjectDiscoveryDTO>?>(null)
    val result: StateFlow<List<ProjectDiscoveryDTO>?> = _projectDiscoveries

    private val _overview = MutableStateFlow<ProjectOverviewDTO?>(null)
    val overview: StateFlow<ProjectOverviewDTO?> = _overview

    private val _details = MutableStateFlow<ProjectDetailDTO?>(null)
    val detail: StateFlow<ProjectDetailDTO?> = _details

    var isLoading = mutableStateOf(false)

    fun projectDiscovery() {
        executor.execute {
            executeApiCall(callOfProjectService.projectDiscovery(1)) { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        _projectDiscoveries.value = response.data.`object`.projects
                    }

                    is ApiResponse.Error -> {
                        Log.v("ProjectViewModel", "PROJECT FAIL!")
                    }
                }
            }
        }
    }


    fun findProjectOverviewByProjectId(id: UUID) {
        viewModelScope.launch {
            isLoading.value = true
            executeApiCall(callOfProjectService.findProjectOverviewsById(id)) { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        Log.v("ProjectOverview", "IN SUCCESS")
                        _overview.value = response.data.`object`
                        isLoading.value = false
                        Log.v("ProjectOverview", response.data.`object`.toString())
                    }

                    is ApiResponse.Error -> {
                        Log.v("ProjectViewModel", "PROJECT FAIL!")
                    }
                }
            }
        }
    }


    fun findProjectDetailsByProjectId(id: UUID) {
        viewModelScope.launch {
            isLoading.value = true
            executeApiCall(
                callOfProjectService.findProjectDetailsById(
                    id,
                    fromString("4f3afef8-32d4-4853-8f92-ce555fe5f71f")
                )
            ) { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        Log.i("COP:PROJECT_DETAIL_SUCCESS", "SUCCESS")
                        _details.value = response.data.`object`
                        isLoading.value = false
                        Log.v("DETAIL_P", response.data.`object`.toString())
                    }

                    is ApiResponse.Error -> {
                        Log.e("DETAIL_P", "PROJECT FAIL!")
                    }
                }
            }
        }
    }


    private fun <T> executeApiCall(call: Call<T>, callback: (ApiResponse<T>) -> Unit) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        callback(ApiResponse.Success(it))
                    } ?: callback(ApiResponse.Error("Empty Response"))
                } else {
                    callback(ApiResponse.Error(response.message()))
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                callback(ApiResponse.Error(t.message ?: "Unknown Error"))
            }
        })
    }
}