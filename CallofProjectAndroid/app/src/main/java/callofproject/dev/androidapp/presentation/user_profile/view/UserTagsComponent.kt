package callofproject.dev.androidapp.presentation.user_profile.view

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.presentation.components.EditableCardComponent
import callofproject.dev.androidapp.presentation.components.TagItem
import callofproject.dev.androidapp.presentation.user_profile.UserProfileEvent
import callofproject.dev.androidapp.presentation.user_profile.UserProfileState
import callofproject.dev.androidapp.presentation.user_profile.UserProfileViewModel
import callofproject.dev.androidapp.presentation.user_profile.edit.UserTagEditComponent

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun UserTagsComponent(
    state: UserProfileState,
    viewModel: UserProfileViewModel,
    isEditable: Boolean = true
) {

    var expandedAddLink by remember { mutableStateOf(false) }

    EditableCardComponent(
        stringResource(R.string.title_userTags),
        400.dp,
        imageVector = Icons.Filled.Add,
        imageDescription = stringResource(R.string.default_image_description),
        isEditable = isEditable,
        removable = false,
        onIconClick = { expandedAddLink = true }
    )
    {

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.CenterStart)
        ) {

            (0..<state.userProfileDTO.profile.tags.size).forEach { idx ->
                TagItem(text = state.userProfileDTO.profile.tags[idx].tagName, isRemovable = false)
            }
        }

    }


    if (expandedAddLink)
        UserTagEditComponent(
            onDismissRequest = { expandedAddLink = false },
            userTagDTO = state.userProfileDTO.profile.tags,
            confirmEvent = { viewModel.onEvent(UserProfileEvent.OnCreateTag(it)) },
            removeEvent = { viewModel.onEvent(UserProfileEvent.OnRemoveTag(it)) }
        )
}