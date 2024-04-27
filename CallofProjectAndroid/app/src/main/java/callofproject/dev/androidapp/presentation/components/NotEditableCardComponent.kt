package callofproject.dev.androidapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NotEditableCardComponent(
    title: String,
    height: Dp,
    padVal: PaddingValues = PaddingValues(10.dp),
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(height)
        .padding(padVal)
        .border(
            BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(15.dp)
        ),
    content: @Composable (Modifier) -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            contentColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color.Transparent
        ),
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Box {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = title, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    }
                    Divider(color = MaterialTheme.colorScheme.primary, thickness = 1.dp)
                }
            }
            Column {

            }
            content(Modifier.height(IntrinsicSize.Min))
        }
    }
}