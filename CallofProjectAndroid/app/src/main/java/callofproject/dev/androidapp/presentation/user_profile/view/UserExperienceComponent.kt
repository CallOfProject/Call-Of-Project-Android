package callofproject.dev.androidapp.presentation.user_profile.view

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.domain.dto.user_profile.experience.ExperienceDTO
import callofproject.dev.androidapp.presentation.components.EditableCardComponent
import callofproject.dev.androidapp.presentation.user_profile.UserProfileEvent
import callofproject.dev.androidapp.presentation.user_profile.UserProfileState
import callofproject.dev.androidapp.presentation.user_profile.UserProfileViewModel
import callofproject.dev.androidapp.presentation.user_profile.edit.UserExperienceEditComponent
import callofproject.dev.androidapp.util.route.UiEvent

@Composable
fun UserExperienceComponent(
    state: UserProfileState,
    viewModel: UserProfileViewModel,
    isEditable: Boolean = true,
    expandedUpsertExperience: MutableState<Boolean> = mutableStateOf(false),
    expandedAddExperience: MutableState<Boolean> = mutableStateOf(false),
) {

    var selectedExperienceIndex by remember { mutableIntStateOf(-1) }


    EditableCardComponent(
        stringResource(R.string.title_experience),
        400.dp,
        imageVector = Icons.Filled.Add,
        imageDescription = stringResource(R.string.default_image_description),
        isEditable = isEditable,
        removable = false,
        onIconClick = { expandedAddExperience.value = true }) {

        LazyColumn {

            items(state.userProfileDTO.profile.experiences.size) { index ->

                val experience = state.userProfileDTO.profile.experiences[index]

                EditableCardComponent(
                    height = 250.dp,
                    title = experience.companyName,
                    isEditable = isEditable,
                    onRemoveClick = {
                        viewModel.onEvent(
                            UserProfileEvent.OnDeleteExperience(
                                experience.experienceId
                            )
                        )
                    },
                    onIconClick = {
                        expandedUpsertExperience.value = true; selectedExperienceIndex = index
                    }
                ) { ExperienceDetails(experience = experience) }
            }
        }
    }

    if (expandedUpsertExperience.value)
        UserExperienceEditComponent(
            onDismissRequest = { expandedUpsertExperience.value = false },
            experienceDTO = state.userProfileDTO.profile.experiences[selectedExperienceIndex],
            confirmEvent = { viewModel.onEvent(UserProfileEvent.OnUpdateExperience(it)) }
        )

    if (expandedAddExperience.value)
        UserExperienceEditComponent(
            onDismissRequest = { expandedAddExperience.value = false },
            confirmEvent = { viewModel.onEvent(UserProfileEvent.OnCreateExperience(it)) }
        )
}


@Composable
private fun ExperienceDetails(experience: ExperienceDTO) {
    Row {
        Text(
            text = stringResource(R.string.title_position),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
        )

        Text(
            text = experience.position,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
        )
    }


    Row {
        Text(
            text = stringResource(R.string.title_date),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
        )

        Text(
            text = "${experience.startDate} - ${experience.finishDate}",
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
        )
    }

    Column {
        Text(
            text = stringResource(R.string.title_description),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,

            )

        Text(
            text = experience.description,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            overflow = TextOverflow.Ellipsis,

            )
    }

}