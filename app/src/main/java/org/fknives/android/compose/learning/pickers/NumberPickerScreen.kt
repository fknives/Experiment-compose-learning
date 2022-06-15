package org.fknives.android.compose.learning.pickers

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.fknives.android.compose.picker.number.LinedInnerTextPicker
import org.fknives.android.compose.picker.number.NumberPicker
import org.fknives.android.compose.picker.number.NumberPickerConfig
import org.fknives.android.compose.picker.text.TextPicker


@Composable
fun NumberPickerScreen() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            Labeled(text = "Number Picker", modifier = Modifier.padding(16.dp)) {
                var selected by remember { mutableStateOf(0) }
                NumberPicker(
                    modifier = Modifier.defaultMinSize(minWidth = 200.dp),
                    onSelectedValueChange = {
                        selected = it
                    },
                    textStyle = MaterialTheme.typography.h5,
                    config = NumberPickerConfig(maximum = 10),
                    selectedValue = selected
                )
            }
            Labeled(text = "Limited Minute Picker", modifier = Modifier.padding(16.dp)) {
                var selected by remember { mutableStateOf(0) }
                NumberPicker(
                    config = NumberPickerConfig.configMinutePicker,
                    selectedValue = selected,
                    onSelectedValueChange = { selected = it },
                    roundAround = false,
                    textStyle = MaterialTheme.typography.h5
                )
            }
            Labeled(text = "Lined Minute Picker", modifier = Modifier.padding(16.dp)) {
                var selected by remember { mutableStateOf(0) }
                ProvideTextStyle(MaterialTheme.typography.h5) {
                    NumberPicker(
                        config = NumberPickerConfig.configMinutePicker,
                        selectedValue = selected,
                        onSelectedValueChange = { selected = it },
                        timePicker = { LinedInnerTextPicker(modifier = Modifier.defaultMinSize(minWidth = 200.dp)) }
                    )
                }
            }

            Labeled(text = "Custom Hour Picker", modifier = Modifier.padding(16.dp)) {
                var selected by remember { mutableStateOf(0) }
                NumberPicker(
                    config = NumberPickerConfig.configHourPicker24,
                    selectedValue = selected,
                    onSelectedValueChange = { selected = it }
                ) {
                    TextPicker(
                        modifier = Modifier.defaultMinSize(minWidth = 200.dp),
                        textStyle = MaterialTheme.typography.h5,
                        roundAround = false,
                        textForIndex = textForIndex,
                        itemCount = itemCount,
                        selectedIndex = selectedIndex,
                        onSelectedIndexChange = onSelectedIndexChange,
                        onIndexDifferenceChanging = onIndexDifferenceChanging,
                        state = state
                    )
                }
            }

            Labeled(text = "Skipping 1, 1-5 Picker(1,3,5)", modifier = Modifier.padding(16.dp)) {
                var selected by remember { mutableStateOf(1) }
                ProvideTextStyle(MaterialTheme.typography.h5) {
                    NumberPicker(
                        config = NumberPickerConfig(maximum = 5, minimum = 1, skipInBetween = 1),
                        selectedValue = selected,
                        onSelectedValueChange = { selected = it }
                    )
                }
            }

            Labeled(text = "Skipping 3, 1-5 Picker(1,4)", modifier = Modifier.padding(16.dp)) {
                var selected by remember { mutableStateOf(1) }
                ProvideTextStyle(MaterialTheme.typography.h5) {
                    NumberPicker(
                        config = NumberPickerConfig(maximum = 5, minimum = 1, skipInBetween = 2),
                        selectedValue = selected,
                        onSelectedValueChange = { selected = it }
                    )
                }
            }

            Labeled(text = "Skipping 1, 1-5 Picker(5,3,1) reverse", modifier = Modifier.padding(16.dp)) {
                var selected by remember { mutableStateOf(1) }
                ProvideTextStyle(MaterialTheme.typography.h5) {
                    NumberPicker(
                        config = NumberPickerConfig(maximum = 5, minimum = 1, skipInBetween = 1, reversedOrder = true),
                        selectedValue = selected,
                        onSelectedValueChange = { selected = it }
                    )
                }
            }

            Labeled(text = "Skipping 2, 1-5 Picker(5,2) reverse", modifier = Modifier.padding(16.dp)) {
                var selected by remember { mutableStateOf(1) }
                ProvideTextStyle(MaterialTheme.typography.h5) {
                    NumberPicker(
                        config = NumberPickerConfig(maximum = 5, minimum = 1, skipInBetween = 2, reversedOrder = true),
                        selectedValue = selected,
                        roundAround = false,
                        onSelectedValueChange = { selected = it },
                    )
                }
            }
        }
    }
}