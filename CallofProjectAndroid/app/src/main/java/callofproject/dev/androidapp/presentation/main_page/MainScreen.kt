package callofproject.dev.androidapp.presentation.main_page


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import callofproject.dev.androidapp.presentation.components.LoadingComponent
import callofproject.dev.androidapp.util.route.UiEvent
import coil.compose.rememberAsyncImagePainter

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    scaffoldState: SnackbarHostState,
    viewModel: MainPageViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit,
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit
) {
    val state = viewModel.state
    val context = androidx.compose.ui.platform.LocalContext.current

    LaunchedEffect(key1 = true) {

    }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)

                is UiEvent.ShowSnackbar -> {
                    scaffoldState.showSnackbar(message = event.msg.asString(context))
                }

                else -> Unit
            }
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { topBar() },
        bottomBar = { bottomBar() },
        snackbarHost = { SnackbarHost(scaffoldState) }) { it ->
        if (state.isLoading)
            LoadingComponent(it)
        else LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(state.projectDiscoveryList.size) {
                Card(
                    colors = CardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                        contentColor = MaterialTheme.colorScheme.onSurface,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp), shape = RoundedCornerShape(10.dp)
                ) {
                    Row(
                        modifier = Modifier.clickable {
                            viewModel.onEvent(MainPageEvent.OnClickProjectDiscoveryItem(state.projectDiscoveryList[it].projectId.toString()))
                        },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,

                        content = {
                            Image(
                                painter = rememberAsyncImagePainter(state.projectDiscoveryList[it].projectImagePath),
                                contentDescription = "project",
                                modifier = Modifier
                                    .size(100.dp)
                                    .align(Alignment.CenterVertically),
                                alignment = Alignment.Center
                            )

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(10.dp),
                                content = {
                                    Text(
                                        text = state.projectDiscoveryList[it].projectTitle,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(
                                        text = state.projectDiscoveryList[it].projectSummary,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                })
                        })
                }
            }
        }
    }
}