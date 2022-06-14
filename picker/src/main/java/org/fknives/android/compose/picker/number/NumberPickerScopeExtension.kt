package org.fknives.android.compose.picker.number

import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import org.fknives.android.compose.picker.text.LinedTextPicker
import org.fknives.android.compose.picker.text.TextPicker
import org.fknives.android.compose.picker.text.util.TextPickerDefaults

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

@Composable
fun NumberPickerScope.LinedInnerTextPicker(modifier: Modifier = Modifier) {
    LinedTextPicker(
        modifier = modifier,
        textForIndex = textForIndex,
        itemCount = itemCount,
        selected = selectedIndex,
        onSelectedChange = onSelectedIndexChange,
        onIndexDifferenceChanging = onIndexDifferenceChanging,
        state = state
    )
}

