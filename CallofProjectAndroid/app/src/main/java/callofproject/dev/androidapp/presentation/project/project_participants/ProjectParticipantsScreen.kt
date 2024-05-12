package callofproject.dev.androidapp.presentation.project.project_participants

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.domain.dto.project.ProjectParticipantDTO
import callofproject.dev.androidapp.presentation.components.AlertDialog
import callofproject.dev.androidapp.presentation.components.LoadingComponent
import callofproject.dev.androidapp.presentation.components.bottom_bar.BottomBarComponent
import callofproject.dev.androidapp.presentation.connections.ConnectionEvent
import callofproject.dev.androidapp.presentation.project.components.projects_topbar.ProjectTopBarComponent
import callofproject.dev.androidapp.presentation.project.project_overview.ProjectOverviewViewModel
import callofproject.dev.androidapp.util.route.UiEvent
import coil.compose.rememberAsyncImagePainter

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProjectParticipantsScreen(
    viewModel: ProjectParticipantsViewModel = hiltViewModel(),
    scaffoldState: SnackbarHostState,
    onNavigate: (UiEvent.Navigate) -> Unit,
    projectId: String,
    selectedNavBar: Int
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.findProjectParticipants(projectId)
    }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)

                is UiEvent.ShowToastMessage -> {
                    Toast.makeText(context, event.message.asString(context), Toast.LENGTH_SHORT)
                        .show()
                }

                else -> Unit
            }
        }
    }

    Scaffold(
        topBar = {
            ProjectTopBarComponent(
                onNavigate = onNavigate,
                projectId = projectId,
                selectedNavBar = selectedNavBar
            )
        },
        bottomBar = { BottomBarComponent(scaffoldState, onNavigate) }) { scf ->
        if (!viewModel.state.isParticipantOrOwner)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scf),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    Icons.Filled.Lock,
                    contentDescription = stringResource(R.string.default_image_description),
                    modifier = Modifier.size(55.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                )
                Text(
                    text = stringResource(R.string.msg_not_found_participant),
                    modifier = Modifier.padding(5.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight(600)
                )
            }
        else LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(scf),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(viewModel.state.participants) {
                ParticipantCard(participantObj = it, viewModel)
            }
        }

    }
}


@Composable
private fun ParticipantCard(
    participantObj: ProjectParticipantDTO,
    viewModel: ProjectParticipantsViewModel
) {
    var expandedAlertDialog by remember { mutableStateOf(false) }
    Card(
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(10.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Full Name",
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center,
                    fontSize = 13.sp,
                    fontWeight = FontWeight(600)
                )
                Text(
                    text = participantObj.full_name,
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Normal,
                    fontSize = 12.sp,
                    overflow = TextOverflow.Ellipsis
                )
            }


            Box(
                modifier = Modifier
                    .height(40.dp)
                    .width(1.dp)
                    .background(color = Color.LightGray)
            )

            Column(
                modifier = Modifier.padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Username:",
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center,
                    fontSize = 13.sp,
                    fontWeight = FontWeight(600)
                )
                Text(
                    text = participantObj.username,
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Normal,
                    fontSize = 12.sp,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Box(
                modifier = Modifier
                    .height(40.dp)
                    .width(1.dp)
                    .background(color = Color.LightGray)
            )

            Column(
                modifier = Modifier.padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Join Date",
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center,
                    fontSize = 13.sp,
                    fontWeight = FontWeight(600)
                )
                Text(
                    text = participantObj.join_date,
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Normal,
                    fontSize = 12.sp,
                    overflow = TextOverflow.Ellipsis
                )
            }


            if (viewModel.state.isOwner) {
                IconButton(onClick = {
                    expandedAlertDialog = true
                }) {
                    Icon(
                        painter = painterResource(R.drawable.trash),
                        contentDescription = "",
                        tint = Color.Red,
                        modifier = Modifier.size(30.dp),
                    )
                }
            }
        }
    }

    if (expandedAlertDialog)
        AlertDialog(
            onDismissRequest = { expandedAlertDialog = false },
            onConfirmation = {
                viewModel.onEvent(
                    ProjectParticipantsEvent.OnClickRemoveParticipant(
                        participantObj.user_id,
                        participantObj.project_id
                    )
                )
            },
            dialogTitle = stringResource(R.string.dialog_title_remove_participant),
            dialogText = stringResource(R.string.dialog_text_remove_participant),
            confirmMessage = stringResource(R.string.dialog_confirm_remove_participant)
        )

}


