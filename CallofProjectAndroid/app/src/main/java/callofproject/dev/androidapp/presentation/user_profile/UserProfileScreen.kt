package callofproject.dev.androidapp.presentation.user_profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.presentation.user_profile.view.UserAboutMeComponent
import callofproject.dev.androidapp.presentation.user_profile.view.UserCoursesComponent
import callofproject.dev.androidapp.presentation.user_profile.view.UserEducationComponent
import callofproject.dev.androidapp.presentation.user_profile.view.UserExperienceComponent
import callofproject.dev.androidapp.presentation.user_profile.view.UserLinksComponent
import callofproject.dev.androidapp.presentation.user_profile.view.UserRatingComponent
import coil.compose.rememberAsyncImagePainter

@Composable
fun UserProfileScreen(
    userId: String,
    scaffoldState: SnackbarHostState,
    viewModel: UserProfileViewModel = hiltViewModel(),
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit
) {
    val state = viewModel.state

    LaunchedEffect(key1 = true) { viewModel.findUserProfileByUserId(userId) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        topBar = topBar,
        bottomBar = bottomBar,
        snackbarHost = { SnackbarHost(scaffoldState) })
    {
        Box(contentAlignment = Alignment.TopCenter, modifier = Modifier.fillMaxSize().padding(it))
        {
            Column(
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Center,
                modifier = Modifier.verticalScroll(rememberScrollState())
            )
            {
                UserProfileTopComponent(state, viewModel)
                UserRatingComponent(state, viewModel)
                UserAboutMeComponent(state, viewModel)
                UserEducationComponent(state, viewModel)
                UserExperienceComponent(state, viewModel)
                UserCoursesComponent(state, viewModel)
                UserLinksComponent(state, viewModel)
            }
        }
    }
}


@Composable
private fun UserProfileTopComponent(state: UserProfileState, viewModel: UserProfileViewModel) {
    Image(
        painter = state.userProfileDTO.profile.profilePhoto
            .takeIf { true }
            ?.run { rememberAsyncImagePainter(this) }
            ?: painterResource(R.drawable.account),
        contentDescription = stringResource(R.string.default_image_description),
        modifier = Modifier.size(150.dp).clip(CircleShape)
    )
    Text(
        text = state.userProfileDTO.user.username,
        fontSize = 20.sp,
        fontWeight = FontWeight(700),
        fontStyle = FontStyle.Normal
    )
}