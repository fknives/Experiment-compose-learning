package org.fknives.android.compose.learning.pickers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.fknives.android.compose.picker.text.LinedTextPicker
import org.fknives.android.compose.picker.text.TextPicker

@Composable
fun TextPickerScreen() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        val text = listOf("Alma", "Banan", "Citrom","Dinnye","Eper","Fuge","Goji","Karfiol")

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Labeled(text = "Text Picker") {
                var selected by remember { mutableStateOf(0) }
                TextPicker(
                    modifier = Modifier.defaultMinSize(minWidth = 200.dp),
                    selectedIndex = selected,
                    textForIndex = text::get,
                    onSelectedIndexChange = {
                        selected = it
                    },
                    textStyle = MaterialTheme.typography.h5,
                    itemCount = text.size
                )
            }
            Labeled(text = "Limited Text Picker") {
                var selected by remember { mutableStateOf(0) }
                TextPicker(
                    modifier = Modifier.defaultMinSize(minWidth = 200.dp),
                    selectedIndex = selected,
                    textForIndex = text::get,
                    onSelectedIndexChange = {
                        selected = it
                    },
                    roundAround = false,
                    textStyle = MaterialTheme.typography.h5,
                    itemCount = text.size
                )
            }
            Labeled(text = "Lined Text Picker") {
                var selected by remember { mutableStateOf(0) }
                LinedTextPicker(
                    modifier = Modifier.defaultMinSize(minWidth = 200.dp),
                    selectedIndex = selected,
                    textForIndex = text::get,
                    onSelectedIndexChange = {
                        selected = it
                    },
                    textStyle = MaterialTheme.typography.h5,
                    itemCount = text.size
                )
            }
        }
    }
}