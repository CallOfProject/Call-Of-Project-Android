package callofproject.dev.androidapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MultipleFilterChipComponent(
    text: String,
    selectedItems: MutableState<List<String>>,
    itemsList: List<String>
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .border(BorderStroke(1.dp, SolidColor(Color.Gray)))
    ) {
        Text(
            text = text,
            fontSize = 15.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
            modifier = Modifier.padding(5.dp)
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.Center
        ) {
            items(itemsList) { item ->
                FilterChip(
                    modifier = Modifier.padding(horizontal = 6.dp), // gap between items
                    selected = selectedItems.value.contains(item),
                    onClick = {
                        val currentList =
                            selectedItems.value.toMutableList()
                        if (currentList.contains(item)) {
                            currentList.remove(item)
                        } else {
                            currentList.add(item)
                        }

                        selectedItems.value = currentList
                    },
                    label = {
                        Text(text = item, fontSize = 9.5.sp)
                    }
                )
            }
        }
    }
}

