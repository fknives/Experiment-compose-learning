package org.fknives.android.compose.picker.text.util

import androidx.compose.animation.splineBasedDecay
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import org.fknives.android.compose.picker.text.TextPickerAnimator
import org.fknives.android.compose.picker.text.TextPickerState

/**
 * Helper function caching [TextPickerState]
 */
@Composable
fun rememberTextPickerState(selected: Int, itemCount: Int): TextPickerState {
    require(selected >= 0) { "Selected value ($selected) is less than 0!" }
    require(selected < itemCount) { "Selected value ($selected) is more or equal to ItemCount = $itemCount!" }

    val itemSizeState = remember { mutableStateOf(1f) }
    val previousSelected = remember { mutableStateOf(selected) }
    return remember(selected, itemCount) {
        TextPickerState(
            itemSizeState = itemSizeState,
            previousSelectedState = previousSelected,
            selected = selected,
            itemCount = itemCount
        )
    }
}

/**
 * Helper function caching [TextPickerAnimator]
 */
@Composable
fun rememberTextPickerAnimator(
    roundAround: Boolean,
    onIndexDifferenceChanging: (Int) -> Unit = TextPickerDefaults.onIndexDifferenceChanging,
    velocityMultiplier: Float = TextPickerDefaults.velocityMultiplier,
    density: Density = LocalDensity.current,
): TextPickerAnimator {
    require(velocityMultiplier > 0) { "0 Velocity would not work" }
    val offsetLimiter = remember(roundAround) { OffsetLimiter.default(roundAround) }
    return rememberTextPickerAnimator(
        offsetLimiter = offsetLimiter,
        velocityMultiplier = velocityMultiplier,
        density = density,
        onIndexDifferenceChanging = onIndexDifferenceChanging
    )
}

/**
 * Helper function caching [TextPickerAnimator]
 */
@Composable
fun rememberTextPickerAnimator(
    offsetLimiter: OffsetLimiter,
    velocityMultiplier: Float = TextPickerDefaults.velocityMultiplier,
    density: Density = LocalDensity.current,
    onIndexDifferenceChanging: (Int) -> Unit = TextPickerDefaults.onIndexDifferenceChanging
): TextPickerAnimator {
    require(velocityMultiplier > 0) { "0 Velocity would not work" }
    return remember(offsetLimiter, velocityMultiplier, density, onIndexDifferenceChanging) {
        TextPickerAnimator(
            offsetLimiter = offsetLimiter,
            flingDecaySpec = splineBasedDecay(density),
            velocityMultiplier = velocityMultiplier,
            onIndexDifferenceChanging = onIndexDifferenceChanging
        )
    }
}

/**
 * Creates a Lambda changing index based on [roundAround].
 *
 * If roundAround is False, means the index is not touched, since it should not behave like a wheel.
 * IF roundAround is True, means the index is feed into [moveUnsafeToProperIndex] to behave like a wheel.
 */
@Composable
fun rememberDefaultMoveUnsafeToProperIndex(itemCount: Int, roundAround: Boolean) =
    if (roundAround) {
        remember(itemCount) { createMoveUnsafeToProperIndex(itemCount = itemCount) }
    } else {
        remember { { index: Int -> index } }
    }

/**
 * Curry function for [moveUnsafeToProperIndex] which stores the [itemCount]
 *
 * @return lambda expecting the unsafeIndex to call [moveUnsafeToProperIndex]
 */
fun createMoveUnsafeToProperIndex(itemCount: Int) = moveUnsafeToProperIndex@{ unsafeIndex: Int ->
    moveUnsafeToProperIndex(unsafeIndex, itemCount)
}

/**
 * Moves a given [unsafeIndex] between `0 until [itemCount]` like it is a continuous wheel.
 * Meaning that `unsafeIndex=-1` becomes `itemCount-1` while `unsafeIndex=itemCount` becomes 0.
 *
 */
fun moveUnsafeToProperIndex(unsafeIndex: Int, itemCount: Int): Int {
    var index = unsafeIndex
    while (index < 0) {
        index += itemCount
    }
    while (index >= itemCount) {
        index -= itemCount
    }
    return index
}

/**
 * Caches function accessing text for given index safely.
 * [roundAround] True means the Index will be modified if it needs to be to fall between the safe range for [textForIndex]
 * [roundAround] False means the if the Index is falls outside of safeZone, [textForUnsafeIndex] will be used as fallback.
 * Note: unsafeIndex means the index NOT between `0 until itemCount`.
 */
@Composable
fun rememberSafeTextForIndex(
    textForUnsafeIndex: String = "",
    itemCount: Int,
    roundAround: Boolean,
    textForIndex: (Int) -> String,
    moveUnsafeToProperIndex: (Int) -> Int
): (Int) -> String =
    if (roundAround) {
        remember(moveUnsafeToProperIndex, textForIndex) {
            createSafeRoundAroundTextForIndex(moveUnsafeToProperIndex = moveUnsafeToProperIndex, textForIndex = textForIndex)
        }
    } else {
        remember(itemCount, textForIndex) {
            createSafeOrDefaultTextForIndex(itemCount = itemCount, textForIndex = textForIndex, textForUnsafeIndex = textForUnsafeIndex)
        }
    }

/**
 * Returns lambda, which ensures the index is moved to into safe position by [moveUnsafeToProperIndex] before accessing [textForIndex]
 */
fun createSafeRoundAroundTextForIndex(moveUnsafeToProperIndex: (Int) -> Int, textForIndex: (Int) -> String): (Int) -> String =
    { index: Int ->
        textForIndex(moveUnsafeToProperIndex(index))
    }

/**
 * Returns lambda, which ensures the index is between `0 until itemCount` before accessing [textForIndex], fallbacks [textForUnsafeIndex]
 */
fun createSafeOrDefaultTextForIndex(textForUnsafeIndex: String = "", itemCount: Int, textForIndex: (Int) -> String): (Int) -> String =
    { index: Int ->
        if (index in 0 until itemCount) textForIndex(index) else textForUnsafeIndex
    }