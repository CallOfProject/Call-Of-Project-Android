package callofproject.dev.androidapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RowBasedCardComponent(title: String, value: String) {
    Card(
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .border(
                BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(5.dp)
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "-${title}:",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(5.dp)
            )
            Text(
                text = value,
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}
