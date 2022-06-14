package org.fknives.android.compose.learning.pickers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.fknives.android.compose.picker.time.clock.ClockTimePicker
import org.fknives.android.compose.picker.time.SelectedTime
import org.fknives.android.compose.picker.time.TimePicker

@Composable
fun TimePickerScreen() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            Labeled(text = "Clock Time Picker") {
                var selectedTime by remember { mutableStateOf(SelectedTime.get()) }

                ClockTimePicker(
                    selectedTime = selectedTime,
                    onSelectedTimeChanged = { selectedTime = it }
                )
            }

            Labeled(text = "Standard Time Picker") {
                var selectedTime by remember { mutableStateOf(SelectedTime.get()) }

                TimePicker(
                    selectedTime = selectedTime,
                    onSelectedTimeChanged = { selectedTime = it }
                )
            }
        }
    }
}