package org.fknives.android.compose.picker.number

import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import org.fknives.android.compose.picker.text.util.TextPickerDefaults
import org.fknives.android.compose.picker.text.TextPickerState

@Composable
fun NumberPicker(
    modifier: Modifier = Modifier,
    config: NumberPickerConfig,
    selectedValue: Int,
    onSelectedChange: (Int) -> Unit,
    onIndexDifferenceChanging: (Int) -> Unit = TextPickerDefaults.onIndexDifferenceChanging,
    textStyle: TextStyle = LocalTextStyle.current,
    roundAround: Boolean = TextPickerDefaults.roundAround,
) {
    NumberPicker(
        config = config,
        selectedValue = selectedValue,
        onSelectedChange = onSelectedChange,
        onIndexDifferenceChanging = onIndexDifferenceChanging
    ) {
        CustomInnerTextPicker(
            modifier = modifier,
            textStyle = textStyle,
            roundAround = roundAround
        )
    }
}

@Composable
fun NumberPicker(
    config: NumberPickerConfig,
    selectedValue: Int,
    onSelectedChange: (Int) -> Unit,
    onIndexDifferenceChanging: (Int) -> Unit = TextPickerDefaults.onIndexDifferenceChanging,
    state: TextPickerState = rememberNumberPickerState(selectedValue = selectedValue, config = config),
    timePicker: @Composable NumberPickerScope.() -> Unit = { StandardInnerTextPicker() }
) {
    require(selectedValue >= config.minimum) { "Selected Value($selectedValue) is less than Minimum (${config.minimum})!" }
    require(selectedValue <= config.maximum) { "Selected Value($selectedValue) is more than Maximum (${config.maximum})!" }
    val numberPickerScope = rememberNumberPickerScope(
        state,
        config,
        onIndexDifferenceChanging,
        onSelectedChange
    )

    timePicker(numberPickerScope)
}