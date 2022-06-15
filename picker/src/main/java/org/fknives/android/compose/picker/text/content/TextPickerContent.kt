package org.fknives.android.compose.picker.text.content

import androidx.compose.runtime.Composable

fun interface TextPickerContent {
    @Composable
    fun Content(
        textForIndex: (Int) -> String,
        selected: Int,
        before1TranslatePercent: Float,
        itemTranslatePercent: Float,
        after1TranslatePercent: Float,
        after2TranslatePercent: Float,
        item: @Composable (text: String, translation: Float) -> Unit
    )
}