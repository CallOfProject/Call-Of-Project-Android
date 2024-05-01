package callofproject.dev.androidapp.presentation.user_profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.presentation.components.EditableCardComponent
import callofproject.dev.androidapp.presentation.components.NotEditableCardComponent
import callofproject.dev.androidapp.presentation.components.TagComponent
import callofproject.dev.androidapp.presentation.user_profile.view.UploadFileComponent
import callofproject.dev.androidapp.presentation.user_profile.view.UserAboutMeComponent
import callofproject.dev.androidapp.presentation.user_profile.view.UserCoursesComponent
import callofproject.dev.androidapp.presentation.user_profile.view.UserEducationComponent
import callofproject.dev.androidapp.presentation.user_profile.view.UserExperienceComponent
import callofproject.dev.androidapp.presentation.user_profile.view.UserLinksComponent
import callofproject.dev.androidapp.presentation.user_profile.view.UserProfileTopComponent
import callofproject.dev.androidapp.presentation.user_profile.view.UserRatingComponent
import callofproject.dev.androidapp.presentation.user_profile.view.UserTagsComponent
import callofproject.dev.androidapp.util.route.UiEvent

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
                    scaffoldState.showSnackbar(
                        withDismissAction = true,
                        message = event.msg.asString(context),
                        duration = SnackbarDuration.Short
                    )
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
                //UserTagsComponent(state, viewModel)
            }
        }
    }
}



