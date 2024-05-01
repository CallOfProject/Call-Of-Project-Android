package callofproject.dev.androidapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import callofproject.dev.androidapp.R

@Composable
fun DropDownComponent(
    options: List<String>,
    onClick: (String) -> Unit,
    isOpen: Boolean = false,
    selectedItem: MutableState<String>
) {

    var dropControl by remember { mutableStateOf(isOpen) }
    var selectIndex by remember { mutableIntStateOf(0) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedCard(modifier = Modifier.padding(5.dp)) {

            Row(horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .wrapContentWidth()
                    .height(30.dp)
                    .padding(5.dp)
                    .clickable {
                        dropControl = true
                    }) {
                Text(
                    text = stringResource(R.string.sort_prefix),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = options[selectIndex],
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal
                )
                Image(
                    painter = painterResource(id = R.drawable.cop_logo_180),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(5.dp)
                        .height(15.dp)
                )

            }

            DropdownMenu(expanded = dropControl, onDismissRequest = { dropControl = false }) {

                options.forEachIndexed { index, strings ->
                    DropdownMenuItem(
                        text = { Text(text = strings) },
                        onClick = {
                            dropControl = false
                            selectIndex = index
                            selectedItem.value = options[index]
                            onClick(options[index])
                        })
                }

            }
        }
    }
}