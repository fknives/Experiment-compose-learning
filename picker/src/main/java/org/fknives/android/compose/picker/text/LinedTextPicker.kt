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

/**
 * Customised version of TextPicker.
 * Adds additional Dividers by [textPickerContent] and places them by customized [textPickerMeasurePolicy]
 * The Default Dividers can be customised by giving parameters to [LinedTextPickerContent] as [textPickerContent].
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
 * @param textPickerContent The actual Composables inside the TextPicker, by default it is the 4 Texts and the 2 Dividers
 * @param textPickerMeasurePolicy The MeasurePolicy of the Layout, by default Translates the 4 Texts in [textPickerContent]
 * according to [state] changed by [animator]
 * @param pickerItem The Expected Text Composables in [textPickerContent].
 */
@Composable
fun LinedTextPicker(
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
    textPickerContent: TextPickerContent = LinedTextPickerContent(),
    textPickerMeasurePolicy: MeasurePolicy = remember(state) { LinedTextPickerMeasurePolicy(state) },
    pickerItem: @Composable (text: String, translation: Float) -> Unit = defaultNumberPickerTextAlphaModifier()
) =
    TextPicker(
        modifier = modifier,
        textForIndex = textForIndex,
        itemCount = itemCount,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = onSelectedIndexChange,
        textStyle = textStyle,
        roundAround = roundAround,
        onIndexDifferenceChanging = onIndexDifferenceChanging,
        state = state,
        animator = animator,
        textPickerContent = textPickerContent,
        textPickerMeasurePolicy = textPickerMeasurePolicy,
        pickerItem = pickerItem,
    )