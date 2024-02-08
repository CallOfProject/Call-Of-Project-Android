package callofproject.dev.adroid.app.view.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver



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

private fun lifeCycleAction(event : Lifecycle.Event,
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
