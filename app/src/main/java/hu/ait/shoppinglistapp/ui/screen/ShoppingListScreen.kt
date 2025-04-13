package hu.ait.shoppinglistapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.shopping_list_title),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                )
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(items, key = { it.id }) { item ->
                        ShoppingItemRow(item = item, viewModel = viewModel)
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        IconButton(onClick = { viewModel.deleteAll() }) {
                            Image(
                                painter = painterResource(id = R.drawable.delete),
                                contentDescription = stringResource(R.string.delete_all_desc),
                                modifier = Modifier.size(28.dp),
                                colorFilter = null
                            )

                        }
                        TextButton(onClick = { viewModel.deleteAll() }) {
                            Text(stringResource(R.string.delete_all_button))
                        }
                    }
                    Image(
                        painter = painterResource(id = R.drawable.add),
                        contentDescription = stringResource(R.string.add_desc),
                        modifier = Modifier
                            .size(36.dp)
                            .clickable { showDialog = true },
                        alignment = Alignment.Center,
                        colorFilter = null
                    )

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
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyLarge
                )
                if (item.description.isNotBlank()) {
                    Text(
                        text = item.description,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Text(
                    text = "$${"%.2f".format(item.price)}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
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
