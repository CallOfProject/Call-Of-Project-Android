package callofproject.dev.androidapp.presentation.user_profile.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.domain.dto.user_profile.UserTagDTO
import callofproject.dev.androidapp.presentation.components.TagItem

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun UserTagEditComponent(
    onDismissRequest: () -> Unit,
    confirmEvent: (String) -> Unit,
    removeEvent: (String) -> Unit,
    userTagDTO: List<UserTagDTO> = emptyList()
) {
    var tagName by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Dialog(onDismissRequest = onDismissRequest) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .border(width = 1.dp, color = MaterialTheme.colorScheme.primary),
                colors = CardColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = Color.Transparent,
                    disabledContentColor = Color.Transparent
                ),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    BasicTextField(
                        value = tagName,
                        onValueChange = { tagName = it },
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.onSecondaryContainer,
                                CircleShape
                            )
                            .shadow(5.dp, CircleShape)
                            .background(MaterialTheme.colorScheme.background, CircleShape)
                            .padding(horizontal = 10.dp, vertical = 10.dp),
                        maxLines = 1,
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onTertiaryContainer),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                if (tagName.isEmpty())
                                    return@KeyboardActions

                                confirmEvent(tagName)
                                keyboardController?.hide()

                                tagName = ""
                            }
                        ),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                if (tagName.isEmpty()) {
                                    Text(
                                        text = stringResource(R.string.tag_name),
                                        color = Color.LightGray,
                                        fontSize = 14.sp
                                    )
                                }
                                innerTextField()
                            }
                        },
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                    )
                    FlowRow(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .padding(top = 5.dp)
                    ) {
                        userTagDTO.forEach { userTag ->
                            TagItem(
                                text = userTag.tagName,
                                onClickRemove = { removeEvent(userTag.tagId) },
                            )
                        }
                    }
                }
            }
        }
    }
}
