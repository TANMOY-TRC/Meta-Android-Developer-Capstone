package com.example.littlelemon

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.example.littlelemon.data.local.AppDatabase
import com.example.littlelemon.data.local.DatabaseProvider
import com.example.littlelemon.data.remote.ApiService
import com.example.littlelemon.data.remote.MenuItemNetwork
import com.example.littlelemon.ui.theme.LittleLemonTheme
import com.example.littlelemon.ui.AppScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    private lateinit var database: AppDatabase

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = DatabaseProvider.getDatabase(applicationContext)
        enableEdgeToEdge()

        lifecycleScope.launch(Dispatchers.IO) {
            val isEmpty = database.menuItemDao().getCount() == 0
            if (isEmpty) {
                try {
                    val menu = ApiService.fetchMenu()
                    saveMenuToDatabase(menu)
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            applicationContext,
                            "No internet connection",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        setContent {
            LittleLemonTheme {
                AppScreen()
            }
        }
    }

    private fun saveMenuToDatabase(menuItemsNetwork: List<MenuItemNetwork>) {
        val menuItemsRoom = menuItemsNetwork.map {
            it.toMenuItemRoom()
        }
        database.menuItemDao().insertAll(menuItemsRoom)
    }
}
