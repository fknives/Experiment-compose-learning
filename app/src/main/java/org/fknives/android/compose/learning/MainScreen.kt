package org.fknives.android.compose.learning

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.fknives.android.compose.learning.navigation.Screens

val Screens.text: String
    get() = when (this) {
        Screens.NumberPicker -> "Number Picker"
        Screens.TimePicker -> "Time Picker"
        Screens.TextPicker -> "Text Picker"
    }

@Composable
fun MainContent(
    onNavigation: (Screens) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            columns = GridCells.Adaptive(240.dp),
            verticalArrangement = Arrangement.Bottom,
            contentPadding = PaddingValues(16.dp)
        ) {
            Screens.values().forEach {
                item {
                    MainNavigationItem(it, onClick = onNavigation)
                }
            }
        }
    }
}

@Composable
fun MainNavigationItem(
    screens: Screens,
    onClick: (Screens) -> Unit,
    text: String = screens.text
) {
    Text(
        text = text,
        fontSize = MaterialTheme.typography.h5.fontSize,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                onClick(screens)
            }
    )
}