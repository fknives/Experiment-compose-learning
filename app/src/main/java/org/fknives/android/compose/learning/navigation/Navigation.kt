package org.fknives.android.compose.learning.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import org.fknives.android.compose.learning.pickers.NumberPickerScreen
import org.fknives.android.compose.learning.pickers.TextPickerScreen
import org.fknives.android.compose.learning.pickers.TimePickerScreen
import org.fknives.android.compose.learning.text

@Composable
fun Navigation(
    mainScreen: @Composable (onNavigation: (Screens) -> Unit) -> Unit
) {
    var screen by remember { mutableStateOf<Screens?>(null) }

    Crossfade(targetState = screen) { currentScreen ->
        if (currentScreen == null) {
            mainScreen { selected ->
                screen = selected
            }
        } else {
            DetailScreen(title = currentScreen.text, onBack = { screen = null }) {
                when (currentScreen) {
                    Screens.NumberPicker -> NumberPickerScreen()
                    Screens.TimePicker -> TimePickerScreen()
                    Screens.TextPicker -> TextPickerScreen()
                }
            }
        }
    }
}

@Composable
fun DetailScreen(
    title: String,
    onBack: () -> Unit,
    content: @Composable () -> Unit
) {
    BackHandler(onBack = onBack)
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        TopAppBar {
            IconButton(onClick = onBack) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back navigation")
            }
            Text(title)
        }
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            content()
        }
    }
}