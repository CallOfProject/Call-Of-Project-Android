package callofproject.dev.android.authentication.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

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
