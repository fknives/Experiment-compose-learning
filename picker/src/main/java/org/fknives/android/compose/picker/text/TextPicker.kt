package org.fknives.android.compose.picker.text

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.text.TextStyle
import org.fknives.android.compose.picker.text.content.DefaultTextPickerContent
import org.fknives.android.compose.picker.text.content.DefaultTextPickerMeasurePolicy
import org.fknives.android.compose.picker.text.content.defaultNumberPickerTextAlphaModifier
import org.fknives.android.compose.picker.text.util.TextPickerDefaults
import org.fknives.android.compose.picker.text.util.rememberDefaultMoveUnsafeToProperIndex
import org.fknives.android.compose.picker.text.util.rememberTextPickerAnimator
import org.fknives.android.compose.picker.text.util.rememberTextPickerState
import org.fknives.android.compose.picker.text.util.rememberWrappedTextForIndex

@Composable
fun TextPicker(
    modifier: Modifier = Modifier,
    textForIndex: (Int) -> String,
    itemCount: Int,
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    textStyle: TextStyle = LocalTextStyle.current,
    roundAround: Boolean = TextPickerDefaults.roundAround,
    onIndexDifferenceChanging: (Int) -> Unit = TextPickerDefaults.onIndexDifferenceChanging,
    state: TextPickerState = rememberTextPickerState(selected = selectedIndex, itemCount = itemCount),
    animator: TextPickerAnimator = rememberTextPickerAnimator(roundAround = roundAround, onIndexDifferenceChanging = onIndexDifferenceChanging),
    textPickerContent: TextPickerContent = DefaultTextPickerContent(),
    textPickerMeasurePolicy: MeasurePolicy = remember(state) { DefaultTextPickerMeasurePolicy(state) },
    pickerItem: @Composable (text: String, translation: Float) -> Unit = defaultNumberPickerTextAlphaModifier()
) {

    val moveUnsafeToProperIndex: (Int) -> Int = rememberDefaultMoveUnsafeToProperIndex(itemCount = itemCount, roundAround = roundAround)
    val rememberTextForIndex = rememberWrappedTextForIndex(itemCount = itemCount, roundAround = roundAround, textForIndex = textForIndex)

    Layout(
        modifier = modifier
            .clipToBounds()
            .draggable(
            orientation = Orientation.Vertical,
            onDragStopped = { velocity ->
                animator.flingAndSnap(this, state, velocity) { change ->
                    val index = moveUnsafeToProperIndex(selectedIndex + change)
                    state.onPreviouslySelectedChange(index)
                    onSelectedIndexChange(moveUnsafeToProperIndex(selectedIndex + change))
                }
            },
            state = rememberDraggableState {
                animator.onDeltaY(state, it)
            }
        ),
        content = {
            if (state.previousSelected != selectedIndex) {
                LaunchedEffect(selectedIndex) {
                    animator.snapToIndex(itemCount = itemCount, state = state)
                    state.onPreviouslySelectedChange(selectedIndex)
                }
            }

            ProvideTextStyle(textStyle) {
                textPickerContent.Content(
                    textForIndex = rememberTextForIndex,
                    item = pickerItem,
                    moveUnsafeToProperIndex = moveUnsafeToProperIndex,
                    selected = moveUnsafeToProperIndex(selectedIndex + state.indexOffset),
                    before1TranslatePercent = state.before1TranslatePercent,
                    itemTranslatePercent = state.itemTranslatePercent,
                    after1TranslatePercent = state.after1TranslatePercent,
                    after2TranslatePercent = state.after2TranslatePercent
                )
            }
        },
        measurePolicy = textPickerMeasurePolicy
    )
}

fun interface TextPickerContent {
    @Composable
    fun Content(
        textForIndex: (Int) -> String,
        selected: Int,
        moveUnsafeToProperIndex: (Int) -> Int,
        before1TranslatePercent: Float,
        itemTranslatePercent: Float,
        after1TranslatePercent: Float,
        after2TranslatePercent: Float,
        item: @Composable (text: String, translation: Float) -> Unit
    )
}
