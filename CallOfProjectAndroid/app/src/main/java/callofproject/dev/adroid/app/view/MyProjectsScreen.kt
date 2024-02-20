package callofproject.dev.adroid.app.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import callofproject.dev.adroid.app.R
import callofproject.dev.adroid.app.ui.theme.CallOfProjectAndroidTheme
import callofproject.dev.adroid.app.util.PROJECT_OVERVIEW_PAGE

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyProjectsScreen(navController: NavController) {

    Scaffold(
        bottomBar = { BottomBarComponent(navController) },
        topBar = { TopBarComponent("My Projects") }) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .imePadding(),
            contentPadding = PaddingValues(
                horizontal = 12.dp,
                vertical = 24.dp
            )
        ) {
            items(10) {
                MyProjectCardComponent(navController)
            }
        }
    }
}

@Composable
fun MyProjectCardComponent(navController: NavController) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .clickable { navController.navigate(PROJECT_OVERVIEW_PAGE) }
            .height(200.dp)
            .padding(10.dp)
            .fillMaxWidth()
            .border(10.dp, Color.Transparent, RoundedCornerShape(1.dp))
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(
                    id =
                    R.drawable.project_icon
                ),
                contentDescription = "project icon",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )

            Text(
                text = "Project Name", fontSize = 19.sp,
                modifier = Modifier.padding(10.dp)
            )
        }

    }

}

@Preview(showBackground = true)
@Composable
fun MyProjectsScreenPreview() {
    CallOfProjectAndroidTheme {
        MyProjectsScreen(rememberNavController())
    }
}