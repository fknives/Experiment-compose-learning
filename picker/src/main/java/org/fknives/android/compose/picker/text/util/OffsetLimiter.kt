package org.fknives.android.compose.picker.text.util

import org.fknives.android.compose.picker.text.TextPickerState
import kotlin.math.max
import kotlin.math.min

/**
 * Scrolling Offset Limiter intended for [TextPickerAnimator][org.fknives.android.compose.picker.text.TextPickerAnimator].
 *
 * Provides to [default]s:
 * - [NoLimit] Used when scrolling should not be limited
 * (like RoundAround ([TextPicker][org.fknives.android.compose.picker.text.TextPicker])
 * - [MinMaxLimit] Used when scrolling should be limited to maximum scroll of up to First Element and down to LastElement.
 */
fun interface OffsetLimiter {

    /**
     * Hard limit on offset, to ensure indexes are within range.
     */
    fun limit(offset: Float, state: TextPickerState): Float

    /**
     * Soft limit on offset, to enable overshooting for animations.
     */
    fun overshootLimit(offset: Float, state: TextPickerState): Float =
        limit(offset, state)

    class NoLimit : OffsetLimiter {
        override fun limit(offset: Float, state: TextPickerState): Float = offset
    }

    class MinMaxLimit : OffsetLimiter {

        override fun limit(offset: Float, state: TextPickerState): Float {
            val max = maximalOffset(state)
            val min = minimalOffset(state)
            return max(min(offset, max), min)
        }

        private fun maximalOffset(state: TextPickerState) = state.selected * state.itemSize

        private fun minimalOffset(state: TextPickerState) = (state.selected + 1 - state.itemCount) * state.itemSize

        override fun overshootLimit(offset: Float, state: TextPickerState): Float {
            val max = maximalOffset(state) + state.itemSize / 2
            val min = minimalOffset(state) - state.itemSize / 2
            return max(min(offset, max), min)
        }
    }

    companion object {

        fun default(roundAround: Boolean): OffsetLimiter =
            if (roundAround) NoLimit() else MinMaxLimit()
    }
}