package org.fknives.android.compose.picker.time.clock

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.fknives.android.compose.picker.number.NumberPickerConfig
import org.fknives.android.compose.picker.number.rememberNumberPickerScope
import org.fknives.android.compose.picker.number.rememberNumberPickerState
import org.fknives.android.compose.picker.text.util.rememberLapsCounterByOnIndexDifference
import org.fknives.android.compose.picker.time.SelectedTime
import org.fknives.android.compose.picker.time.StandardTimePickers
import org.fknives.android.compose.picker.time.TimePickerScope
import org.fknives.android.compose.picker.time.rememberAMorPMPickerScope
import org.fknives.android.compose.picker.time.rememberDefaultAMPMList
import org.fknives.android.compose.picker.time.rememberTimePickerScope
import kotlin.math.abs

@Composable
fun ClockTimePicker(
    timePickerMinWidth: Dp = 40.dp,
    selectedTime: SelectedTime,
    amPm: List<String> = rememberDefaultAMPMList(),
    onSelectedTimeChanged: (SelectedTime) -> Unit,
    timePickers: @Composable (TimePickerScope) -> Unit = { StandardTimePickers(it) }
) {
    var changingIsAM by remember(selectedTime.isAM) { mutableStateOf(selectedTime.isAM) }
    var changingHour by remember(selectedTime.hour) { mutableStateOf(0) }
    val hoursLapCounterByIndex = rememberLapsCounterByOnIndexDifference(selectedIndex = selectedTime.hour - 1, itemCount = 12)
    val minutesLapCounterByIndex = rememberLapsCounterByOnIndexDifference(selectedIndex = selectedTime.minute, itemCount = 60)

    val hourConfig = remember { NumberPickerConfig.configHourPicker12 }
    val hourPickerState = rememberNumberPickerState(selectedValue = selectedTime.hour + changingHour, config = hourConfig)
    val hourScope = rememberNumberPickerScope(
        state = hourPickerState,
        config = hourConfig,
        onIndexDifferenceChanging = {
            if (changingHour == 0) {
                val lapsCounter = hoursLapCounterByIndex(it)
                changingIsAM = isAMResultByHourLapsCounter(lapsCounter, selectedTime.isAM)
            }
        },
        onSelectedValueChange = {
            onSelectedTimeChanged(selectedTime.copy(hour = it, isAM = changingIsAM))
        },
        keys = arrayOf(changingHour, hoursLapCounterByIndex, changingIsAM)
    )
    val minuteConfig = remember { NumberPickerConfig.configMinutePicker }
    val minutePickerState = rememberNumberPickerState(selectedValue = selectedTime.minute, config = minuteConfig)
    val minutesScope = rememberNumberPickerScope(
        state = minutePickerState,
        config = minuteConfig,
        onIndexDifferenceChanging = {
            val hourIndexDifference = minutesLapCounterByIndex(it)
            var hourDifference = hourIndexDifference
            while (selectedTime.hour + hourDifference <= 0) {
                hourDifference += 12
            }
            while (selectedTime.hour + hourDifference > 12) {
                hourDifference -= 12
            }
            val lapsCounter = hoursLapCounterByIndex(hourIndexDifference)
            changingIsAM = isAMResultByHourLapsCounter(lapsCounter, selectedTime.isAM)
            changingHour = hourDifference
        },
        onSelectedValueChange = {
            onSelectedTimeChanged(selectedTime.copy(hour = selectedTime.hour + changingHour, minute = it, isAM = changingIsAM))
        },
        keys = arrayOf(changingHour, hoursLapCounterByIndex, changingIsAM)
    )
    val amORpmScope = rememberAMorPMPickerScope(
        listOfAMorPM = amPm,
        isAM = changingIsAM,
        onAMSelectedChange = {
            onSelectedTimeChanged(selectedTime.copy(hour = selectedTime.hour + changingHour, isAM = it))
        }
    )

    val scope = rememberTimePickerScope(
        timePickerMinWidth = timePickerMinWidth,
        hoursPickerScope = hourScope,
        minutesPickerScope = minutesScope,
        amORpmPickerScope = amORpmScope
    )

    timePickers(scope)
}

private fun isAMResultByHourLapsCounter(hourLapCounter: Int, selectedTimeIsAm: Boolean): Boolean =
    if (abs(hourLapCounter) % 2 == 0) {
        selectedTimeIsAm
    } else {
        !selectedTimeIsAm
    }