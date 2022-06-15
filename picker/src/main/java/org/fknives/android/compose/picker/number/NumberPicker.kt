package org.fknives.android.compose.picker.number

import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import org.fknives.android.compose.picker.text.TextPickerState
import org.fknives.android.compose.picker.text.util.TextPickerDefaults

/**
 * Configuration API around [TextPicker][org.fknives.android.compose.picker.text.TextPicker].
 *
 * Sets up inner StateManagement of [TextPicker][org.fknives.android.compose.picker.text.TextPicker] in a [NumberPickerScope]
 * then places it via [CustomInnerTextPicker].
 *
 * @param modifier [Modifier] of [TextPicker][org.fknives.android.compose.picker.text.TextPicker]
 * @param config Configuration of NumberPicker. Defines the Number Range that should be shown.
 * @param selectedValue The selected value, expected to be between [config]'s minimum and maximum.
 * @param onSelectedValueChange Notified when the Selected Value Changes.
 * @param onIndexDifferenceChanging Signals the animation changes, how much the current dragging is away from index of [selectedValue].
 * Negative values mean the index were decreased, Positive means it was increased.
 * **IMPORTANT! works with index-difference, not values (minimumValue = 0 index)**
 * @param textStyle TextStyle of [TextPicker][org.fknives.android.compose.picker.text.TextPicker]
 * @param roundAround roundAround of [TextPicker][org.fknives.android.compose.picker.text.TextPicker].
 * Should behave like a Wheel, or should be limited.
 */
@Composable
fun NumberPicker(
    modifier: Modifier = Modifier,
    config: NumberPickerConfig,
    selectedValue: Int,
    onSelectedValueChange: (Int) -> Unit,
    onIndexDifferenceChanging: (Int) -> Unit = TextPickerDefaults.onIndexDifferenceChanging,
    textStyle: TextStyle = LocalTextStyle.current,
    roundAround: Boolean = TextPickerDefaults.roundAround,
) {
    NumberPicker(
        config = config,
        selectedValue = selectedValue,
        onSelectedValueChange = onSelectedValueChange,
        onIndexDifferenceChanging = onIndexDifferenceChanging
    ) {
        CustomInnerTextPicker(
            modifier = modifier,
            textStyle = textStyle,
            roundAround = roundAround
        )
    }
}

/**
 * Configuration API around [TextPicker][org.fknives.android.compose.picker.text.TextPicker].
 *
 * Sets up inner StateManagement of [TextPicker][org.fknives.android.compose.picker.text.TextPicker] in a [NumberPickerScope]
 * then places it via [timePicker].
 *
 * @param config Configuration of NumberPicker. Defines the Number Range that should be shown.
 * @param selectedValue The selected value, expected to be between [config]'s minimum and maximum.
 * @param onSelectedValueChange Notified when the Selected Value Changes.
 * @param onIndexDifferenceChanging Signals the animation changes, how much the current dragging is away from index of [selectedValue].
 * Negative values mean the index were decreased, Positive means it was increased.
 * **IMPORTANT! works with index-difference, not values (minimumValue = 0 index)**
 * Should behave like a Wheel, or should be limited.
 * @param state TextPickerState of [TextPicker][org.fknives.android.compose.picker.text.TextPicker]
 * @param timePicker The Lambda placing the TimePicker. [NumberPickerScope] is provided with the configuration setup.
 * *Note: Check [StandardInnerTextPicker], [CustomInnerTextPicker], if you wish to customize.*
 */
@Composable
fun NumberPicker(
    config: NumberPickerConfig,
    selectedValue: Int,
    onSelectedValueChange: (Int) -> Unit,
    onIndexDifferenceChanging: (Int) -> Unit = TextPickerDefaults.onIndexDifferenceChanging,
    state: TextPickerState = rememberNumberPickerState(selectedValue = selectedValue, config = config),
    timePicker: @Composable NumberPickerScope.() -> Unit = { StandardInnerTextPicker() }
) {
    require(selectedValue >= config.minimum) { "Selected Value($selectedValue) is less than Minimum (${config.minimum})!" }
    require(selectedValue <= config.maximum) { "Selected Value($selectedValue) is more than Maximum (${config.maximum})!" }
    val numberPickerScope = rememberNumberPickerScope(
        state = state,
        config = config,
        onIndexDifferenceChanging = onIndexDifferenceChanging,
        onSelectedValueChange = onSelectedValueChange
    )

    timePicker(numberPickerScope)
}