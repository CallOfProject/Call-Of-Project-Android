package callofproject.dev.androidapp.presentation.notifications

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.presentation.user_profile.UserProfileViewModel
import callofproject.dev.androidapp.util.route.UiEvent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NotificationScreen(
    scaffoldState: SnackbarHostState,
    viewModel: UserProfileViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit,
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit
) {
    Scaffold(
        topBar = topBar,
        bottomBar = bottomBar,
        snackbarHost = { SnackbarHost(scaffoldState) }) {
        Box(
            contentAlignment = Alignment.TopCenter, modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {

                (0..10).forEach { _ ->
                    NotificationCardDialog("Ömer ERTAş senin \"Call-Of-Project\" isimli projene katılmak için istek gönderdi.")
                }
            }
        }

    }
}

@Composable
private fun NotificationCard(msg: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(15.dp), shape = RoundedCornerShape(15.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.Left,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.notification_icon),
                contentDescription = "project",
                modifier = Modifier
                    .size(60.dp)
                    .align(Alignment.CenterVertically),
                alignment = Alignment.Center
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(10.dp),
                content = {
                    Text(text = msg, style = MaterialTheme.typography.bodyMedium)
                })
        }
    }
}

@Composable
private fun NotificationCardDialog(msg: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 160.dp)
            .padding(15.dp),
        shape = RoundedCornerShape(15.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.Left,
                modifier = Modifier.fillMaxWidth().padding(10.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.notification_icon),
                    contentDescription = "project",
                    modifier = Modifier
                        .size(60.dp)
                        .align(Alignment.CenterVertically),
                    alignment = Alignment.Center
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(10.dp),
                    content = {
                        Text(text = msg, style = MaterialTheme.typography.bodyMedium)
                    })
            }


            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Button(onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.Done, contentDescription = "")
                }
                Spacer(modifier = Modifier.width(20.dp))
                Button(onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.Clear, contentDescription = "")
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun NotificationScreenPreview() {
    NotificationCardDialog(msg = "Are you sure you want to delete this project?")
}