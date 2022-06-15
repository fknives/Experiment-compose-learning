package org.fknives.android.compose.picker.text.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

/**
 * Remembers the lapCounterByIndexOfDifference function with fixed [selectedIndex] and [itemCount].
 */
@Composable
fun rememberLapsCounterByOnIndexDifference(selectedIndex: Int, itemCount: Int) = remember(selectedIndex, itemCount) {
    createLapsCounterByOnIndexDifference(selectedIndex = selectedIndex, itemCount = itemCount)
}

/**
 * Curry function for [lapCounterByIndexOfDifference]
 * Keeps [itemCount] and [selectedIndex] for scope.
 *
 * @return Returned lambda expectes the indexDifference for the [lapCounterByIndexOfDifference] calculation.
 */
fun createLapsCounterByOnIndexDifference(itemCount: Int, selectedIndex: Int): (Int) -> Int = { indexDifference ->
    lapCounterByIndexOfDifference(itemCount = itemCount, indexDifference = indexDifference, selectedIndex = selectedIndex)
}

/**
 * Helper function intended to be used with onIndexDifferenceChanging of [TextPicker][org.fknives.android.compose.picker.text.TextPicker].
 *
 * Calculates how many times the TextPicker were scrolled over (first element -to-> last element by index decrease or vice versa)
 * Useful when OverScrolling needs some reaction.
 *
 * @param itemCount Number of items
 * @param selectedIndex the currently selected index, must be between `0 until [itemCount]`
 * @param indexDifference the current index difference from [selectedIndex].
 *
 * @return The Round Around Scrolling.
 *
 * Example1:
 * `itemCount = 3, selectedIndex = 0, indexDifference = -1.`
 * This results in -1, meaning the currently shown element is index 2. 1 overscroll happened from index 0 to index 2.
 *
 * Example2:
 * `itemCount = 3, selectedIndex = 0, indexDifference = -3.`
 * This results in -1, meaning the currently shown element is index 0. 1 overscroll happened from index 0 to index 2.
 *
 * Example3:
 * `itemCount = 3, selectedIndex = 0, indexDifference = -4.`
 * This results in -2, meaning the currently shown element is index 2. 2 overscroll happened from index 0 to index 2.
 *
 * Example3:
 * `itemCount = 3, selectedIndex = 0, indexDifference = 3.`
 * This results in 1, meaning the currently shown element is index 0. 1 overscroll happened from index 2 to index 0.
 *
 * Example3:
 * `itemCount = 3, selectedIndex = 0, indexDifference = 5.`
 * This results in 1, meaning the currently shown element is index 2. 1 overscroll happened from index 2 to index 0.
 */
fun lapCounterByIndexOfDifference(
    itemCount: Int,
    selectedIndex: Int,
    indexDifference: Int
): Int {
    var changedIndex = selectedIndex + indexDifference
    var counter = 0
    if (changedIndex < 0) {
        counter--
        while (changedIndex <= -itemCount) {
            counter--
            changedIndex += itemCount
        }
    } else {
        while (changedIndex >= itemCount) {
            counter++
            changedIndex -= itemCount
        }
    }

    return counter
}