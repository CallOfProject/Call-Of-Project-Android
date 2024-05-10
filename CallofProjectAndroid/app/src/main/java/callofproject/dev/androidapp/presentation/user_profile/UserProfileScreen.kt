package callofproject.dev.androidapp.presentation.user_profile

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
    val state = viewModel.getState()
    val context = LocalContext.current
    val expandedUpsertEducation = remember { mutableStateOf(false) }
    val expandedAddEducation = remember { mutableStateOf(false) }
    val expandedUpdateCourse = remember { mutableStateOf(false) }
    val expandedAddCourse = remember { mutableStateOf(false) }
    val expandedEditAboutMe = remember { mutableStateOf(false) }
    val expandedUpsertExperience = remember { mutableStateOf(false) }
    val expandedAddExperience = remember { mutableStateOf(false) }
    val expandedUpdateLink = remember { mutableStateOf(false) }
    val expandedAddLink = remember { mutableStateOf(false) }
    LaunchedEffect(key1 = true) {
        viewModel.getUiEvent().collect { event ->
            when (event) {
                is UiEvent.ShowToastMessageViaStatus -> {
                    Toast.makeText(context, event.message.asString(context), Toast.LENGTH_SHORT)
                        .show()

                    if (event.success) {
                        expandedUpsertEducation.value = false
                        expandedAddEducation.value = false
                        expandedUpdateCourse.value = false
                        expandedAddCourse.value = false
                        expandedEditAboutMe.value = false
                        expandedUpsertExperience.value = false
                        expandedAddExperience.value = false
                        expandedUpdateLink.value = false
                        expandedAddLink.value = false
                    }
                }

                is UiEvent.ShowToastMessage -> {
                    Toast.makeText(context, event.message.asString(context), Toast.LENGTH_SHORT)
                        .show()
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
                UserAboutMeComponent(
                    state,
                    viewModel,
                    expandedUpsertAboutMe = expandedEditAboutMe
                )
                UserEducationComponent(
                    state,
                    viewModel,
                    expandedUpsertEducation = expandedUpsertEducation,
                    expandedAddEducation = expandedAddEducation
                )
                UserExperienceComponent(
                    state,
                    viewModel,
                    expandedUpsertExperience = expandedUpsertExperience,
                    expandedAddExperience = expandedAddExperience
                )
                UserCoursesComponent(
                    state, viewModel,
                    expandedUpdateCourse = expandedUpdateCourse,
                    expandedAddCourse = expandedAddCourse
                )
                UserLinksComponent(
                    state,
                    viewModel,
                    expandedUpdateLink = expandedUpdateLink,
                    expandedAddLink = expandedAddLink
                )
                UserTagsComponent(state, viewModel)
            }
        }
    }
}



