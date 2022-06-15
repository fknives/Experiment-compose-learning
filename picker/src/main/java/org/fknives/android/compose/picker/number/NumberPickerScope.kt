package org.fknives.android.compose.picker.number

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import org.fknives.android.compose.picker.text.util.TextPickerDefaults
import org.fknives.android.compose.picker.text.TextPickerState

/**
 * Prepared parameters for [TextPicker][org.fknives.android.compose.picker.text.TextPicker].
 *
 * [state] The [TextPickerState] expected to be set to [TextPicker][org.fknives.android.compose.picker.text.TextPicker]
 *
 * [onIndexDifferenceChanging] The onIndexDifferenceChanging Listener expected to be set to
 * [TextPicker][org.fknives.android.compose.picker.text.TextPicker]
 *
 * [onSelectedIndexChange] The onSelectedIndexChange Listener expected to be set to
 * [TextPicker][org.fknives.android.compose.picker.text.TextPicker]
 *
 * [textForIndex] The IndexToNumberVlaue Converter expected to be set to [TextPicker][org.fknives.android.compose.picker.text.TextPicker]
 *
 * [selectedIndex] The selectedIndex expected to be set to [TextPicker][org.fknives.android.compose.picker.text.TextPicker]
 *
 * [itemCount] The selectedIndex expected to be set to [TextPicker][org.fknives.android.compose.picker.text.TextPicker]
 */
@Immutable
interface NumberPickerScope {
    val state: TextPickerState
    val onIndexDifferenceChanging: (Int) -> Unit
    val onSelectedIndexChange: (Int) -> Unit
    val textForIndex: (Int) -> String
    val selectedIndex: Int get() = state.selected
    val itemCount: Int get() = state.itemCount
}

/**
 * Data class implementation of [NumberPickerScope]
 */
@Immutable
data class NumberPickerScopeImpl(
    override val state: TextPickerState,
    override val onIndexDifferenceChanging: (Int) -> Unit,
    override val onSelectedIndexChange: (Int) -> Unit,
    override val textForIndex: (Int) -> String,
) : NumberPickerScope {
    override val selectedIndex: Int get() = state.selected
    override val itemCount: Int get() = state.itemCount
}

/**
 * Caching function, preparing the [NumberPickerScope] from simple parameters.
 *
 * @param state The [TextPickerState] expected to be set to [TextPicker][org.fknives.android.compose.picker.text.TextPicker]
 * @param config The NumberPickers config. Used to convert between TextPicker's Index and NumberPicker's values.
 * @param onSelectedValueChange NumberPicker's onSelectedValueChange Listener
 * @param onIndexDifferenceChanging NumberPicker's onIndexDifferenceChanging Listener
 * @param keys any additional keys, to signal changing of the scope cache.
 */
@Composable
fun rememberNumberPickerScope(
    state: TextPickerState,
    config: NumberPickerConfig,
    onSelectedValueChange: (Int) -> Unit,
    onIndexDifferenceChanging: (Int) -> Unit = TextPickerDefaults.onIndexDifferenceChanging,
    vararg keys: Any?
): NumberPickerScope {
    val indexToNumber = rememberNumberPickerIndexToNumber(config = config)

    return remember(state, onIndexDifferenceChanging, config, keys) {

        NumberPickerScopeImpl(
            state = state,
            textForIndex = { "${indexToNumber(it)}" },
            onIndexDifferenceChanging = onIndexDifferenceChanging,
            onSelectedIndexChange = {
                onSelectedValueChange(indexToNumber(it))
            }
        )
    }
}