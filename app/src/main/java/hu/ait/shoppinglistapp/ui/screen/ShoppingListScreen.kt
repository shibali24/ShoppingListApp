package hu.ait.shoppinglistapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.ait.shoppinglistapp.R
import hu.ait.shoppinglistapp.data.ShoppingItem
import hu.ait.shoppinglistapp.ui.viewmodel.ShoppingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen() {
    val viewModel: ShoppingViewModel = viewModel()
    val items by viewModel.items.collectAsState(initial = emptyList())
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.shopping_list_title)) },
                actions = {
                    IconButton(onClick = { viewModel.deleteAll() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.delete),
                            contentDescription = stringResource(R.string.delete_all_desc)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(
                    painter = painterResource(id = R.drawable.add),
                    contentDescription = stringResource(R.string.add_desc)
                )
            }
        }
    ) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .fillMaxSize()) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(items, key = { it.id }) { item ->
                    ShoppingItemRow(item = item, viewModel = viewModel)
                }
            }
        }
    }

    if (showDialog) {
        NewItemDialog(
            onDismiss = { showDialog = false },
            onItemAdded = {
                viewModel.insert(it)
                showDialog = false
            }
        )
    }
}

@Composable
fun ShoppingItemRow(item: ShoppingItem, viewModel: ShoppingViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.weight(1f)) {
            Image(
                painter = painterResource(
                    id = when (item.category) {
                        "Food" -> R.drawable.food
                        "Electronic" -> R.drawable.electronic
                        else -> R.drawable.book
                    }
                ),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(Modifier.weight(1f)) {
                Text(text = item.name)
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        Image(
            painter = painterResource(
                id = if (item.isBought) R.drawable.yescheckbox else R.drawable.checkbox
            ),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    viewModel.update(item.copy(isBought = !item.isBought))
                }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Image(
            painter = painterResource(id = R.drawable.delete),
            contentDescription = stringResource(R.string.delete_item_desc),
            modifier = Modifier
                .size(24.dp)
                .clickable { viewModel.delete(item) }
        )
    }
}