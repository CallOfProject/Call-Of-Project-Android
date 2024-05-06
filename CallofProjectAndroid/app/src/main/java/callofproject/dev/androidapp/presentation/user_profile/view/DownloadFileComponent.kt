package callofproject.dev.androidapp.presentation.user_profile.view

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.presentation.user_profile.UserProfileState

@Composable
fun DownloadFileComponent(
    state: UserProfileState
) {
    val context = LocalContext.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {

        Button(onClick = {
            val cvUrl = state.userProfileDTO.profile.cv
            if (cvUrl != null) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(cvUrl))
                context.startActivity(intent)
            }
        }) {
            Icon(
                painter = painterResource(id = R.drawable.download_24),
                contentDescription = stringResource(R.string.default_image_description),
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.size(5.dp))
            Text(text = stringResource(R.string.cv), fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
    }
}