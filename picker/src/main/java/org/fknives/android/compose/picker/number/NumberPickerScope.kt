package org.fknives.android.compose.picker.number

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import org.fknives.android.compose.picker.text.util.TextPickerDefaults
import org.fknives.android.compose.picker.text.TextPickerState

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

@Composable
fun rememberNumberPickerScope(
    state: TextPickerState,
    config: NumberPickerConfig,
    onSelectedChange: (Int) -> Unit,
    onIndexDifferenceChanging: (Int) -> Unit = TextPickerDefaults.onIndexDifferenceChanging,
    vararg keys: Any?
): NumberPickerScope {
    val indexToNumber = rememberNumberPickerIndexToNumber(config = config)

    return remember(state, onIndexDifferenceChanging, config, keys) {

        NumberPickerScopeImpl(
            state = state,
            textForIndex = { "${indexToNumber(it)}" },
            onIndexDifferenceChanging = onIndexDifferenceChanging,
            onSelectedIndexChange = { onSelectedChange(indexToNumber(it)) }
        )
    }
}

@Immutable
interface NumberPickerScope {
    val state: TextPickerState
    val onIndexDifferenceChanging: (Int) -> Unit
    val onSelectedIndexChange: (Int) -> Unit
    val textForIndex: (Int) -> String
    val selectedIndex: Int get() = state.selected
    val itemCount: Int get() = state.itemCount
}