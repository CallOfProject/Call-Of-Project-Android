package callofproject.dev.adroid.app.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import callofproject.dev.adroid.app.MainActivity
import callofproject.dev.adroid.app.R
import callofproject.dev.adroid.app.ui.theme.CallOfProjectAndroidTheme
import callofproject.dev.adroid.app.view.util.BoxAndColumnComponent
import kotlinx.coroutines.delay


@SuppressLint("CustomSplashScreen") class SplashScreen : ComponentActivity()
{
    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContent {
            CallOfProjectAndroidTheme { // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    SplashScreenComponent(::finishSplash)
                }
            }
        }
    }

    private fun finishSplash()
    {
        Intent(this, MainActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }

    companion object
    {
        @Composable
        fun SplashScreenComponent(finishSplash : () -> Unit)
        {
            val logos = listOf(R.drawable.cop_logo_0, R.drawable.cop_logo_90, R.drawable.cop_logo_180, R.drawable.cop_logo_270, R.drawable.cop_logo_0)

            var currentLogoIndex by remember { mutableIntStateOf(0) }


            val image = painterResource(id = logos[currentLogoIndex])

            LaunchedEffect(key1 = true) {

                (1..5).forEach { _ ->
                    logos.indices.forEach { index ->
                        delay(10)
                        currentLogoIndex = index
                    }
                }
                finishSplash()
            }

            BoxAndColumnComponent {
                Image(painter = image, contentDescription = "Logo")
            }
        }
    }

}


