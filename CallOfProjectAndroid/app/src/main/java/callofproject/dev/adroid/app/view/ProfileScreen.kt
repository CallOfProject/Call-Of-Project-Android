package callofproject.dev.adroid.app.view

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import callofproject.dev.adroid.app.R
import callofproject.dev.adroid.app.ui.theme.CallOfProjectAndroidTheme

class ProfileScreen : ComponentActivity()
{

    // Profile screen
    companion object
    {
        @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
        @Composable
        fun ProfileScreenComponent(navController : NavController)
        {
            Scaffold(topBar = topBarComponent(), bottomBar = bottomBarComponent(navController)) {
                Box(contentAlignment = Alignment.TopCenter, modifier = Modifier
                    .fillMaxSize()
                    .padding(it)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.verticalScroll(rememberScrollState())) {
                        Image(painter = painterResource(id = R.drawable.account), contentDescription = "Logo", Modifier
                            .width(200.dp)
                            .height(150.dp))
                        Text(text = "Nuri Can ÖZTÜRK", fontSize = 20.sp, fontWeight = FontWeight(700), fontStyle = FontStyle.Normal)
                        Text(text = "Software Engineering Student", fontSize = 15.sp, fontWeight = FontWeight.Normal)
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)) {

                            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth(0.2f)) {
                                Text(text = "User", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                                    Icon(painter = painterResource(id = R.drawable.star_icon), contentDescription = "")
                                    Text(text = "4.5", fontSize = 15.sp, fontWeight = FontWeight.Normal)
                                }
                            }

                            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth(0.4f)) {
                                Text(text = "Feedback", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                                    Icon(painter = painterResource(id = R.drawable.star_icon), contentDescription = "")
                                    Text(text = "4.5", fontSize = 15.sp, fontWeight = FontWeight.Normal)
                                }
                            }
                        }

                        EditableCardComponent(title = "About me") {
                            Text(text = "I am final year Software Engineering student.I am interesting with Backend and Android development", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                        }

                        NotEditableCardComponent("Education", 400.dp) {
                            EditableCardComponent(title = "Yasar University") {
                                Text(text = "Bölüm: Yazılım Mühendisliği", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                                Text(text = "Tarih: 2018-2024", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                                Text(text = "GPA: 2.95", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                            }
                        }


                        NotEditableCardComponent("Experience", 400.dp) {
                            EditableCardComponent(title = "") {
                                Text(text = "Firma: Kafein Technology", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                                Text(text = "Pozisyon: Backend Developer", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                                Text(text = "Tarih: 2021-2022", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                            }
                        }

                        NotEditableCardComponent("Courses", 400.dp) {
                            EditableCardComponent(title = "İleri Java") {
                                Text(text = "Firma: CSD", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                                Text(text = "Tarih: 2020-2023", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                                Text(text = "Açıklama: Java öğrendik", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                            }

                            EditableCardComponent(title = "Temel Java") {
                                Text(text = "Firma: CSD", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                                Text(text = "Tarih: 2020-2023", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                                Text(text = "Açıklama: Java öğrendik", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                            }
                        }

                        NotEditableCardComponent("Projects", 400.dp) {
                            EditableCardComponent(title = "Project - 1") {
                                Text(text = "Açıklama: ", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                                Text(text = "Summary: Backend Developer", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                                Text(text = "Link: 2021-2022", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                            }

                            EditableCardComponent(title = "Project - 2") {
                                Text(text = "Açıklama: ", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                                Text(text = "Summary: Backend Developer", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                                Text(text = "Link: 2021-2022", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                            }
                        }

                        NotEditableCardComponent("Links", 400.dp) {
                            EditableCardComponent(title = "Github", height = 150.dp) {
                                Text(text = "https://www.github.com/nuricanozturk01", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                            }

                            EditableCardComponent(title = "Linkedln", height = 150.dp) {
                                Text(text = "https://www.linkedln.com/nuricanozturk", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                            }

                            EditableCardComponent(title = "Medium", height = 150.dp) {
                                Text(text = "", fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(5.dp))
                            }
                        }
                    }

                }
            }

        }


        @Composable
        fun EditableCardComponent(title : String,
                                  height : Dp = 200.dp,
                                  content : @Composable () -> Unit)
        {
            Card(modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .padding(20.dp)
                .border(BorderStroke(1.dp, MaterialTheme.colorScheme.primary), shape = RoundedCornerShape(15.dp))) {
                Box {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                            Text(text = title, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        }
                        content()
                    }
                    IconButton(onClick = {

                    }, modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(10.dp)
                        .size(24.dp)) {
                        Icon(Icons.Filled.Edit, contentDescription = "Edit")
                    }
                }
            }

        }


        @Composable
        fun NotEditableCardComponent(title : String, height : Dp, content : @Composable () -> Unit)
        {
            Card(modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .padding(20.dp)
                .border(BorderStroke(1.dp, MaterialTheme.colorScheme.primary), shape = RoundedCornerShape(15.dp))) {
                Column(modifier = Modifier

                    .padding(10.dp)
                    .height(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                        Text(text = title, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Column(modifier = Modifier
                    .padding(10.dp)
                    .verticalScroll(rememberScrollState())) {
                    content()
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview()
{
    CallOfProjectAndroidTheme {
        ProfileScreen.ProfileScreenComponent(rememberNavController())
    }
}
