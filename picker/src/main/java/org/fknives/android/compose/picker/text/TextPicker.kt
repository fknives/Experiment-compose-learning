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
import org.fknives.android.compose.picker.text.content.TextPickerContent
import org.fknives.android.compose.picker.text.content.defaultNumberPickerTextAlphaModifier
import org.fknives.android.compose.picker.text.util.TextPickerDefaults
import org.fknives.android.compose.picker.text.util.rememberDefaultMoveUnsafeToProperIndex
import org.fknives.android.compose.picker.text.util.rememberTextPickerAnimator
import org.fknives.android.compose.picker.text.util.rememberTextPickerState
import org.fknives.android.compose.picker.text.util.rememberSafeTextForIndex

/**
 * NumberPicker Custom Layout Composable which shows given text on each index.
 * Shows 3 selections options (while animating may show 4).
 * Scrolling the Composable moves between elements and at the end of the movement is selects the most middle element.
 *
 * If no selection changes, snaps back to the selected element.
 * Longer drags and letting go, animates scrolling over elements.
 * Remembers the previously selected element and when selecting programmatically, scrolls to it..
 * (Selected by UI action makes it previously selected before sending back the event, aka no double animation)
 *
 * @param modifier Standard Modifier for the Layout
 * @param textForIndex Used to display Text for the given index. Indexes are between `0 until [itemCount]`.
 * @param itemCount Number of Elements that can be selected
 * @param selectedIndex Currently selected index. Must be between `0 until [itemCount]`
 * @param onSelectedIndexChange Notified after animation, what the User selected through dragging of the UI.
 * @param textStyle Default textStyle provided for the Text Composables inside the Layout.
 * @param roundAround Behavioural change:
 * Setting this to True, means the elements behave like a wheel, and the last element is above the first.
 * Setting this to False, means the first element has no elements above it, and the last element has no element below it.
 * @param onIndexDifferenceChanging Signals the animation changes, how much the current dragging is away from [selectedIndex].
 * Negative values mean the index were decreased, Positive means it was increased.
 * @param state Animation State for the TextPicker
 * @param animator Uses state to Animate the Composable elements. Handles continous drag, calculating fling and snapping to an index.
 * @param textPickerContent The actual Composables inside the TextPicker, by default it is the 4 Texts
 * @param textPickerMeasurePolicy The MeasurePolicy of the Layout, by default Translates the 4 Texts in [textPickerContent]
 * according to [state] changed by [animator]
 * @param pickerItem The Expected Text Composables in [textPickerContent].
 * text is the given Text for the Item.
 * translation is the movement of the element:
 * - 1.0 -> selected
 * - 0.5 -> unselected
 * - 0.0 -> completely outside of boundaries.
 * - movement between translation are expected to be proper (such as setting alpha based on the translation linearly).
 */
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
    val rememberTextForIndex = rememberSafeTextForIndex(
        itemCount = itemCount,
        roundAround = roundAround,
        textForIndex = textForIndex,
        moveUnsafeToProperIndex = moveUnsafeToProperIndex
    )

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

