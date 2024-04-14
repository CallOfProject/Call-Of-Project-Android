package callofproject.dev.androidapp.presentation.user_profile.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import callofproject.dev.androidapp.presentation.components.EditableCardComponent
import callofproject.dev.androidapp.presentation.user_profile.UserProfileEvent
import callofproject.dev.androidapp.presentation.user_profile.UserProfileState
import callofproject.dev.androidapp.presentation.user_profile.UserProfileViewModel
import callofproject.dev.androidapp.presentation.user_profile.edit.UserAboutMeEditComponent

@Composable
fun UserAboutMeComponent(state: UserProfileState, viewModel: UserProfileViewModel) {
    var expandedUpsertAboutMe by remember { mutableStateOf(false) }
    EditableCardComponent(
        title = "About me",
        onIconClick = { expandedUpsertAboutMe = true }) {
        Text(
            text = state.userProfileDTO.profile.aboutMe ?: "",
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(5.dp)
        )
    }

    if (expandedUpsertAboutMe) {
        UserAboutMeEditComponent(
            onDismissRequest = { expandedUpsertAboutMe = false },
            defaultAboutMe = state.userProfileDTO.profile.aboutMe ?: "",
            confirmEvent = { viewModel.onEvent(UserProfileEvent.OnUpdateAboutMe(it)) }
        )
    }
}

