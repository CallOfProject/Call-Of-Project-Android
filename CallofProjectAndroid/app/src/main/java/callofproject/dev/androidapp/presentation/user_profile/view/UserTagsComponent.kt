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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import callofproject.dev.androidapp.presentation.components.EditableCardComponent
import callofproject.dev.androidapp.presentation.components.TagComponent
import callofproject.dev.androidapp.presentation.user_profile.UserProfileState
import callofproject.dev.androidapp.presentation.user_profile.UserProfileViewModel

@Composable
fun UserTagsComponent(
    state: UserProfileState,
    viewModel: UserProfileViewModel,
    isEditable: Boolean = true
) {
    val context = LocalContext.current
    var expandedUpdateLink by remember { mutableStateOf(false) }
    var expandedAddLink by remember { mutableStateOf(false) }
    var selectedLinkIndex by remember { mutableIntStateOf(-1) }
    EditableCardComponent("Courses",
        500.dp,
        imageVector = Icons.Filled.Add,
        imageDescription = "Add",
        isEditable = isEditable,
        removable = false,
        onIconClick = {  })
    {
        LazyRow {
            items(state.userProfileDTO.profile.tags.size) {
                TagComponent(text = state.userProfileDTO.profile.tags[it].tagName)
            }
        }
    }
}