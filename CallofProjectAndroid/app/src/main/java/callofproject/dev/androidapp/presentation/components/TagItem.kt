package callofproject.dev.androidapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.ui.theme.isLight

@Composable
fun TagItem(text: String, isRemovable: Boolean = true, onClickRemove: () -> Unit = { }) {
    Row(
        modifier = Modifier
            .padding(top = 20.dp)
            .padding(horizontal = 10.dp)
            .clip(RoundedCornerShape(13.dp))
            .background(if (MaterialTheme.colorScheme.isLight()) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.tertiaryContainer)
            .padding(horizontal = 5.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Text(text = text, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight(600))

        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
        if (isRemovable)
            Icon(
                Icons.Filled.Close,
                contentDescription = stringResource(R.string.remove_tag),
                tint = Color.White,
                modifier = Modifier
                    .size(15.dp)
                    .clickable { onClickRemove() }
            )
    }
}