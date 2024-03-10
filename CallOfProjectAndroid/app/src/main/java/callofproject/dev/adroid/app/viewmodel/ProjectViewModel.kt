package callofproject.dev.adroid.app.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import callofproject.dev.adroid.servicelib.di.ICallOfProjectService
import callofproject.dev.adroid.servicelib.dto.ApiResponse
import callofproject.dev.adroid.servicelib.dto.ProjectDiscoveryDTO
import callofproject.dev.adroid.servicelib.dto.ProjectOverviewDTO
import callofproject.dev.adroid.servicelib.dto.ProjectsDiscoveryDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.UUID
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
        executor.execute {
            executeApiCall(callOfProjectService.findProjectOverviewsById(id)) { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        _overview.value = response.data.`object`
                        Log.v("OV:", response.data.`object`.toString())
                    }

                    is ApiResponse.Error -> {
                        Log.v("ProjectViewModel", "PROJECT FAIL!")
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