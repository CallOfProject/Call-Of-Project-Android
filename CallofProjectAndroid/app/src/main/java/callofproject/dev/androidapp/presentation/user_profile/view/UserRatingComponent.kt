package callofproject.dev.androidapp.presentation.user_profile.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.R.string.title_feedback
import callofproject.dev.androidapp.presentation.user_profile.UserProfileState

@Composable
fun UserRatingComponent(state: UserProfileState) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(0.2f)
        ) {

            Text(
                text = stringResource(R.string.title_user),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            )
            {
                Icon(
                    painter = painterResource(id = R.drawable.star_icon),
                    contentDescription = stringResource(R.string.default_image_description)
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

            Text(
                text = stringResource(title_feedback),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth())
            {
                Icon(
                    painter = painterResource(id = R.drawable.star_icon),
                    contentDescription = stringResource(R.string.default_image_description)
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