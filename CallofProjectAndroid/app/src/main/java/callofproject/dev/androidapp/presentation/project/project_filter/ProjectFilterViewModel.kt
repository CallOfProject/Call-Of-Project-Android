package callofproject.dev.androidapp.presentation.project.project_filter

import android.content.Context
import android.content.res.Resources
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.domain.dto.filter.ProjectFilterDTO
import callofproject.dev.androidapp.domain.preferences.IPreferences
import callofproject.dev.androidapp.domain.use_cases.UseCaseFacade
import callofproject.dev.androidapp.util.Resource
import callofproject.dev.androidapp.util.route.Route
import callofproject.dev.androidapp.util.route.UiEvent
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectFilterViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val useCases: UseCaseFacade,
    private val gson: Gson,
    private val pref: IPreferences
) :
    ViewModel() {
    private val resources = context.resources as Resources

    val professionLevels = resources.getStringArray(R.array.array_professionLevels).toList()
    val projectLevels = resources.getStringArray(R.array.array_projectLevels).toList()
    val degreeItems = resources.getStringArray(R.array.array_degrees).toList()
    val feedbackTimeRanges = resources.getStringArray(R.array.array_feedbackTimeRanges).toList()
    val interviewTypes = resources.getStringArray(R.array.array_interviewType).toList()
    val projectStatusItems = resources.getStringArray(R.array.array_projectStatus).toList()

    var selectedDegrees = mutableStateOf("")
    var selectedFeedbackTimeRanges = mutableStateOf("")
    var selectedProjectStatus = mutableStateOf("")
    var isOpenStartDateDialog = mutableStateOf(false)
    var isOpenCompletionDateDialog = mutableStateOf(false)
    var isOpenDeadlineDialog = mutableStateOf(false)
    var selectedProfessionLevel = mutableStateOf("")
    var selectedInterviewType = mutableStateOf("")
    var selectedProjectLevel = mutableStateOf("")
    var wordContains = mutableStateOf("")
    var startDate = mutableStateOf("")
    var selectedExpectedCompletionDate = mutableStateOf("")
    var selectedApplicationDeadline = mutableStateOf("")

    private var filterJob: Job? = null
    var state by mutableStateOf(ProjectFilterState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun onEvent(event: ProjectFilterEvent) = when (event) {
        is ProjectFilterEvent.OnClickSaveFilterBtn -> saveFilterOpt()

        is ProjectFilterEvent.OnClickFilterProjectBtn -> findProjectsByFilter(event.projectFilterDTO)

        is ProjectFilterEvent.OnClickProjectDiscoveryItem -> navigateProjectOverview(event.projectId)
    }

    private fun findProjectsByFilter(projectFilterDTO: ProjectFilterDTO) {
        filterJob?.cancel()
        state = state.copy(isLoading = true)
        val startDateStr = projectFilterDTO.startDate?.replace("-", "/")
        val completionDateStr = projectFilterDTO.expectedCompletionDate?.replace("-", "/")
        val deadlineStr = projectFilterDTO.applicationDeadline?.replace("-", "/")
        projectFilterDTO.startDate = startDateStr
        projectFilterDTO.expectedCompletionDate = completionDateStr
        projectFilterDTO.applicationDeadline = deadlineStr

        filterJob = viewModelScope.launch {
            useCases.project.filterProjects(projectFilterDTO, 1)
                .onStart { delay(50L) }
                .onEach { result ->
                    when (result) {
                        is Resource.Loading -> {
                            state = state.copy(isLoading = true)
                        }

                        is Resource.Success -> {
                            state = state.copy(
                                projectFilterList = result.data!!.`object`,
                                isLoading = false,
                                error = ""
                            )
                        }

                        is Resource.Error -> {
                            state = state.copy(error = result.message!!, isLoading = false)
                        }
                    }

                }.launchIn(this)
        }
    }

    private fun saveFilterOpt() {
        viewModelScope.launch {
            val dto = createDTO()
            pref.saveFilterObjects(dto)
            _uiEvent.send(UiEvent.Navigate("${Route.FILTERED_PROJECTS}/${gson.toJson(dto)}"))
        }
    }

    private fun navigateProjectOverview(projectId: String) {
        viewModelScope.launch {
            _uiEvent.send(UiEvent.Navigate("${Route.PROJECT_OVERVIEW}/$projectId/0"))
        }
    }

    private fun createDTO(): ProjectFilterDTO {
        val startDateStr = startDate.value.replace("/", "-").ifBlank { null }

        val expectedCompletionDateStr = selectedExpectedCompletionDate.value
            .replace("/", "-")
            .ifBlank { null }

        val applicationDeadlineStr = selectedApplicationDeadline.value
            .replace("/", "-")
            .ifBlank { null }

        return ProjectFilterDTO(
            selectedProfessionLevel.value.ifBlank { null },
            selectedProjectLevel.value.ifBlank { null },
            selectedDegrees.value.ifBlank { null },
            selectedFeedbackTimeRanges.value.ifBlank { null },
            selectedInterviewType.value.ifBlank { null },
            selectedProjectStatus.value.ifBlank { null },
            startDateStr,
            expectedCompletionDateStr,
            applicationDeadlineStr,
            wordContains.value.ifBlank { "" },
        )
    }

    fun clearFilter() = pref.clearFilterObjects()
    fun putFilterOptions() {
        val savedFilterObjects = pref.getFilterObjects()
        startDate.value = savedFilterObjects.startDate!!
        selectedExpectedCompletionDate.value = savedFilterObjects.expectedCompletionDate!!
        selectedApplicationDeadline.value = savedFilterObjects.applicationDeadline!!
        selectedDegrees.value = savedFilterObjects.degree!!
        selectedFeedbackTimeRanges.value = savedFilterObjects.feedbackTimeRange!!
        selectedInterviewType.value = savedFilterObjects.interviewType!!
        selectedProjectStatus.value = savedFilterObjects.projectStatus!!
        selectedProfessionLevel.value = savedFilterObjects.professionLevel!!
        selectedProjectLevel.value = savedFilterObjects.projectLevel!!
        wordContains.value = savedFilterObjects.keyword!!
    }

}