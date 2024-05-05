package callofproject.dev.androidapp.presentation.user_profile.view

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import callofproject.dev.androidapp.presentation.components.EditableCardComponent
import callofproject.dev.androidapp.presentation.components.TagItem
import callofproject.dev.androidapp.presentation.user_profile.UserProfileEvent
import callofproject.dev.androidapp.presentation.user_profile.UserProfileState
import callofproject.dev.androidapp.presentation.user_profile.UserProfileViewModel
import callofproject.dev.androidapp.presentation.user_profile.edit.UserTagEditComponent

@Composable
fun UserTagsComponent(
    state: UserProfileState,
    viewModel: UserProfileViewModel,
    isEditable: Boolean = true
) {

    var expandedAddLink by remember { mutableStateOf(false) }

    EditableCardComponent(
        "User Tags",
        200.dp,
        imageVector = Icons.Filled.Add,
        imageDescription = "Add",
        isEditable = isEditable,
        removable = false,
        onIconClick = { expandedAddLink = true }
    )
    {
        LazyRow {
            items(state.userProfileDTO.profile.tags.size) {
                TagItem(text = state.userProfileDTO.profile.tags[it].tagName, isRemovable = false)
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