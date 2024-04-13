package callofproject.dev.androidapp.presentation.project.project_filter

import android.content.Context
import android.content.res.Resources
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import callofproject.dev.androidapp.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class ProjectFilterViewModel @Inject constructor(@ApplicationContext context: Context) :
    ViewModel() {
    private val resources = context.resources as Resources

    val professionLevels = resources.getStringArray(R.array.array_professionLevels).toList()
    val projectLevels = resources.getStringArray(R.array.array_projectLevels).toList()
    val degreeItems = resources.getStringArray(R.array.array_degrees).toList()
    val feedbackTimeRanges = resources.getStringArray(R.array.array_feedbackTimeRanges).toList()
    val interviewTypes = resources.getStringArray(R.array.array_interviewType).toList()
    val projectStatusItems = resources.getStringArray(R.array.array_projectStatus).toList()

    var selectedDegrees = mutableStateOf(listOf<String>())
    var selectedFeedbackTimeRanges = mutableStateOf(listOf(""))
    var selectedProjectStatus = mutableStateOf(listOf<String>())
    var isOpenStartDateDialog = mutableStateOf(false)
    var isOpenCompletionDateDialog = mutableStateOf(false)
    var selectedProfessionLevel = mutableStateOf("")
    var selectedInterviewType = mutableStateOf("")
    var selectedProjectLevel = mutableStateOf("")
    var wordContains = mutableStateOf("")
    var startDate = mutableStateOf("")
    var selectedExpectedCompletionDate = mutableStateOf("")


    fun onEvent(event: ProjectFilterEvent) = when (event) {
        is ProjectFilterEvent.OnClickFilterBtn -> applyFilters()
        else -> Unit
    }

    private fun applyFilters() {

    }
}