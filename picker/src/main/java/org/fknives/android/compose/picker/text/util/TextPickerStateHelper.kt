package org.fknives.android.compose.picker.text.util

import androidx.compose.animation.splineBasedDecay
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import org.fknives.android.compose.picker.text.TextPickerAnimator
import org.fknives.android.compose.picker.text.TextPickerState

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

@Composable
fun rememberDefaultMoveUnsafeToProperIndex(itemCount: Int, roundAround: Boolean) =
    if (roundAround) {
        remember(itemCount) { createMoveUnsafeToProperIndex(itemCount = itemCount) }
    } else {
        remember { { index: Int -> index } }
    }


fun createMoveUnsafeToProperIndex(itemCount: Int) = moveUnsafeToProperIndex@{ unsafeIndex: Int ->
    moveUnsafeToProperIndex(unsafeIndex, itemCount)
}

fun moveUnsafeToProperIndex(unsafeIndex: Int, itemCount: Int) : Int {
    var index = unsafeIndex
    while (index < 0) {
        index += itemCount
    }
    while (index >= itemCount) {
        index -= itemCount
    }
    return index
}

@Composable
fun rememberWrappedTextForIndex(itemCount: Int, roundAround: Boolean, textForIndex: (Int) -> String): (Int) -> String =
    if (roundAround) {
        textForIndex
    } else {
        remember(itemCount, textForIndex) {
            { index -> if (index < 0 || index >= itemCount) "" else textForIndex(index) }
        }
    }

