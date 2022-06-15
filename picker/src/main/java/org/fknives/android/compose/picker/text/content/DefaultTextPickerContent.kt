package org.fknives.android.compose.picker.text.content

import androidx.compose.runtime.Composable

/**
 * Default [TextPickerContent] for [TextPicker][org.fknives.android.compose.picker.text.TextPicker].
 */
class DefaultTextPickerContent : TextPickerContent {

    /**
     * Creates the actual content.
     *
     * Calculates the indexes around [selected], gets each item's text via [textForIndex].
     * From that the [item]s are placed with their respective text and given translation.
     *
     * The items are expected to be places by [DefaultTextPickerContent]
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
    }
}