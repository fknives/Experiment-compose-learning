package org.fknives.android.compose.picker.number

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.fknives.android.compose.picker.text.TextPickerState
import org.fknives.android.compose.picker.text.util.rememberTextPickerState

/**
 * Caches function converting from TextPicker's Index to NumberPicker's NumberValues, defined by [config]
 *
 * Inverse of [valueToIndex]
 */
@Composable
fun rememberNumberPickerIndexToNumber(config: NumberPickerConfig): (Int) -> Int =
    remember(config) {
        if (config.reversedOrder) {
            { index: Int ->
                config.maximum - index - config.skipInBetween * index
            }
        } else {
            { index: Int ->
                config.minimum + index + config.skipInBetween * index
            }
        }
    }

/**
 * Function converting from  NumberPicker's NumberValues to TextPicker's Index, defined by [config].
 *
 * Inverse of [valueToIndex]
 */
private fun valueToIndex(config: NumberPickerConfig, value: Int): Int =
    if (config.reversedOrder) {
        (config.maximum - value) / (config.skipInBetween + 1)
    } else {
        (value - config.minimum) / (config.skipInBetween + 1)
    }

/**
 * Caches [TextPickerState] created from the NumberPicker's [config] and [selectedValue].
 */
@Composable
fun rememberNumberPickerState(selectedValue: Int, config: NumberPickerConfig): TextPickerState {
    val selected = valueToIndex(value = selectedValue, config = config)

    val itemCount = (config.maximum - config.minimum) / (config.skipInBetween + 1) + 1
    return rememberTextPickerState(selected = selected, itemCount = itemCount)
}