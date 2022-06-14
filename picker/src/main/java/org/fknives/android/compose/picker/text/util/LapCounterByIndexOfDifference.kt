package org.fknives.android.compose.picker.text.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

fun createLapsCounterByOnIndexDifference(itemCount: Int, selectedIndex: Int): (Int) -> Int = { indexDifference ->
    lapCounterByIndexOfDifference(itemCount = itemCount, indexDifference = indexDifference, selectedIndex = selectedIndex)
}

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

@Composable
fun rememberLapsCounterByOnIndexDifference(selectedIndex: Int, itemCount: Int) = remember(selectedIndex, itemCount) {
    createLapsCounterByOnIndexDifference(selectedIndex = selectedIndex, itemCount = itemCount)
}