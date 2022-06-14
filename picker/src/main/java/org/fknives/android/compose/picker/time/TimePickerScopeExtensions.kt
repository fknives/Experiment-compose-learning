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
