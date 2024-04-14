package callofproject.dev.androidapp.presentation.user_profile.view

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import callofproject.dev.androidapp.domain.dto.user_profile.education.EducationDTO
import callofproject.dev.androidapp.presentation.components.EditableCardComponent
import callofproject.dev.androidapp.presentation.user_profile.UserProfileEvent
import callofproject.dev.androidapp.presentation.user_profile.UserProfileState
import callofproject.dev.androidapp.presentation.user_profile.UserProfileViewModel
import callofproject.dev.androidapp.presentation.user_profile.edit.UserEducationEditComponent

@Composable
fun UserEducationComponent(state: UserProfileState, viewModel: UserProfileViewModel) {
    var expandedUpsertEducation by remember { mutableStateOf(false) }
    var expandedAddEducation by remember { mutableStateOf(false) }
    var selectedEducationIndex by remember { mutableIntStateOf(-1) }

    EditableCardComponent(
        "Education",
        400.dp,
        imageVector = Icons.Filled.Add,
        imageDescription = "Add",
        onIconClick = { expandedAddEducation = true }
    ) {
        LazyColumn {

            items(state.userProfileDTO.profile.educations.size) { idx ->
                EditableCardComponent(
                    title = state.userProfileDTO.profile.educations[idx].schoolName,
                    onIconClick = { expandedUpsertEducation = true; selectedEducationIndex = idx }
                ) {
                    EducationDetails(state.userProfileDTO.profile.educations[idx])
                }
            }
        }
    }

    if (expandedUpsertEducation) {
        UserEducationEditComponent(
            onDismissRequest = { expandedUpsertEducation = false },
            educationDTO = state.userProfileDTO.profile.educations[selectedEducationIndex],
            confirmEvent = { viewModel.onEvent(UserProfileEvent.OnUpdateEducation(it)) }
        )
    }

    if (expandedAddEducation) {
        UserEducationEditComponent(
            onDismissRequest = { expandedAddEducation = false },
            educationDTO = EducationDTO(),
            confirmEvent = { viewModel.onEvent(UserProfileEvent.OnCreateEducation(it)) }
        )
    }
}

@Composable
private fun EducationDetails(education: EducationDTO) {
    Text(
        text = "Department: ${education.department}",
        fontSize = 15.sp,
        fontWeight = FontWeight.Normal,
        modifier = Modifier.padding(5.dp)
    )
    Text(
        text = "Date: ${education.startDate} - ${education.finishDate}",
        fontSize = 15.sp,
        fontWeight = FontWeight.Normal,
        modifier = Modifier.padding(5.dp)
    )
    Text(
        text = "GPA: ${education.gpa}",
        fontSize = 15.sp,
        fontWeight = FontWeight.Normal,
        modifier = Modifier.padding(5.dp)
    )
}