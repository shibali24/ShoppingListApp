package hu.ait.shoppinglistapp.ui.screen


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SpinnerSample(
    list: List<String>,
    preselected: String,
    onSelectionChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selected by remember { mutableStateOf(preselected) }
    var expanded by remember { mutableStateOf(false) }

    OutlinedCard(modifier = modifier.clickable { expanded = !expanded }) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selected,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Icon(Icons.Outlined.ArrowDropDown, null, modifier = Modifier.padding(8.dp))
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            list.forEach { entry ->
                DropdownMenuItem(
                    onClick = {
                        selected = entry
                        expanded = false
                        onSelectionChanged(entry)
                    },
                    text = {
                        Text(entry, modifier = Modifier.fillMaxWidth())
                    }
                )
            }
        }
    }
}