package org.fknives.android.compose.picker.text

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.core.spring
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.fknives.android.compose.picker.text.util.OffsetLimiter
import org.fknives.android.compose.picker.text.util.TextPickerDefaults
import kotlin.math.abs
import kotlin.math.roundToInt

class TextPickerAnimator(
    private val flingDecaySpec: DecayAnimationSpec<Float>,
    private val flingAnimationSpec: AnimationSpec<Float> = spring(0.75f),
    private val velocityMultiplier: Float,
    private val offsetLimiter: OffsetLimiter,
    private val onIndexDifferenceChanging: (Int) -> Unit = TextPickerDefaults.onIndexDifferenceChanging
) {

    private var animationJob by mutableStateOf<Job?>(null)

    fun onDeltaY(state: TextPickerState, deltaY: Float) {
        modifyOffset(state, state.offset + deltaY)
    }

    suspend fun snapToIndex(itemCount: Int, state: TextPickerState) {
        if (state.previousSelected == state.selected) return
        val scrollingIndexChange = findIndexChangeForProperScrollingAnimation(
            fromIndex = state.previousSelected,
            toIndex = state.selected,
            itemCount = itemCount
        )
        val offset = scrollingIndexChange * state.itemSize
        animateOffset(initialValue = offset, targetValue = 0f, state = state)
    }

    fun flingAndSnap(scope: CoroutineScope, state: TextPickerState, rawVelocity: Float, onIndexChange: (Int) -> Unit) {
        val velocity = rawVelocity * velocityMultiplier
        val flingOffsetValue = flingDecaySpec.calculateTargetValue(0f, velocity)
        val endFlingValue = state.offset + flingOffsetValue
        val limitedEndFlingValue = offsetLimiter.limit(endFlingValue, state)
        val changeIndex = -(limitedEndFlingValue / state.itemSize).roundToInt()
        val offsetTarget = -changeIndex * state.itemSize
        animationJob?.cancel()
        animationJob = scope.launch {
            animateOffset(initialValue = state.offset, targetValue = offsetTarget, velocity = velocity, state = state)
            onIndexChange(changeIndex)
            modifyOffset(state, 0f)
        }
    }

    private suspend fun animateOffset(state: TextPickerState, initialValue: Float, targetValue: Float, velocity: Float = 0f) {
        animate(
            initialValue = initialValue,
            targetValue = targetValue,
            animationSpec = flingAnimationSpec,
            initialVelocity = velocity
        ) { value, _ ->
            modifyOffset(state, value)
        }
    }

    private fun modifyOffset(state: TextPickerState, offset: Float) {
        val animationLimitedOffset = offsetLimiter.overshootLimit(offset, state)
        state.onOffsetChange(animationLimitedOffset)
        if (onIndexDifferenceChanging !== TextPickerDefaults.onIndexDifferenceChanging) {
            val hardLimitedOffset = offsetLimiter.limit(offset, state)
            val fullIndex = (-hardLimitedOffset / state.itemSize).toInt()
            val halfIndexChange = ((-hardLimitedOffset - fullIndex * state.itemSize)*2 / state.itemSize).toInt()
            val index = halfIndexChange + fullIndex
            onIndexDifferenceChanging(index)
        }
    }

    companion object {

        private fun findIndexChangeForProperScrollingAnimation(fromIndex: Int, toIndex: Int, itemCount: Int): Int {
            val indexDifference = toIndex - fromIndex
            return if (shouldNotScrollByDistance(indexDifference = indexDifference)) {
                0
            } else if (shouldScrollUpByDistance(indexDifference = indexDifference, itemCount = itemCount)) {
                calculateIndexChangeScrollingUp(indexDifference = indexDifference, itemCount = itemCount)
            } else {
                calculateIndexChangeScrollingDown(indexDifference = indexDifference, itemCount = itemCount)
            }
        }

        private fun shouldNotScrollByDistance(indexDifference: Int) = indexDifference == 0

        private fun calculateIndexChangeScrollingUp(indexDifference: Int, itemCount: Int): Int =
            if (indexDifference < 0) {
                indexDifference
            } else {
                indexDifference - itemCount
            }

        private fun calculateIndexChangeScrollingDown(indexDifference: Int, itemCount: Int): Int =
            if (indexDifference > 0) {
                indexDifference
            } else {
                indexDifference + itemCount
            }

        private fun shouldScrollUpByDistance(indexDifference: Int, itemCount: Int): Boolean {
            val scrollUpIndexDifference = calculateIndexChangeScrollingUp(indexDifference = indexDifference, itemCount = itemCount)
            val scrollUpDistance = abs(scrollUpIndexDifference)
            return scrollUpDistance < itemCount / 2
        }
    }
}