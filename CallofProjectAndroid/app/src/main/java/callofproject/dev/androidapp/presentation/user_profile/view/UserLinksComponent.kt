package callofproject.dev.androidapp.presentation.user_profile.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.domain.dto.user_profile.link.LinkDTO
import callofproject.dev.androidapp.presentation.components.EditableCardComponent
import callofproject.dev.androidapp.presentation.user_profile.UserProfileEvent.OnCreateLink
import callofproject.dev.androidapp.presentation.user_profile.UserProfileEvent.OnUpdateLink
import callofproject.dev.androidapp.presentation.user_profile.UserProfileState
import callofproject.dev.androidapp.presentation.user_profile.UserProfileViewModel
import callofproject.dev.androidapp.presentation.user_profile.edit.UserLinkEditComponent

@Composable
fun UserLinksComponent(state: UserProfileState, viewModel: UserProfileViewModel) {
    val context = LocalContext.current
    var expandedUpdateLink by remember { mutableStateOf(false) }
    var expandedAddLink by remember { mutableStateOf(false) }
    var selectedLinkIndex by remember { mutableIntStateOf(-1) }

    EditableCardComponent(
        title = stringResource(R.string.title_links),
        height = 400.dp,
        imageVector = Icons.Filled.Add,
        imageDescription = "Add",
        onIconClick = { expandedAddLink = true }
    ) {
        LazyColumn {
            items(state.userProfileDTO.profile.links.size) { index ->

                val link = state.userProfileDTO.profile.links[index]

                EditableCardComponent(
                    height = 100.dp,
                    title = link.linkTitle,
                    onIconClick = { expandedUpdateLink = true; selectedLinkIndex = index }
                ) { LinkDetails(link, context) }
            }
        }
    }



    if (expandedAddLink)
        UserLinkEditComponent(
            onDismissRequest = { expandedAddLink = false },
            confirmEvent = { viewModel.onEvent(OnCreateLink(it)) }
        )

    if (expandedUpdateLink)
        UserLinkEditComponent(
            onDismissRequest = { expandedUpdateLink = false },
            linkDTO = state.userProfileDTO.profile.links[selectedLinkIndex],
            confirmEvent = { viewModel.onEvent(OnUpdateLink(it)) }
        )
}

@Composable
fun LinkDetails(link: LinkDTO, context: Context) {
    Text(
        text = link.link,
        style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Normal),
        modifier = Modifier
            .padding(5.dp)
            .clickable { handleLinkClick(context, link) }
    )
}


private fun handleLinkClick(context: Context, link: LinkDTO) {
    ContextCompat.startActivity(context, Intent(Intent.ACTION_VIEW, Uri.parse(link.link)), null)
}