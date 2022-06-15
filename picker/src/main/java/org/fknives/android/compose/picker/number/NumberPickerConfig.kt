package org.fknives.android.compose.picker.number

import androidx.compose.runtime.Immutable

/**
 * Configuration of [NumberPicker]
 *
 * [minimum] The minimum value shown inside the [NumberPicker]. Minimum Value included in the List of Elements.
 * [maximum] The maximum value shown inside the [NumberPicker]. Maximum Value included in the List of Elements.
 * [reversedOrder] false, means goes from [minimum]..[maximum], true means [maximum]..[minimum]
 * [skipInBetween], number of elements skipped when going from one value to another.
 *
 * Example1:
 * minimum = 1, maximum = 3, reversedOrder = false, skipInBetween=0
 * =>
 * [1,2,3]
 * *Note: No skipping, no reverse, so all elements from 1 to 3*
 *
 * Example2:
 * minimum = 1, maximum = 3, reversedOrder = true, skipInBetween=0
 * =>
 * [3,2,1]
 * *Note: No skipping, reverse, so all elements from 3 to 1*
 *
 * Example3:
 * minimum = 1, maximum = 5, reversedOrder = false, skipInBetween=1
 * =>
 * [1,3,5]
 * *Note: Skipping 1 element, no reverse, so starting from 1. Then skipping 2, so 3. Then skipping 4 so 5.*
 *
 * Example4:
 * minimum = 1, maximum = 5, reversedOrder = true, skipInBetween=2
 * =>
 * [5,2]
 * *Note: Skipping 2 element, Reverse, so starting from 5. Then skipping 4 and 3, so 2. Next would be smaller than minimal so, that's it.*
 */
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