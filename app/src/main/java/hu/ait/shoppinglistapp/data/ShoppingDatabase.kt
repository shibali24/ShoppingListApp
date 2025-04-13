package hu.ait.shoppinglistapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ShoppingItem::class], version = 2)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}