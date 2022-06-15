package org.fknives.android.compose.picker.time

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp
import org.fknives.android.compose.picker.number.NumberPickerScope
import org.fknives.android.compose.picker.text.util.TextPickerDefaults

/**
 * Scope setup from [TimePicker].
 *
 * Contains all the Values for the respective Hour, Minute and AMorPM Pickers.
 */
@Immutable
interface TimePickerScope {
    val timePickerMinWidth: Dp
    val hoursPickerScope: NumberPickerScope
    val minutesPickerScope: NumberPickerScope
    val amORpmPickerScope: AMorPMPickerScope
}

/**
 * Data class implementation of [TimePickerScope]
 */
@Immutable
data class TimePickerScopeImpl(
    override val timePickerMinWidth: Dp,
    override val hoursPickerScope: NumberPickerScope,
    override val minutesPickerScope: NumberPickerScope,
    override val amORpmPickerScope: AMorPMPickerScope
) : TimePickerScope

/**
 * Intended scope for AMorPM Picker.
 * Contained within [TimePickerScope]
 */
@Immutable
interface AMorPMPickerScope {
    val listOfAMorPM: List<String>
    val isAM: Boolean
    val onAMSelectedChange: (Boolean) -> Unit
    val onIndexDifferenceChanging: (Int) -> Unit
}

/**
 * Data class implementation of [AMorPMPickerScope]
 */
@Immutable
data class AMorPMPickerScopeImpl(
    override val listOfAMorPM: List<String>,
    override val isAM: Boolean,
    override val onAMSelectedChange: (Boolean) -> Unit,
    override val onIndexDifferenceChanging: (Int) -> Unit = TextPickerDefaults.onIndexDifferenceChanging
) : AMorPMPickerScope

/**
 * Caching function for [AMorPMPickerScope]
 */
@Composable
fun rememberAMorPMPickerScope(
    listOfAMorPM: List<String>,
    isAM: Boolean,
    onAMSelectedChange: (Boolean) -> Unit,
    onIndexDifferenceChanging: (Int) -> Unit = TextPickerDefaults.onIndexDifferenceChanging
): AMorPMPickerScope {

    return remember(listOfAMorPM, isAM, onAMSelectedChange, onIndexDifferenceChanging) {
        AMorPMPickerScopeImpl(
            listOfAMorPM = listOfAMorPM,
            isAM = isAM,
            onAMSelectedChange = onAMSelectedChange,
            onIndexDifferenceChanging = onIndexDifferenceChanging
        )
    }
}

/**
 * Caching function for [TimePickerScope]
 */
@Composable
fun rememberTimePickerScope(
    timePickerMinWidth: Dp,
    hoursPickerScope: NumberPickerScope,
    minutesPickerScope: NumberPickerScope,
    amORpmPickerScope: AMorPMPickerScope
) = remember(timePickerMinWidth, hoursPickerScope, minutesPickerScope, amORpmPickerScope) {
    TimePickerScopeImpl(
        timePickerMinWidth = timePickerMinWidth,
        hoursPickerScope = hoursPickerScope,
        minutesPickerScope = minutesPickerScope,
        amORpmPickerScope = amORpmPickerScope
    )
}