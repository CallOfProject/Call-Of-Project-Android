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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.domain.dto.user_profile.experience.ExperienceDTO
import callofproject.dev.androidapp.presentation.components.EditableCardComponent
import callofproject.dev.androidapp.presentation.user_profile.UserProfileEvent
import callofproject.dev.androidapp.presentation.user_profile.UserProfileState
import callofproject.dev.androidapp.presentation.user_profile.UserProfileViewModel
import callofproject.dev.androidapp.presentation.user_profile.edit.UserExperienceEditComponent

@Composable
fun UserExperienceComponent(
    state: UserProfileState,
    viewModel: UserProfileViewModel,
    isEditable: Boolean = true
) {
    var expandedUpsertExperience by remember { mutableStateOf(false) }
    var expandedAddExperience by remember { mutableStateOf(false) }
    var selectedExperienceIndex by remember { mutableIntStateOf(-1) }

    EditableCardComponent(
        stringResource(R.string.title_experience),
        400.dp,
        imageVector = Icons.Filled.Add,
        imageDescription = stringResource(R.string.default_image_description),
        isEditable = isEditable,
        onIconClick = { expandedAddExperience = true }) {

        LazyColumn {

            items(state.userProfileDTO.profile.experiences.size) { index ->

                val experience = state.userProfileDTO.profile.experiences[index]

                EditableCardComponent(
                    title = experience.companyName,
                    isEditable = isEditable,
                    onIconClick = {
                        expandedUpsertExperience = true; selectedExperienceIndex = index
                    }
                ) { ExperienceDetails(experience = experience) }
            }
        }
    }

    if (expandedUpsertExperience)
        UserExperienceEditComponent(
            onDismissRequest = { expandedUpsertExperience = false },
            experienceDTO = state.userProfileDTO.profile.experiences[selectedExperienceIndex],
            confirmEvent = { viewModel.onEvent(UserProfileEvent.OnUpdateExperience(it)) }
        )

    if (expandedAddExperience)
        UserExperienceEditComponent(
            onDismissRequest = { expandedAddExperience = false },
            confirmEvent = { viewModel.onEvent(UserProfileEvent.OnCreateExperience(it)) }
        )
}


@Composable
private fun ExperienceDetails(experience: ExperienceDTO) {
    Text(
        text = "Position: ${experience.position}",
        fontSize = 15.sp,
        fontWeight = FontWeight.Normal,
        modifier = Modifier.padding(5.dp)
    )

    Text(
        text = "Date: ${experience.startDate} - ${experience.finishDate}",
        fontSize = 15.sp,
        fontWeight = FontWeight.Normal,
        modifier = Modifier.padding(5.dp)
    )

    Text(
        text = "Description: ${experience.description}",
        fontSize = 15.sp,
        fontWeight = FontWeight.Normal,
        modifier = Modifier.padding(5.dp)
    )
}