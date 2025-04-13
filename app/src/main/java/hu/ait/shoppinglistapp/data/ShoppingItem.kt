package hu.ait.shoppinglistapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class ShoppingItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val category: String,
    val name: String,
    val description: String,
    val price: Float,
    val isBought: Boolean = false
)