package org.fknives.android.compose.picker.number

import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import org.fknives.android.compose.picker.text.LinedTextPicker
import org.fknives.android.compose.picker.text.TextPicker
import org.fknives.android.compose.picker.text.util.TextPickerDefaults

/**
 * Custom [TextPicker] Creator for [NumberPicker], containing all styling [TextPicker].
 * Keeps all the default values of [TextPicker].
 *
 * For More Customization, Give your Custom TextPicker callback to [NumberPicker]
 */
@Composable
fun NumberPickerScope.CustomInnerTextPicker(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    roundAround: Boolean = TextPickerDefaults.roundAround
) {
    TextPicker(
        modifier = modifier,
        textStyle = textStyle,
        roundAround = roundAround,
        textForIndex = textForIndex,
        itemCount = itemCount,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = onSelectedIndexChange,
        onIndexDifferenceChanging = onIndexDifferenceChanging,
        state = state
    )
}

/**
 * Default [TextPicker] Creator for [NumberPicker].
 * Keeps all the default values of [TextPicker].
 */
@Composable
fun NumberPickerScope.StandardInnerTextPicker(modifier: Modifier = Modifier) {
    TextPicker(
        modifier = modifier,
        textForIndex = textForIndex,
        itemCount = itemCount,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = onSelectedIndexChange,
        onIndexDifferenceChanging = onIndexDifferenceChanging,
        state = state
    )
}

/**
 * Default [LinedTextPicker] Creator for [NumberPicker].
 * Keeps all the default values of [LinedTextPicker].
 */
@Composable
fun NumberPickerScope.LinedInnerTextPicker(modifier: Modifier = Modifier) {
    LinedTextPicker(
        modifier = modifier,
        textForIndex = textForIndex,
        itemCount = itemCount,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = onSelectedIndexChange,
        onIndexDifferenceChanging = onIndexDifferenceChanging,
        state = state
    )
}

