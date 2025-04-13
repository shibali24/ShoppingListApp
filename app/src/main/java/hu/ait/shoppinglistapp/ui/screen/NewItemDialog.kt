package hu.ait.shoppinglistapp.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import hu.ait.shoppinglistapp.R
import hu.ait.shoppinglistapp.data.ShoppingItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewItemDialog(
    onDismiss: () -> Unit,
    onItemAdded: (ShoppingItem) -> Unit
) {
    val context = LocalContext.current

    var itemName by remember { mutableStateOf("") }
    var itemDescription by remember { mutableStateOf("") }
    var itemPrice by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Food") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                if (itemName.isNotBlank() && selectedCategory.isNotBlank()) {
                    val newItem = ShoppingItem(
                        name = itemName,
                        category = selectedCategory,
                        description = itemDescription,
                        price = itemPrice.toFloatOrNull() ?: 0f
                    )
                    onItemAdded(newItem)
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.name_required),
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }) {
                Text(stringResource(R.string.add_item))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        },
        title = { Text(stringResource(R.string.add_item)) },
        text = {
            Column {
                OutlinedTextField(
                    value = itemName,
                    onValueChange = { itemName = it },
                    label = { Text("Name*") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = itemDescription,
                    onValueChange = { itemDescription = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = itemPrice,
                    onValueChange = { itemPrice = it },
                    label = { Text("Price") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                SpinnerSample(
                    list = listOf("Food", "Electronics", "Clothing", "Other"),
                    preselected = selectedCategory,
                    onSelectionChanged = { selectedCategory = it },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}
