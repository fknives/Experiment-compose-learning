package org.fknives.android.compose.picker.text

import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.text.TextStyle
import org.fknives.android.compose.picker.text.content.*
import org.fknives.android.compose.picker.text.util.TextPickerDefaults
import org.fknives.android.compose.picker.text.util.rememberTextPickerAnimator
import org.fknives.android.compose.picker.text.util.rememberTextPickerState

@Composable
fun LinedTextPicker(
    modifier: Modifier = Modifier,
    textForIndex: (Int) -> String,
    itemCount: Int,
    selected: Int,
    onSelectedChange: (Int) -> Unit,
    textStyle: TextStyle = LocalTextStyle.current,
    roundAround: Boolean = TextPickerDefaults.roundAround,
    onIndexDifferenceChanging: (Int) -> Unit = TextPickerDefaults.onIndexDifferenceChanging,
    state: TextPickerState = rememberTextPickerState(selected = selected, itemCount = itemCount),
    animator: TextPickerAnimator = rememberTextPickerAnimator(roundAround = roundAround, onIndexDifferenceChanging = onIndexDifferenceChanging),
    textPickerContent: TextPickerContent = LinedTextPickerContent(),
    textPickerMeasurePolicy: MeasurePolicy = remember(state) { LinedTextPickerMeasurePolicy(state) },
    pickerItem: @Composable (text: String, translation: Float) -> Unit = defaultNumberPickerTextAlphaModifier()
) =
    TextPicker(
        modifier = modifier,
        textForIndex = textForIndex,
        itemCount = itemCount,
        selectedIndex = selected,
        onSelectedIndexChange = onSelectedChange,
        textStyle = textStyle,
        roundAround = roundAround,
        onIndexDifferenceChanging = onIndexDifferenceChanging,
        state = state,
        animator = animator,
        textPickerContent = textPickerContent,
        textPickerMeasurePolicy = textPickerMeasurePolicy,
        pickerItem = pickerItem,
    )