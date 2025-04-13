package hu.ait.shoppinglistapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import hu.ait.shoppinglistapp.data.ShoppingDatabase
import hu.ait.shoppinglistapp.data.ShoppingItem
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ShoppingViewModel(application: Application) : AndroidViewModel(application) {
    private val db = Room.databaseBuilder(
        application,
        ShoppingDatabase::class.java,
        "shopping-list.db"
    ).fallbackToDestructiveMigration().build()

    val items = db.itemDao().getAll().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun insert(item: ShoppingItem) = viewModelScope.launch {
        db.itemDao().insert(item)
    }

    fun update(item: ShoppingItem) = viewModelScope.launch {
        db.itemDao().update(item)
    }

    fun delete(item: ShoppingItem) = viewModelScope.launch {
        db.itemDao().delete(item)
    }

    fun deleteAll() = viewModelScope.launch {
        db.itemDao().deleteAll()
    }
}