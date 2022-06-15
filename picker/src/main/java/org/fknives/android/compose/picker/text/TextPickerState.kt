package org.fknives.android.compose.picker.text

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * Internal State for [TextPicker]
 *
 * Holds the Scrolling [offset] changes and Text [itemSize]-s.
 * From this the Translations and index changes are Derived.
 */
class TextPickerState(
    itemSizeState: MutableState<Float>,
    previousSelectedState: MutableState<Int>,
    val selected: Int,
    val itemCount: Int
) {
    var offset by mutableStateOf(0f)
        private set

    var itemSize by itemSizeState
        private set

    var previousSelected by previousSelectedState
        private set

    val translateOffset by derivedStateOf {
        (offset % itemSize) - (animationShiftOffset * itemSize)
    }
    val indexOffset by derivedStateOf {
        (-offset / itemSize).toInt() - animationShiftOffset
    }
    private val animationShiftOffset get() = if (offset > 0) 1 else 0

    val itemTranslatePercent by derivedStateOf { translateOffset / itemSize / 2f + 1f }
    val before1TranslatePercent by derivedStateOf { itemTranslatePercent - 0.5f }
    val after1TranslatePercent by derivedStateOf { 1.5f - itemTranslatePercent }
    val after2TranslatePercent by derivedStateOf { 1f - itemTranslatePercent }

    fun onItemSizeChange(itemSize: Float) {
        this.itemSize = itemSize
    }

    fun onPreviouslySelectedChange(index: Int) {
        previousSelected = index
    }

    fun onOffsetChange(offset: Float) {
        this.offset = offset
    }
}
