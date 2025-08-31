package com.example.littlelemon.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MultiSelectFilterButtons(
    filters: List<String>,
    selectedFilters: Set<String>,
    onSelectionChange: (Set<String>) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        filters.forEach { filter ->
            val isSelected = filter in selectedFilters

            Button(
                onClick = {
                    val newSelection = if (isSelected) {
                        selectedFilters - filter // Deselect
                    } else {
                        selectedFilters + filter // Select
                    }
                    onSelectionChange(newSelection) // Pass updated set
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.secondary
                ),
                shape = MaterialTheme.shapes.extraLarge,
                contentPadding = PaddingValues(vertical = 4.dp, horizontal = 14.dp)
            ) {
                Text(
                    text = filter,
                    style = MaterialTheme.typography.labelLarge,
                    color = if (isSelected)
                        Color.White
                    else
                        Color.Gray
                )
            }
        }
    }
}
