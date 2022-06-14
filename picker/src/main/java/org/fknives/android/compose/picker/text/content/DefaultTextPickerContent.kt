package org.fknives.android.compose.picker.text.content

import androidx.compose.runtime.Composable
import org.fknives.android.compose.picker.text.TextPickerContent

class DefaultTextPickerContent : TextPickerContent {

    @Composable
    override fun Content(
        textForIndex: (Int) -> String,
        selected: Int,
        moveUnsafeToProperIndex: (Int) -> Int,
        before1TranslatePercent: Float,
        itemTranslatePercent: Float,
        after1TranslatePercent: Float,
        after2TranslatePercent: Float,
        item: @Composable (text: String, translation: Float) -> Unit
    ) {
        val before1 = moveUnsafeToProperIndex(selected - 1)
        val after1 = moveUnsafeToProperIndex(selected + 1)
        val after2 = moveUnsafeToProperIndex(selected + 2)
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