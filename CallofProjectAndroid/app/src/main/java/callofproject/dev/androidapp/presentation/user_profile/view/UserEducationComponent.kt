package callofproject.dev.androidapp.presentation.user_profile.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import callofproject.dev.androidapp.domain.dto.user_profile.education.EducationDTO
import callofproject.dev.androidapp.presentation.components.EditableCardComponent
import callofproject.dev.androidapp.presentation.user_profile.UserProfileEvent
import callofproject.dev.androidapp.presentation.user_profile.UserProfileState
import callofproject.dev.androidapp.presentation.user_profile.UserProfileViewModel
import callofproject.dev.androidapp.presentation.user_profile.edit.UserEducationEditComponent

@Composable
fun UserEducationComponent(
    state: UserProfileState,
    viewModel: UserProfileViewModel,
    isEditable: Boolean = true
) {
    var expandedUpsertEducation by remember { mutableStateOf(false) }
    var expandedAddEducation by remember { mutableStateOf(false) }
    var selectedEducationIndex by remember { mutableIntStateOf(-1) }

    EditableCardComponent(
        "Education",
        500.dp,
        imageVector = Icons.Filled.Add,
        imageDescription = "Add",
        isEditable = isEditable,
        removable = false,
        onIconClick = { expandedAddEducation = true }
    ) {
        LazyColumn {

            items(state.userProfileDTO.profile.educations.size) { idx ->

                EditableCardComponent(
                    height = 250.dp,
                    title = state.userProfileDTO.profile.educations[idx].schoolName,
                    isEditable = isEditable,
                    onRemoveClick = { viewModel.onEvent(UserProfileEvent.OnDeleteEducation(state.userProfileDTO.profile.educations[idx].educationId)) },
                    onIconClick = { expandedUpsertEducation = true; selectedEducationIndex = idx }
                ) { EducationDetails(state.userProfileDTO.profile.educations[idx]) }
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

    Row {
        Text(
            text = "Department:",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
        )

        Text(
            text = education.department,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
        )
    }


    Row {
        Text(
            text = "Date:",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
        )

        Text(
            text = "${education.startDate} - ${education.finishDate}",
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
        )
    }


    Row {
        Text(
            text = "GPA:",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,

            )

        Text(
            text = education.gpa.toString(),
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            overflow = TextOverflow.Ellipsis,

            )
    }

    Column {
        Text(
            text = "Description:",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,

            )

        Text(
            text = education.description,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            overflow = TextOverflow.Ellipsis,

            )
    }

}