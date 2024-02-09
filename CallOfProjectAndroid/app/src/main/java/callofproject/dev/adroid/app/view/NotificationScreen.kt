package callofproject.dev.adroid.app.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import callofproject.dev.adroid.app.R
import callofproject.dev.adroid.app.ui.theme.CallOfProjectAndroidTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NotificationScreen(navController : NavController)
{
    Scaffold(topBar = { TopBarComponent() }, bottomBar = { BottomBarComponent(navController) }) {
        Box(contentAlignment = Alignment.TopCenter, modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.verticalScroll(rememberScrollState())) {

                (0..10).forEach { _ ->
                    NotificationCard("Ömer ERTAş senin \"Call-Of-Project\" isimli projene katılmak için istek gönderdi.")
                }
            }
        }

    }
}


@Composable
fun NotificationCard(msg : String)
{
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(120.dp)
        .padding(15.dp), shape = RoundedCornerShape(15.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Absolute.Left, modifier = Modifier.fillMaxSize()) {
            Image(painter = painterResource(id = R.drawable.notification_icon), contentDescription = "project", modifier = Modifier
                .size(60.dp)
                .align(Alignment.CenterVertically), alignment = Alignment.Center)

            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(10.dp), content = {
                Text(text = msg, style = MaterialTheme.typography.bodyMedium)
            })
        }
    }
}


@Preview(showBackground = true)
@Composable
fun NotificationScreenPreview()
{
    CallOfProjectAndroidTheme {
        NotificationScreen(rememberNavController())
    }
}