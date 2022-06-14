package org.fknives.android.compose.picker.time

import androidx.compose.runtime.Immutable
import java.util.Calendar

@Immutable
data class SelectedTime(
    val hour: Int,
    val minute: Int,
    val isAM: Boolean
) {

    companion object {
        fun get(time: Long = System.currentTimeMillis()): SelectedTime {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = time
            val hour = calendar.get(Calendar.HOUR)
            val minute = calendar.get(Calendar.MINUTE)
            val isAm = calendar.get(Calendar.AM_PM) == Calendar.AM

            return SelectedTime(
                hour = if (hour == 0) 12 else hour,
                minute = minute,
                isAM = isAm
            )
        }
    }
}
