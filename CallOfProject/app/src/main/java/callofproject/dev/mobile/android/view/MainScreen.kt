package callofproject.dev.mobile.android.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import callofproject.dev.mobile.android.R
import callofproject.dev.mobile.android.ui.theme.LightColorScheme


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navController : NavController)
{
    val items = listOf("Home", "Notification", "Project", "Profile")
    val selectedItem = remember { mutableIntStateOf(0) }
    val isSearching = remember { mutableStateOf(false) }
    val textField = remember { mutableStateOf("") }
    val randomText =
        remember { mutableStateOf("Burada uzun bir açıklama metni olacak.Burada uzun bir açıklama metni olacak.Burada uzun bir açıklama metni olacak." + "Burada uzun bir açıklama metni olacak.Burada uzun bir açıklama metni olacak.Burada uzun bir açıklama metni olacak.Burada uzun bir açıklama metni olacak." + "Burada uzun bir açıklama metni olacak.Burada uzun bir açıklama metni olacak.Burada uzun bir açıklama metni olacak.Burada uzun bir açıklama metni olacak." + "Burada uzun bir açıklama metni olacak.Burada uzun bir açıklama metni olacak.Burada uzun bir açıklama metni olacak.Burada uzun bir açıklama metni olacak." + "Burada uzun bir açıklama metni olacak.Burada uzun bir açıklama metni olacak.Burada uzun bir açıklama metni olacak.Burada uzun bir açıklama metni olacak.") }

    Scaffold(topBar = {
        TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ), title = {
            if (isSearching.value)
            {
                TextField(value = textField.value, onValueChange = {
                    textField.value = it
                }, modifier = Modifier.fillMaxWidth(), textStyle = MaterialTheme.typography.bodyMedium, placeholder = {
                    Text("Search", style = MaterialTheme.typography.bodyMedium)
                }, shape = RectangleShape, colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.primary, unfocusedBorderColor = MaterialTheme.colorScheme.primary))

            }
        }, navigationIcon = {
            IconButton(onClick = { /* do something */ }) {
                Icon(imageVector = Icons.Rounded.Menu, contentDescription = "Localized description")
            }
        }, actions = {
            Row {
                if (isSearching.value)
                {
                    IconButton(onClick = { isSearching.value = false }) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = "Localized description")
                    }
                } else IconButton(onClick = { isSearching.value = true }) {
                    Image(painter = painterResource(id = R.drawable.search_icon), contentDescription = "Notifications", modifier = Modifier.size(30.dp))
                }
            }
        })
    }, bottomBar = {
        NavigationBar(modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)) {
            items.forEachIndexed { index, item ->
                run {
                    NavigationBarItem(selected = selectedItem.intValue == index, onClick = {
                        selectedItem.intValue = index
                    }, label = { Text(text = item) }, icon = {
                        when (item)
                        {
                            "Home" -> Image(painter = painterResource(id = R.drawable.home_icon), contentDescription = "Home", modifier = Modifier.size(24.dp))
                            "Profile" -> Image(painter = painterResource(id = R.drawable.account), contentDescription = "Profile", modifier = Modifier.size(24.dp))
                            "Project" -> Image(painter = painterResource(id = R.drawable.project_icon), contentDescription = "Project", modifier = Modifier.size(24.dp))
                            "Notification" -> Image(painter = painterResource(id = R.drawable.notification_icon), contentDescription = "Notifications", modifier = Modifier.size(24.dp))
                        }
                    })
                }
            }
        }
    }, content = { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
            .fillMaxSize()
            .verticalScroll(ScrollState(0)), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
            for (i in 0 until 10) ProjectOverviewCardComponent(randomText)

        }
    })
}

@Composable
fun ProjectOverviewCardComponent(randomText : MutableState<String>)
{
    Card(modifier = Modifier.padding(bottom = 20.dp), shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.outlinedCardColors(LightColorScheme.secondary)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(id = R.drawable.cop_logo), contentDescription = "", Modifier
                .width(100.dp)
                .height(100.dp))
            Column(modifier = Modifier.padding(start = 10.dp)) {
                Text("Call-Of-Project - [nuricanozturk]", style = MaterialTheme.typography.labelLarge, modifier = Modifier
                    .padding(bottom = 5.dp)
                    .fillMaxWidth(), textAlign = TextAlign.Center)

                Text(if (randomText.value.length > 70) randomText.value.substring(0, 70) + "..." else randomText.value, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}