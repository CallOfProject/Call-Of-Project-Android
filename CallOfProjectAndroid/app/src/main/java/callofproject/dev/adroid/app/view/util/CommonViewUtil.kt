package callofproject.dev.adroid.app.view.util

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver


@Composable
fun NormalTextField(text : String,
                    value : String,
                    onValueChange : (String) -> Unit,
                    focusedBorderColor : Long = 0xFF295a8c,
                    unFocusedBorderColor : Color = Color.Gray,
                    keyboardType : KeyboardType = KeyboardType.Text)
{
    OutlinedTextField(value = value, onValueChange = { onValueChange(it) }, label = { Text(text, color = Color.Gray) }, modifier = Modifier
        .width(300.dp)
        .padding(bottom = 8.dp), keyboardOptions = KeyboardOptions(keyboardType = keyboardType), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(focusedBorderColor), unfocusedBorderColor = unFocusedBorderColor))
}

@Composable
fun PasswordTextField(text : String,
                      value : String,
                      onValueChange : (String) -> Unit,
                      focusedBorderColor : Long = 0xFF295a8c,
                      unFocusedBorderColor : Color = Color.Gray)
{
    OutlinedTextField(value = value, onValueChange = { onValueChange(it) }, label = { Text(text, color = Color.Gray) }, modifier = Modifier
        .width(300.dp)
        .padding(bottom = 8.dp), visualTransformation = PasswordVisualTransformation(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(focusedBorderColor), unfocusedBorderColor = unFocusedBorderColor))
}


@Composable
fun BoxAndColumnComponent(alignment : Alignment = Alignment.Center,
                          content : @Composable ColumnScope.() -> Unit)
{
    Box(contentAlignment = alignment, modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())) {
            content()
        }
    }
}


@Composable
fun BoxAndColumnComponentWithModifier(content : @Composable ColumnScope.() -> Unit,
                                      modifier : Modifier)
{
    Box(contentAlignment = Alignment.Center, modifier = modifier) {
        Column(modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())) {
            content()
        }
    }
}


fun lifeCycleAction(event : Lifecycle.Event,
                    onStart : () -> Unit,
                    onStop : () -> Unit,
                    onCreate : () -> Unit,
                    onResume : () -> Unit,
                    onPause : () -> Unit,
                    onDestroy : () -> Unit)
{
    when (event)
    {
        Lifecycle.Event.ON_CREATE  -> onCreate()
        Lifecycle.Event.ON_START   -> onStart()
        Lifecycle.Event.ON_RESUME  -> onResume()
        Lifecycle.Event.ON_PAUSE   -> onPause()
        Lifecycle.Event.ON_STOP    -> onStop()
        Lifecycle.Event.ON_DESTROY -> onDestroy()

        else                       -> throw UnsupportedOperationException("Unsupported lifecycle event")
    }

}

@Composable
fun LifeCycleObserver(onStart : () -> Unit = {},
                      onStop : () -> Unit = {},
                      onCreate : () -> Unit = {},
                      onResume : () -> Unit = {},
                      onPause : () -> Unit = {},
                      onDestroy : () -> Unit = {})
{
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifeCycleAction(event, onStart, onStop, onCreate, onResume, onPause, onDestroy)
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }
}
