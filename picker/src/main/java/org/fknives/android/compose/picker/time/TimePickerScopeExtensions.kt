package org.fknives.android.compose.picker.time

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import org.fknives.android.compose.picker.number.CustomInnerTextPicker
import org.fknives.android.compose.picker.text.TextPicker
import org.fknives.android.compose.picker.text.util.TextPickerDefaults

/**
 * Default Pickers for [TimePicker].
 *
 * Gets values from [scope] and creates the respective Pickers.
 */
@Composable
fun StandardTimePickers(scope: TimePickerScope) {
    Row {
        scope.apply {
            HourPicker()
            MinutePicker()
            IsAMorPMPicker()
        }
    }
}

/**
 * Default Hour Picker for [TimePicker]
 *
 * Gets values from [TimePickerScope.hoursPickerScope].
 */
@Composable
fun TimePickerScope.HourPicker(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    roundAround: Boolean = TextPickerDefaults.roundAround
) {
    hoursPickerScope.apply {
        CustomInnerTextPicker(
            modifier = modifier.defaultMinSize(timePickerMinWidth),
            textStyle = textStyle,
            roundAround = roundAround
        )
    }
}

/**
 * Default Hour Picker for [TimePicker]
 *
 * Gets values from [TimePickerScope.minutesPickerScope].
 */

@Composable
fun TimePickerScope.MinutePicker(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    roundAround: Boolean = TextPickerDefaults.roundAround
) {
    minutesPickerScope.apply {
        CustomInnerTextPicker(
            modifier = modifier.defaultMinSize(timePickerMinWidth),
            textStyle = textStyle,
            roundAround = roundAround
        )
    }
}

/**
 * Default Hour Picker for [TimePicker]
 *
 * Gets values from [TimePickerScope.amORpmPickerScope].
 */
@Composable
fun TimePickerScope.IsAMorPMPicker(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    roundAround: Boolean = false
) {
    amORpmPickerScope.apply {
        TextPicker(
            modifier = modifier.defaultMinSize(timePickerMinWidth),
            textStyle = textStyle,
            roundAround = roundAround,
            textForIndex = listOfAMorPM::get,
            itemCount = listOfAMorPM.size,
            selectedIndex = if (isAM) 0 else 1,
            onSelectedIndexChange = { onAMSelectedChange(it == 0) },
            onIndexDifferenceChanging = onIndexDifferenceChanging,
        )
    }
}
