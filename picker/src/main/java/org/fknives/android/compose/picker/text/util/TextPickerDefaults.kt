package org.fknives.android.compose.picker.text.util

import androidx.compose.ui.unit.dp

/**
 * Default values for [TextPicker][org.fknives.android.compose.picker.text.TextPicker]
 */
object TextPickerDefaults {
    const val selected = 0
    const val velocityMultiplier = 0.3f
    const val unselectedAlpha = 0.6f
    const val selectedAlpha = 1f
    /**
     * [TextPickerAnimator][org.fknives.android.compose.picker.text.TextPickerAnimator] checks against this to not emit unnecessary updates.
     */
    internal val onIndexDifferenceChanging: (Int) -> Unit = {}

    const val roundAround = true
    const val dividerAlpha = 0.12f
    val dividerThickness = 2.dp
    val dividerStartIndent = 0.dp
}