package callofproject.dev.androidapp.presentation.user_profile.user_view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import callofproject.dev.androidapp.presentation.user_profile.UserProfileViewModel
import callofproject.dev.androidapp.presentation.user_profile.view.DownloadFileComponent
import callofproject.dev.androidapp.presentation.user_profile.view.UserAboutMeComponent
import callofproject.dev.androidapp.presentation.user_profile.view.UserCoursesComponent
import callofproject.dev.androidapp.presentation.user_profile.view.UserEducationComponent
import callofproject.dev.androidapp.presentation.user_profile.view.UserExperienceComponent
import callofproject.dev.androidapp.presentation.user_profile.view.UserLinksComponent
import callofproject.dev.androidapp.presentation.user_profile.view.UserProfileTopComponent
import callofproject.dev.androidapp.presentation.user_profile.view.UserRatingComponent

@Composable
fun UserOverviewScreen(
    userId: String,
    viewModel: UserProfileViewModel = hiltViewModel(),
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit
) {
    val state = viewModel.state

    LaunchedEffect(key1 = true) { viewModel.findUserProfileByUserId(userId) }

    Scaffold(
        topBar = topBar,
        bottomBar = bottomBar,
    )
    {
        Box(contentAlignment = Alignment.TopCenter, modifier = Modifier.fillMaxSize().padding(it))
        {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(15.dp)
            )
            {

                UserProfileTopComponent(state)
                UserRatingComponent(state)
                DownloadFileComponent(state)
                UserAboutMeComponent(state, viewModel, false)
                UserEducationComponent(state, viewModel, false)
                UserExperienceComponent(state, viewModel, false)
                UserCoursesComponent(state, viewModel, false)
                UserLinksComponent(state, viewModel, false)
            }
        }
    }
}