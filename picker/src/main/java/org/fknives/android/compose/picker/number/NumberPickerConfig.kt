package org.fknives.android.compose.picker.number

import androidx.compose.runtime.Immutable

@Immutable
data class NumberPickerConfig(
    val maximum: Int,
    val minimum: Int = 0,
    val reversedOrder: Boolean = false,
    val skipInBetween: Int = 0
) {
    init {
        require(skipInBetween >= 0) { "Skip In Between cannot be negative!" }
    }

    companion object {
        val configMinutePicker get() = NumberPickerConfig(
            minimum = 0,
            maximum = 59,
            reversedOrder = false,
            skipInBetween = 0
        )
        val configHourPicker24 get() = NumberPickerConfig(
            minimum = 0,
            maximum = 23,
            reversedOrder = false,
            skipInBetween = 0
        )
        val configHourPicker12 get() = NumberPickerConfig(
            minimum = 1,
            maximum = 12,
            reversedOrder = false,
            skipInBetween = 0
        )
    }
}