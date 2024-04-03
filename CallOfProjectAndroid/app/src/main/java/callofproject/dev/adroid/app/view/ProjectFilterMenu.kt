package callofproject.dev.adroid.app.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import callofproject.dev.adroid.app.view.util.NormalTextField
import callofproject.dev.android.authentication.components.CustomDatePicker


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterScreen(isFiltering: MutableState<Boolean>) {
    val isOpenStartDateDialog = remember { mutableStateOf(false) }
    val isOpenCompletionDateDialog = remember { mutableStateOf(false) }
    val professionLevel = remember { mutableStateOf("") }
    val degrees = remember { mutableStateOf(listOf("")) }
    val feedbackTimeRanges = remember { mutableStateOf(listOf("")) }
    val interviewType = remember { mutableStateOf("") }
    val projectLevel = remember { mutableStateOf("") } // single
    val projectStatus = remember { mutableStateOf(listOf("")) }
    val wordContains = remember { mutableStateOf("") }
    val startDate = remember { mutableStateOf("Start Date") }
    val expectedCompletionDate = remember { mutableStateOf("Completion Date") }


    ModalBottomSheet(
        modifier = Modifier.fillMaxSize(),
        onDismissRequest = { isFiltering.value = false }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FilterByProfessionLevel(professionLevel)
            FilterByProjectLevel(projectLevel)
            FilterByDegree(degrees)
            FilterByFeedbackTimeRange(feedbackTimeRanges)
            FilterByInterviewType(interviewType)
            FilterByProjectStatus(projectStatus)
            FilterByWordContains(wordContains)
            FilterByDate(
                isOpenStartDateDialog,
                isOpenCompletionDateDialog,
                startDate,
                expectedCompletionDate
            )
            Button(onClick = { }) {
                Text(text = "Apply Filters")
            }
        }
    }

}

@Composable
fun FilterByWordContains(wordContains: MutableState<String>) {
    NormalTextField(
        text = "Word",
        value = wordContains.value,
        onValueChange = { wordContains.value = it },
        modifier = Modifier.padding(bottom = 8.dp),
        textStyle = TextStyle(fontSize = 12.sp)
    )
}

@Composable
fun FilterByDate(
    isOpenStartDateDialog: MutableState<Boolean>,
    isOpenCompletionDateDialog: MutableState<Boolean>,
    startDate: MutableState<String>,
    expectedCompletionDate: MutableState<String>
) {

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        DatePickerComponent(isOpenStartDateDialog, startDate)
        DatePickerComponent(isOpenCompletionDateDialog, expectedCompletionDate)
    }
}

@Composable
fun DatePickerComponent(
    isOpenDateDialog: MutableState<Boolean>,
    date: MutableState<String>
) {
    OutlinedButton(
        onClick = { isOpenDateDialog.value = true }, modifier = Modifier
            .width(150.dp)

    ) {
        Text(text = date.value, fontSize = 9.5.sp)


        CustomDatePicker(
            isOpenDateDialog = isOpenDateDialog.value,
            onDateSelected = { selectedDate ->
                date.value = selectedDate
            },
            onDismiss = { isOpenDateDialog.value = false })
    }
}

@Composable
fun FilterByProjectStatus(projectStatus: MutableState<List<String>>) {
    val itemsList = remember {
        listOf(
            "NOT_STARTED",
            "IN_PROGRESS",
            "FINISHED"
        )
    }
    MultipleFilterChipComponent("Project Status", projectStatus, itemsList)
}

@Composable
fun FilterByInterviewType(interviewType: MutableState<String>) {
    val itemsList = listOf(
        "CODING",
        "TEST",
        "NO_INTERVIEW"
    )
    SingleFilterChipComponent("Interview Type", interviewType, itemsList)
}

@Composable
fun FilterByFeedbackTimeRange(feedbackTimeRanges: MutableState<List<String>>) {

    val itemsList = listOf(
        "ONE_WEEK",
        "TWO_WEEKS",
        "ONE_MONTH",
    )

    MultipleFilterChipComponent("Feedback Time Range", feedbackTimeRanges, itemsList)
}

@Composable
fun FilterByDegree(degrees: MutableState<List<String>>) {
    val itemsList = listOf(
        "BACHELOR",
        "MASTER",
        "PHD"
    )

    MultipleFilterChipComponent("Degree", degrees, itemsList)
}

@Composable
fun FilterByProjectLevel(projectLevel: MutableState<String>) {
    val itemsList = listOf(
        "ENTRY_ LEVEL",
        "INTERMEDIATE",
        "EXPERT"
    )
    SingleFilterChipComponent("Project Level", projectLevel, itemsList)
}

@Composable
fun FilterByProfessionLevel(
    professionLevel: MutableState<String>
) {
    val itemsList = listOf(
        "ENTRY_ LEVEL",
        "INTERMEDIATE",
        "EXPERT"
    )
    SingleFilterChipComponent("Profession Level", professionLevel, itemsList)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultipleFilterChipComponent(
    text: String,
    selectedItems: MutableState<List<String>>,
    itemsList: List<String>
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .border(BorderStroke(1.dp, SolidColor(Color.Gray)))
    ) {
        Text(
            text = text,
            fontSize = 15.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
            modifier = Modifier.padding(5.dp)
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.Center
        ) {
            items(itemsList) { item ->
                FilterChip(
                    modifier = Modifier.padding(horizontal = 6.dp), // gap between items
                    selected = selectedItems.value.contains(item),
                    onClick = {
                        val currentList =
                            selectedItems.value.toMutableList()
                        if (currentList.contains(item)) {
                            currentList.remove(item)
                        } else {
                            currentList.add(item)
                        }
                        selectedItems.value = currentList
                    },
                    label = {
                        Text(text = item, fontSize = 9.5.sp)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleFilterChipComponent(
    text: String,
    selectedItem: MutableState<String>,
    itemsList: List<String>,
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .border(BorderStroke(1.dp, SolidColor(Color.Gray)))
    ) {
        Text(
            text = text,
            fontSize = 15.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
            modifier = Modifier.padding(5.dp)
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.Center
        ) {
            items(itemsList) { item ->
                FilterChip(
                    modifier = Modifier.padding(horizontal = 6.dp),
                    selected = (item == selectedItem.value),
                    onClick = {
                        selectedItem.value = item
                    },
                    label = {
                        Text(text = item, fontSize = 9.5.sp)
                    }
                )
            }
        }
    }
}
