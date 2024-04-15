package callofproject.dev.androidapp.presentation.user_profile

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.presentation.user_profile.view.UploadFileComponent
import callofproject.dev.androidapp.presentation.user_profile.view.UserAboutMeComponent
import callofproject.dev.androidapp.presentation.user_profile.view.UserCoursesComponent
import callofproject.dev.androidapp.presentation.user_profile.view.UserEducationComponent
import callofproject.dev.androidapp.presentation.user_profile.view.UserExperienceComponent
import callofproject.dev.androidapp.presentation.user_profile.view.UserLinksComponent
import callofproject.dev.androidapp.presentation.user_profile.view.UserRatingComponent
import callofproject.dev.androidapp.util.route.UiEvent
import coil.compose.rememberAsyncImagePainter

@Composable
fun UserProfileScreen(
    scaffoldState: SnackbarHostState,
    viewModel: UserProfileViewModel = hiltViewModel(),
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit
) {
    val state = viewModel.state

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.showSnackbar(message = event.msg.asString(context))
                }

                else -> Unit
            }
        }
    }

    LaunchedEffect(key1 = true) { viewModel.findUserProfileByUserId() }

    Scaffold(
        topBar = topBar,
        bottomBar = bottomBar,
    )
    {
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        )
        {
            Column(
                horizontalAlignment = CenterHorizontally,
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(15.dp)
            )
            {

                UserProfileTopComponent(state)
                UserRatingComponent(state)
                UploadFileComponent(state, viewModel)
                UserAboutMeComponent(state, viewModel)
                UserEducationComponent(state, viewModel)
                UserExperienceComponent(state, viewModel)
                UserCoursesComponent(state, viewModel)
                UserLinksComponent(state, viewModel)
            }
        }
    }
}


@SuppressLint("Range")
@Composable
private fun UserProfileTopComponent(state: UserProfileState) {
    Image(
        painter = state.userProfileDTO.profile.profilePhoto
            .takeIf { true }
            ?.run { rememberAsyncImagePainter(this) }
            ?: painterResource(R.drawable.account),
        contentDescription = stringResource(R.string.default_image_description),
        modifier = Modifier.size(200.dp)

    )
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = state.userProfileDTO.user.username,
        fontSize = 20.sp,
        fontWeight = FontWeight(700),
        fontStyle = FontStyle.Normal
    )
}