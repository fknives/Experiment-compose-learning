package org.fknives.android.compose.picker.text.content

import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Dp
import org.fknives.android.compose.picker.text.util.TextPickerDefaults
import org.fknives.android.compose.picker.text.TextPickerState

/**
 * Default [TextPickerContent] for [LinedTextPicker][org.fknives.android.compose.picker.text.LinedTextPicker].
 */
class LinedTextPickerContent(
    private val dividerModifier: Modifier = Modifier,
    private val dividerColor: @Composable () -> Color = { MaterialTheme.colors.onSurface.copy(alpha = TextPickerDefaults.dividerAlpha) },
    private val dividerThickness: Dp = TextPickerDefaults.dividerThickness,
    private val dividerStartIndent: Dp = TextPickerDefaults.dividerStartIndent
) : TextPickerContent {

    /**
     * Creates the actual content.
     *
     * Calculates the indexes around [selected], gets each item's text via [textForIndex].
     * From that the [item]s are placed with their respective text and given translation.
     *
     * Also adds 2 Divider configured by constructor at the end.
     * The Dividers are expected to be places by [LinedTextPickerMeasurePolicy].
     */
    @Composable
    override fun Content(
        textForIndex: (Int) -> String,
        selected: Int,
        before1TranslatePercent: Float,
        itemTranslatePercent: Float,
        after1TranslatePercent: Float,
        after2TranslatePercent: Float,
        item: @Composable (text: String, translation: Float) -> Unit
    ) {
        val before1 = selected - 1
        val after1 = selected + 1
        val after2 = selected + 2
        item(
            text = textForIndex(before1),
            translation = before1TranslatePercent
        )
        item(
            text = textForIndex(selected),
            translation = itemTranslatePercent
        )
        item(
            text = textForIndex(after1),
            translation = after1TranslatePercent
        )
        item(
            text = textForIndex(after2),
            translation = after2TranslatePercent
        )
        Divider(
            modifier = dividerModifier,
            color = dividerColor(),
            thickness = dividerThickness,
            startIndent = dividerStartIndent,
        )
        Divider(
            modifier = dividerModifier,
            color = dividerColor(),
            thickness = dividerThickness,
            startIndent = dividerStartIndent,
        )
    }
}

/**
 * Same as [DefaultTextPickerMeasurePolicy], with additiona Divider positioning.
 *
 * Expected to be used together with [LinedTextPickerContent] providing the Dividers,
 * which this MeasurePolicy places around the selected Item's usual place.
 */
class LinedTextPickerMeasurePolicy(state: TextPickerState) : DefaultTextPickerMeasurePolicy(state = state) {

    override fun Placeable.PlacementScope.placementAfterItems(placeables: List<Placeable>, state: TextPickerState) {
        val dividers = placeables.drop(numberOfItemsToPlace)
        dividers.forEachIndexed { index, placeable ->
            val y = ((index + 1) * state.itemSize) - placeable.measuredHeight / 2f
            placeable.placeRelative(x = 0, y = y.toInt())
        }
    }
}