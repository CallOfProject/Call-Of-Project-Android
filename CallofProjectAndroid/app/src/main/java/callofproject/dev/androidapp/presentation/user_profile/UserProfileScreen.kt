package callofproject.dev.androidapp.presentation.user_profile

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.domain.dto.user_profile.course.CourseDTO
import callofproject.dev.androidapp.domain.dto.user_profile.education.EducationDTO
import callofproject.dev.androidapp.presentation.components.EditableCardComponent
import callofproject.dev.androidapp.presentation.components.NotEditableCardComponent
import callofproject.dev.androidapp.presentation.user_profile.edit.UserAboutMeEditComponent
import callofproject.dev.androidapp.presentation.user_profile.edit.UserCourseEditComponent
import callofproject.dev.androidapp.presentation.user_profile.edit.UserEducationEditComponent
import callofproject.dev.androidapp.presentation.user_profile.edit.UserExperienceEditComponent
import callofproject.dev.androidapp.presentation.user_profile.edit.UserLinkEditComponent
import callofproject.dev.androidapp.util.route.UiEvent
import coil.compose.rememberAsyncImagePainter

@Composable
fun UserProfileScreen(
    userId: String,
    scaffoldState: SnackbarHostState,
    viewModel: UserProfileViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit,
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit
) {
    val state = viewModel.state

    LaunchedEffect(key1 = true) { viewModel.findUserProfileByUserId(userId) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        topBar = topBar,
        bottomBar = bottomBar,
        snackbarHost = { SnackbarHost(scaffoldState) }) {
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        )
        {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
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
private fun UserEducationComponent(state: UserProfileState, viewModel: UserProfileViewModel) {
    var expandedUpsertEducation by remember { mutableStateOf(false) }
    var expandedAddEducation by remember { mutableStateOf(false) }
    var selectedEducationIndex by remember { mutableIntStateOf(-1) }

    EditableCardComponent(
        "Education",
        400.dp,
        imageVector = Icons.Filled.Add,
        imageDescription = "Add",
        onIconClick = { expandedAddEducation = true }
    ) {
        LazyColumn {

            items(state.userProfileDTO.profile.educations.size) { idx ->
                EditableCardComponent(
                    title = state.userProfileDTO.profile.educations[idx].schoolName,
                    onIconClick = { expandedUpsertEducation = true; selectedEducationIndex = idx }
                ) {
                    EducationDetails(state.userProfileDTO.profile.educations[idx])
                }
            }
        }
    }

    if (expandedUpsertEducation) {
        UserEducationEditComponent(
            onDismissRequest = { expandedUpsertEducation = false },
            educationDTO = state.userProfileDTO.profile.educations[selectedEducationIndex],
            confirmEvent = { viewModel.onEvent(UserProfileEvent.OnUpdateEducation(it)) }
        )
    }

    if (expandedAddEducation) {
        UserEducationEditComponent(
            onDismissRequest = { expandedAddEducation = false },
            educationDTO = EducationDTO(),
            confirmEvent = { viewModel.onEvent(UserProfileEvent.OnCreateEducation(it)) }
        )
    }
}

@Composable
private fun EducationDetails(education: EducationDTO) {
    Text(
        text = "Department: ${education.department}",
        fontSize = 15.sp,
        fontWeight = FontWeight.Normal,
        modifier = Modifier.padding(5.dp)
    )
    Text(
        text = "Date: ${education.startDate} - ${education.finishDate}",
        fontSize = 15.sp,
        fontWeight = FontWeight.Normal,
        modifier = Modifier.padding(5.dp)
    )
    Text(
        text = "GPA: ${education.gpa}",
        fontSize = 15.sp,
        fontWeight = FontWeight.Normal,
        modifier = Modifier.padding(5.dp)
    )
}

@Composable
private fun UserExperienceComponent(state: UserProfileState, viewModel: UserProfileViewModel) {
    var expandedUpsertExperience by remember { mutableStateOf(false) }
    var expandedAddExperience by remember { mutableStateOf(false) }
    var selectedExperienceIndex by remember { mutableIntStateOf(-1) }

    EditableCardComponent("Experience",
        400.dp,
        imageVector = Icons.Filled.Add,
        imageDescription = "Add",
        onIconClick = { expandedAddExperience = true }) {
        LazyColumn {
            items(state.userProfileDTO.profile.experiences.size) { index ->
                val experience = state.userProfileDTO.profile.experiences[index]
                EditableCardComponent(
                    title = experience.companyName,
                    onIconClick = {
                        expandedUpsertExperience = true; selectedExperienceIndex = index
                    }) {
                    Text(
                        text = "Position: ${experience.position}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(5.dp)
                    )
                    Text(
                        text = "Date: ${experience.startDate} - ${experience.finishDate}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(5.dp)
                    )
                    Text(
                        text = "Description: ${experience.description}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(5.dp)
                    )
                }
            }
        }
    }

    if (expandedUpsertExperience) {
        UserExperienceEditComponent(
            onDismissRequest = { expandedUpsertExperience = false },
            experienceDTO = state.userProfileDTO.profile.experiences[selectedExperienceIndex],
            confirmEvent = { viewModel.onEvent(UserProfileEvent.OnUpdateExperience(it)) }
        )
    }

    if (expandedAddExperience) {
        UserExperienceEditComponent(
            onDismissRequest = { expandedAddExperience = false },
            confirmEvent = { viewModel.onEvent(UserProfileEvent.OnCreateExperience(it)) }
        )
    }
}


@Composable
private fun UserCoursesComponent(state: UserProfileState, viewModel: UserProfileViewModel) {
    var expandedUpdateCourse by remember { mutableStateOf(false) }
    var expandedAddCourse by remember { mutableStateOf(false) }
    var selectedCourseIndex by remember { mutableIntStateOf(-1) }

    EditableCardComponent("Courses",
        490.dp,
        imageVector = Icons.Filled.Add,
        imageDescription = "Add",
        onIconClick = { expandedAddCourse = true }) {
        LazyColumn {
            items(state.userProfileDTO.profile.courses.size) { index ->
                val course = state.userProfileDTO.profile.courses[index]
                EditableCardComponent(
                    title = course.courseName,
                    onIconClick = {
                        expandedUpdateCourse = true;
                        selectedCourseIndex = index
                    }) {
                    Text(
                        text = "Organizator: ${course.organization}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(5.dp)
                    )
                    Text(
                        text = "Date: ${course.startDate} - ${course.finishDate}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(5.dp)
                    )
                    Text(
                        text = "Description: ${course.description}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(5.dp)
                    )
                }
            }
        }
    }

    if (expandedUpdateCourse) {
        UserCourseEditComponent(
            onDismissRequest = { expandedUpdateCourse = false },
            courseDTO = state.userProfileDTO.profile.courses[selectedCourseIndex],
            confirmEvent = { viewModel.onEvent(UserProfileEvent.OnUpdateCourse(it)) }
        )
    }

    if (expandedAddCourse) {
        UserCourseEditComponent(
            onDismissRequest = { expandedAddCourse = false },
            confirmEvent = { viewModel.onEvent(UserProfileEvent.OnCreateCourse(it)) }
        )
    }
}


@Composable
private fun UserLinksComponent(state: UserProfileState, viewModel: UserProfileViewModel) {
    val context = LocalContext.current
    var expandedUpdateLink by remember { mutableStateOf(false) }
    var expandedAddLink by remember { mutableStateOf(false) }
    var selectedLinkIndex by remember { mutableIntStateOf(-1) }

    EditableCardComponent(
        title = "Links",
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
                    onIconClick = { expandedUpdateLink = true; selectedLinkIndex = index }) {
                    Text(
                        text = link.link,
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier
                            .padding(5.dp)
                            .clickable {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link.link))
                                startActivity(context, intent, null)
                            }
                    )
                }
            }
        }
    }

    if (expandedUpdateLink) {
        UserLinkEditComponent(
            onDismissRequest = { expandedUpdateLink = false },
            linkDTO = state.userProfileDTO.profile.links[selectedLinkIndex],
            confirmEvent = { viewModel.onEvent(UserProfileEvent.OnUpdateLink(it)) }
        )
    }

    if (expandedAddLink) {
        UserLinkEditComponent(
            onDismissRequest = { expandedAddLink = false },
            confirmEvent = { viewModel.onEvent(UserProfileEvent.OnCreateLink(it)) }
        )
    }
}

@Composable
private fun UserRatingComponent(state: UserProfileState, viewModel: UserProfileViewModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(0.2f)
        ) {
            Text(text = "User", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.star_icon),
                    contentDescription = ""
                )
                Text(
                    text = state.userProfileDTO.profile.userRate.toString(),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(0.4f)
        ) {
            Text(text = "Feedback", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.star_icon),
                    contentDescription = ""
                )
                Text(
                    text = state.userProfileDTO.profile.userFeedbackRate.toString(),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal
                )
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
        contentDescription = "Logo",
        modifier = Modifier
            .size(150.dp)
            .clip(CircleShape)
    )
    Text(
        text = state.userProfileDTO.user.username,
        fontSize = 20.sp,
        fontWeight = FontWeight(700),
        fontStyle = FontStyle.Normal
    )
}

@Composable
private fun UserAboutMeComponent(state: UserProfileState, viewModel: UserProfileViewModel) {
    var expandedUpsertAboutMe by remember { mutableStateOf(false) }
    EditableCardComponent(
        title = "About me",
        onIconClick = { expandedUpsertAboutMe = true }) {
        Text(
            text = state.userProfileDTO.profile.aboutMe ?: "",
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(5.dp)
        )
    }

    if (expandedUpsertAboutMe) {
        UserAboutMeEditComponent(
            onDismissRequest = { expandedUpsertAboutMe = false },
            defaultAboutMe = state.userProfileDTO.profile.aboutMe ?: "",
            confirmEvent = { viewModel.onEvent(UserProfileEvent.OnUpdateAboutMe(it)) }
        )
    }
}

