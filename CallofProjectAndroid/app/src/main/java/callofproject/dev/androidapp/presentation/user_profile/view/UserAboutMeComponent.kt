package callofproject.dev.androidapp.presentation.user_profile.view

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.presentation.components.EditableCardComponent
import callofproject.dev.androidapp.presentation.user_profile.UserProfileEvent
import callofproject.dev.androidapp.presentation.user_profile.UserProfileState
import callofproject.dev.androidapp.presentation.user_profile.UserProfileViewModel
import callofproject.dev.androidapp.presentation.user_profile.edit.UserAboutMeEditComponent

@Composable
fun UserAboutMeComponent(
    state: UserProfileState,
    viewModel: UserProfileViewModel,
    isEditable: Boolean = true,
    expandedUpsertAboutMe: MutableState<Boolean> = mutableStateOf(false),
) {

    EditableCardComponent(
        height = 250.dp,
        removable = false,
        title = stringResource(R.string.title_aboutMe),
        isEditable = isEditable,
        onIconClick = { expandedUpsertAboutMe.value = true })
    {
        val scrollState = rememberScrollState()
        Text(
            text = state.userProfileDTO.profile.aboutMe ?: "",
            fontSize = 14.5.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(5.dp).verticalScroll(scrollState)
        )
    }

    if (expandedUpsertAboutMe.value) {
        UserAboutMeEditComponent(
            onDismissRequest = { expandedUpsertAboutMe.value = false },
            defaultAboutMe = state.userProfileDTO.profile.aboutMe ?: "",
            confirmEvent = { viewModel.onEvent(UserProfileEvent.OnUpdateAboutMe(it)) }
        )
    }
}