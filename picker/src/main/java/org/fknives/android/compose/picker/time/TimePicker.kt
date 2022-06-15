package org.fknives.android.compose.picker.time

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.fknives.android.compose.picker.number.NumberPickerConfig
import org.fknives.android.compose.picker.number.rememberNumberPickerScope
import org.fknives.android.compose.picker.number.rememberNumberPickerState

@Composable
fun TimePicker(
    timePickersMinWidth: Dp = 40.dp,
    selectedTime: SelectedTime,
    amPm: List<String> = rememberDefaultAMPMList(),
    onSelectedTimeChanged: (SelectedTime) -> Unit,
    timePickers: @Composable (TimePickerScope) -> Unit = { StandardTimePickers(it) }
) {
    val hourConfig = remember { NumberPickerConfig.configHourPicker12 }
    val hourState = rememberNumberPickerState(selectedValue = selectedTime.hour, config = hourConfig)
    val hourScope = rememberNumberPickerScope(
        state = hourState,
        config = hourConfig,
        onSelectedValueChange = {
            onSelectedTimeChanged(selectedTime.copy(hour = it))
        }
    )

    val minuteConfig = remember { NumberPickerConfig.configMinutePicker }
    val minuteState = rememberNumberPickerState(selectedValue = selectedTime.minute, config = minuteConfig)
    val minuteScope = rememberNumberPickerScope(
        state = minuteState,
        config = minuteConfig,
        onSelectedValueChange = {
            onSelectedTimeChanged(selectedTime.copy(minute = it))
        }
    )

    val amORpmScope = rememberAMorPMPickerScope(
        listOfAMorPM = amPm,
        isAM = selectedTime.isAM,
        onAMSelectedChange = {
            onSelectedTimeChanged(selectedTime.copy(isAM = it))
        })

    val scope = rememberTimePickerScope(
        timePickerMinWidth = timePickersMinWidth,
        hoursPickerScope = hourScope,
        minutesPickerScope = minuteScope,
        amORpmPickerScope = amORpmScope
    )

    timePickers(scope)
}