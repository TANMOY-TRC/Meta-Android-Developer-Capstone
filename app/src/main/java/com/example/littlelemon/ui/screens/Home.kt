package com.example.littlelemon.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.R
import com.example.littlelemon.data.local.DatabaseProvider
import com.example.littlelemon.ui.components.MenuItemRow
import com.example.littlelemon.ui.components.MultiSelectFilterButtons
import com.example.littlelemon.ui.components.SearchBar
import com.example.littlelemon.ui.theme.MarkazText
import com.example.littlelemon.viewmodel.UserViewModel

@SuppressLint("RememberReturnType")
@Composable
fun Home(navController: NavController, viewModel: UserViewModel) {
    val focusManager = LocalFocusManager.current

    val database = DatabaseProvider.getDatabase(LocalContext.current.applicationContext)
    val databaseMenuItems by database.menuItemDao().getAll().observeAsState(emptyList())

    var menuItems by rememberSaveable(databaseMenuItems) { mutableStateOf(databaseMenuItems) }

    val allFilters = listOf("Starters", "Mains", "Desserts", "Drinks")

    var searchQuery by rememberSaveable { mutableStateOf("") }
    var selectedFilters by rememberSaveable { mutableStateOf(setOf<String>()) }

    fun applySearchFilter() {
        var filteredItems = databaseMenuItems

        if (selectedFilters.isNotEmpty()) {
            val lowerCaseFilters = selectedFilters.map { it.lowercase() }.toSet()

            filteredItems = filteredItems.filter {
                lowerCaseFilters.contains(it.category.lowercase())
            }
        }

        if (searchQuery.isNotEmpty()) {
            filteredItems = filteredItems.filter {
                it.title.contains(searchQuery, ignoreCase = true)
            }
        }

        menuItems = filteredItems
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                // No visual ripple or indication on click
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
            }
    ) {
        // Upper Panel
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(20.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.restaurant_name),
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.primaryContainer,
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column(
                        modifier = Modifier
                            .weight(0.6f)
                            .padding(end = 18.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(id = R.string.location),
                            style = MaterialTheme.typography.headlineMedium,
                            fontFamily = MarkazText,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = stringResource(id = R.string.short_description),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Medium,
                            color = Color.White,
                        )
                    }

                    Image(
                        painter = painterResource(R.drawable.hero),
                        contentDescription = "Little Lemon Hero Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .weight(0.4f)
                            .aspectRatio(1f)
                            .clip(MaterialTheme.shapes.large)
                    )
                }

                SearchBar(
                    query = searchQuery,
                    onQueryChange = {
                        searchQuery = it
                        applySearchFilter()
                    },
                    modifier = Modifier.padding(top = 20.dp)
                )
            }
        }

        // Lower Panel: Filters
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = "ORDER FOR DELIVERY!",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(vertical = 15.dp)
                )

                MultiSelectFilterButtons(
                    filters = allFilters,
                    selectedFilters = selectedFilters,
                    onSelectionChange = { newSelection ->
                        selectedFilters = newSelection
                        applySearchFilter()
                    },
                )
            }
        }

        // Lower Panel: Menu Item List
        items(menuItems) { item ->
            MenuItemRow(menuItem = item)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    val navController = rememberNavController()
    val viewModel: UserViewModel = viewModel()

    Home(navController = navController, viewModel = viewModel)
}
