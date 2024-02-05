package callofproject.dev.mobile.android.view


import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun CreateButton(title : String, modifier : Modifier, onClick : () -> Unit)
{
    Button(onClick = onClick, modifier = modifier) {
        Text(text = title)
    }
}


@Composable
fun CreateOutlinedTextField(value : String, onValueChange : (String) -> Unit,
                            labelTitle : String,
                            labelColor : Color = Color.Gray,
                            modifier : Modifier,
                            visualTransformation: VisualTransformation = VisualTransformation.None,
                            keyboardType : KeyboardType = KeyboardType.Text,
                            focusedColor: Color = Color(0xFF295a8c),
                            unFocusedColor: Color = Color.Gray)
{

    OutlinedTextField(value = value, onValueChange = { onValueChange(it) },
        label = { Text(labelTitle, color = labelColor) }, modifier = modifier,
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = focusedColor, unfocusedBorderColor = unFocusedColor))
}