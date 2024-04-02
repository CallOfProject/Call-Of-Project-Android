package callofproject.dev.adroid.app.view.util

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun NormalTextField(
    text: String,
    value: String,
    onValueChange: (String) -> Unit,
    focusedBorderColor: Long = 0xFF295a8c,
    unFocusedBorderColor: Color = Color.Gray,
    modifier: Modifier = Modifier
        .width(300.dp)
        .padding(bottom = 8.dp),
    textStyle: TextStyle = TextStyle.Default,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        label = { Text(text, color = Color.Gray) },
        modifier = modifier,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(focusedBorderColor),
            unfocusedBorderColor = unFocusedBorderColor
        ),
        textStyle = textStyle
    )
}

@Composable
fun PasswordTextField(
    text: String,
    value: String,
    onValueChange: (String) -> Unit,
    focusedBorderColor: Long = 0xFF295a8c,
    textStyle: TextStyle = TextStyle.Default,
    unFocusedBorderColor: Color = Color.Gray,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        label = { Text(text, color = Color.Gray) },
        modifier = Modifier
            .width(300.dp)
            .padding(bottom = 8.dp),
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(focusedBorderColor),
            unfocusedBorderColor = unFocusedBorderColor
        ),
        textStyle = textStyle,
        leadingIcon = leadingIcon
    )
}


@Composable
fun BoxAndColumnComponent(
    alignment: Alignment = Alignment.Center,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(contentAlignment = alignment, modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            content()
        }
    }
}


@Composable
fun EditableCardComponent(
    title: String,
    height: Dp = 200.dp,
    imageVector: ImageVector = Icons.Filled.Edit,
    imageDescription: String = "Edit",
    padVal: PaddingValues = PaddingValues(20.dp),
    onIconClick: (context: Context) -> Unit = {},
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    Card(
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
            Column(modifier = Modifier.padding(10.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = title, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                }
                content()
            }
            IconButton(
                onClick = {
                    onIconClick(context)
                }, modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(10.dp)
                    .size(24.dp)
            ) {
                Icon(imageVector, contentDescription = imageDescription)
            }
        }
    }

}


@Composable
fun NotEditableCardComponent(
    title: String,
    height: Dp,
    padVal: PaddingValues = PaddingValues(20.dp),
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(height)
        .padding(padVal)
        .border(
            BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(15.dp)
        ),
    content: @Composable () -> Unit
) {
    Card(modifier = modifier) {
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
            LazyColumn(modifier = Modifier.padding(top = 10.dp)) {
                item {
                    content()
                }
            }
        }
    }
}



