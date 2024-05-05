package callofproject.dev.androidapp.presentation.components.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.presentation.project.project_filter.FilterComponent
import callofproject.dev.androidapp.util.route.UiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarComponent(
    title: String = "",
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: TopBarViewModel = hiltViewModel()
) {
    val isSearching = remember { mutableStateOf(false) }
    val isFiltering = remember { mutableStateOf(false) }
    val tf = remember { mutableStateOf("") }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)

                else -> Unit
            }
        }
    }
    TopAppBar(title = {
        if (isSearching.value) {
            BasicTextField(
                value = tf.value,
                onValueChange = { tf.value = it },
                maxLines = 1,
                singleLine = true,
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onTertiaryContainer),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MaterialTheme.colorScheme.onSecondaryContainer, CircleShape)
                    .shadow(5.dp, CircleShape)
                    .background(MaterialTheme.colorScheme.background, CircleShape)
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    viewModel.onEvent(TopBarEvent.OnSearchEntered(tf.value))
                }),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
            )

        } else if (isFiltering.value) {
            FilterComponent(isFiltering, onNavigate)
        } else {
            Text(text = title, color = MaterialTheme.colorScheme.onTertiaryContainer)
        }
    }, actions = {
        if (isSearching.value) {
            IconButton(onClick = {
                isSearching.value = false
                tf.value = ""
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.close_icon),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
        } else {
            IconButton(onClick = { isSearching.value = true }) {
                Icon(
                    painter = painterResource(id = R.drawable.search_icon),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
        }
        IconButton(onClick = { isFiltering.value = true }) {
            Icon(
                painter = painterResource(id = R.drawable.filter_icon),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }

        IconButton(onClick = { viewModel.onEvent(TopBarEvent.OnLogoutClicked) }) {
            Icon(
                painter = painterResource(id = R.drawable.logout),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }

    },
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            scrolledContainerColor = Color.Transparent,
            titleContentColor = Color.Transparent,
            navigationIconContentColor = Color.Transparent,
            actionIconContentColor = Color.Transparent
        )
    )
}