package callofproject.dev.androidapp.presentation.project.project_filter

import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.presentation.components.CustomDatePicker
import callofproject.dev.androidapp.presentation.components.MultipleFilterChipComponent
import callofproject.dev.androidapp.presentation.components.SingleFilterChipComponent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterScreen(
    isFiltering: MutableState<Boolean>,
    viewModel: ProjectFilterViewModel = hiltViewModel()
) {

    ModalBottomSheet(
        modifier = Modifier.fillMaxSize(),
        onDismissRequest = { isFiltering.value = false }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            SingleFilterChipComponent(
                stringResource(R.string.text_professionLevels),
                viewModel.selectedProfessionLevel,
                viewModel.professionLevels
            )

            SingleFilterChipComponent(
                stringResource(R.string.text_projectLevel),
                viewModel.selectedProjectLevel,
                viewModel.projectLevels
            )


            SingleFilterChipComponent(
                stringResource(R.string.text_interviewType),
                viewModel.selectedInterviewType,
                viewModel.interviewTypes
            )

            MultipleFilterChipComponent(
                stringResource(R.string.text_degree),
                viewModel.selectedDegrees,
                viewModel.degreeItems
            )

            MultipleFilterChipComponent(
                stringResource(R.string.text_feedbackTimeRange),
                viewModel.selectedFeedbackTimeRanges,
                viewModel.feedbackTimeRanges
            )

            MultipleFilterChipComponent(
                stringResource(R.string.text_projectStatus),
                viewModel.selectedProjectStatus,
                viewModel.projectStatusItems
            )

            OutlinedTextField(
                value = viewModel.wordContains.value,
                onValueChange = { viewModel.wordContains.value = it },
                modifier = Modifier.padding(bottom = 8.dp),
                textStyle = TextStyle(fontSize = 12.sp)
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = SpaceBetween) {
                OutlinedButton(
                    onClick = { viewModel.isOpenStartDateDialog.value = true },
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = viewModel.startDate.value.ifBlank { stringResource(R.string.btn_startDate) }
                    )

                    CustomDatePicker(
                        isOpenDateDialog = viewModel.isOpenStartDateDialog.value,
                        onDateSelected = { date -> viewModel.startDate.value = date },
                        onDismiss = { viewModel.isOpenStartDateDialog.value = false })
                }


                OutlinedButton(
                    onClick = { viewModel.isOpenCompletionDateDialog.value = true },
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                {
                    Text(
                        text = viewModel.selectedExpectedCompletionDate.value.ifBlank {
                            stringResource(R.string.btn_completionDate)
                        }
                    )

                    CustomDatePicker(
                        isOpenDateDialog = viewModel.isOpenCompletionDateDialog.value,
                        onDateSelected = { date ->
                            viewModel.selectedExpectedCompletionDate.value = date
                        },
                        onDismiss = { viewModel.isOpenCompletionDateDialog.value = false })
                }
            }

            Button(onClick = { viewModel.onEvent(ProjectFilterEvent.OnClickFilterBtn) }) {
                Text(text = stringResource(R.string.text_applyFilters))
            }
        }
    }

}