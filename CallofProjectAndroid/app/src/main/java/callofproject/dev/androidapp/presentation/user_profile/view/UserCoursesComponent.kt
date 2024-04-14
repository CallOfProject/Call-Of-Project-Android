package callofproject.dev.androidapp.presentation.user_profile.view

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import callofproject.dev.androidapp.presentation.components.EditableCardComponent
import callofproject.dev.androidapp.presentation.user_profile.UserProfileEvent
import callofproject.dev.androidapp.presentation.user_profile.UserProfileState
import callofproject.dev.androidapp.presentation.user_profile.UserProfileViewModel
import callofproject.dev.androidapp.presentation.user_profile.edit.UserCourseEditComponent

@Composable
fun UserCoursesComponent(state: UserProfileState, viewModel: UserProfileViewModel) {
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