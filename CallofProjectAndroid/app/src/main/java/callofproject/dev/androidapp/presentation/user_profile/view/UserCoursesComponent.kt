package callofproject.dev.androidapp.presentation.user_profile.view

import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.LENGTH_SHORT
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.domain.dto.user_profile.course.CourseDTO
import callofproject.dev.androidapp.presentation.components.EditableCardComponent
import callofproject.dev.androidapp.presentation.user_profile.UserProfileEvent
import callofproject.dev.androidapp.presentation.user_profile.UserProfileState
import callofproject.dev.androidapp.presentation.user_profile.UserProfileViewModel
import callofproject.dev.androidapp.presentation.user_profile.edit.UserCourseEditComponent
import callofproject.dev.androidapp.util.route.UiEvent

@Composable
fun UserCoursesComponent(
    state: UserProfileState,
    viewModel: UserProfileViewModel,
    isEditable: Boolean = true,
    expandedUpdateCourse: MutableState<Boolean> = mutableStateOf(false),
    expandedAddCourse: MutableState<Boolean> = mutableStateOf(false),
) {
    var selectedCourseIndex by remember { mutableIntStateOf(-1) }



    EditableCardComponent("Courses",
        500.dp,
        imageVector = Icons.Filled.Add,
        imageDescription = "Add",
        isEditable = isEditable,
        removable = false,
        onIconClick = { expandedAddCourse.value = true })
    {
        LazyColumn {

            items(state.userProfileDTO.profile.courses.size) { index ->

                val course = state.userProfileDTO.profile.courses[index]

                EditableCardComponent(
                    title = course.courseName,
                    isEditable = isEditable,
                    onRemoveClick = { viewModel.onEvent(UserProfileEvent.OnDeleteCourse(course.courseId)) },
                    onIconClick = {
                        expandedUpdateCourse.value = true
                        selectedCourseIndex = index
                    },
                    height = 250.dp
                )
                {
                    CourseDetails(course)
                }
            }
        }
    }

    if (expandedUpdateCourse.value)
        UserCourseEditComponent(
            onDismissRequest = { expandedUpdateCourse.value = false },
            courseDTO = state.userProfileDTO.profile.courses[selectedCourseIndex],
            confirmEvent = { viewModel.onEvent(UserProfileEvent.OnUpdateCourse(it)) }
        )

    if (expandedAddCourse.value)
        UserCourseEditComponent(
            onDismissRequest = { expandedAddCourse.value = false },
            confirmEvent = { viewModel.onEvent(UserProfileEvent.OnCreateCourse(it)) }
        )
}


@Composable
private fun CourseDetails(course: CourseDTO) {
    Column(

    ) {
        Row {
            Text(
                text = stringResource(R.string.title_organizator),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = course.organization,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
            )
        }


        Row {
            Text(
                text = stringResource(R.string.title_date),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = "${course.startDate} - ${course.finishDate}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
            )
        }


        Column {
            Text(
                text = stringResource(R.string.title_description),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,

                )

            Text(
                text = course.description,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                overflow = TextOverflow.Ellipsis,

                )
        }
    }
}