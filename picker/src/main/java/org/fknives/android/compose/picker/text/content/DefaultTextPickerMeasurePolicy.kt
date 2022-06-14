package org.fknives.android.compose.picker.text.content

import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Constraints
import org.fknives.android.compose.picker.text.TextPickerState

open class DefaultTextPickerMeasurePolicy(private val state: TextPickerState) : MeasurePolicy {

    open val numberOfItemsToPlace = 4
    open val numberOfHeightMeasuringItems = 3

    override fun MeasureScope.measure(measurables: List<Measurable>, constraints: Constraints): MeasureResult {
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }

        val placeablesHeight = measureHeight(placeables)
        val minWidth = measureWidth(placeables)
        state.onItemSizeChange(placeablesHeight / numberOfHeightMeasuringItems.toFloat())

        return layout(minWidth, placeablesHeight) {
            var yPosition = state.translateOffset.toInt()

            placeables.take(numberOfItemsToPlace).forEach { placeable ->
                placeable.placeRelative(x = 0, y = yPosition)
                yPosition += placeable.height
            }
            placementAfterItems(placeables, state)
        }
    }

    open fun Placeable.PlacementScope.placementAfterItems(placeables: List<Placeable>, state: TextPickerState) = Unit

    open fun measureWidth(placeables: List<Placeable>) =
        placeables.take(numberOfItemsToPlace).maxOf { it.measuredWidth }

    open fun measureHeight(placeables: List<Placeable>) =
        placeables.take(numberOfHeightMeasuringItems).sumOf { it.measuredHeight }
}