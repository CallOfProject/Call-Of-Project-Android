package callofproject.dev.androidapp.presentation.components

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import callofproject.dev.androidapp.R

@Composable
fun EditableCardComponent(
    title: String,
    height: Dp = 200.dp,
    imageVector: ImageVector = Icons.Filled.Edit,
    imageDescription: String = "Edit",
    padVal: PaddingValues = PaddingValues(10.dp),
    onIconClick: (context: Context) -> Unit = {},
    removable: Boolean = true,
    onRemoveClick: () -> Unit = {},
    isEditable: Boolean = true,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    Card(
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            contentColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .padding(padVal)
            .border(
                BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(15.dp)
            )
    ) {

        Box {
            Column(
                modifier = Modifier
                    .padding(5.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    Text(
                        text = title,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                content()
            }
            if (isEditable) {
                Row(
                    horizontalArrangement = Arrangement.Absolute.Right,
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    if (removable)
                        IconButton(
                            onClick = { onRemoveClick() },
                            modifier = Modifier
                                .align(Alignment.Top)
                                .padding(5.dp)
                                .size(23.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.trash),
                                contentDescription = imageDescription
                            )
                        }

                    IconButton(
                        onClick = {
                            onIconClick(context)
                        }, modifier = Modifier
                            .align(Alignment.Top)
                            .padding(5.dp)
                            .size(23.dp)
                    ) {
                        Icon(imageVector, contentDescription = imageDescription)
                    }

                }


            }

        }
    }

}
