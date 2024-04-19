package callofproject.dev.androidapp.presentation.user_profile.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.presentation.user_profile.UserProfileState
import coil.compose.rememberAsyncImagePainter

@SuppressLint("Range")
@Composable
fun UserProfileTopComponent(state: UserProfileState) {
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