package org.fknives.android.compose.picker.text.content

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import org.fknives.android.compose.picker.text.util.TextPickerDefaults

/**
 * Default Text Label inside a [TextPicker][org.fknives.android.compose.picker.text.TextPicker]
 */
@Composable
fun DefaultNumberPickerText(
    text: String,
    modifier: Modifier
) {
    Text(
        text = text,
        modifier = modifier,
        textAlign = TextAlign.Center
    )
}

/**
 * Wraps [numberPickerLabel], to calculate the corresponding alpha from the given Translation.
 *
 * @param unselectedAlpha The alpha value an unselected element in [TextPicker][org.fknives.android.compose.picker.text.TextPicker] should have.
 * @param selectedAlpha The alpha value a selected element in [TextPicker][org.fknives.android.compose.picker.text.TextPicker] should have.
 * @param numberPickerLabel the actual label Composable.
 * @param modifier additional [Modifier] for [numberPickerLabel]
 *
 * As described in [TextPicker][org.fknives.android.compose.picker.text.TextPicker].
 * translation is the movement of the element:
 * - 1 -> selected
 * - 0.5 -> unselected
 * - 0 -> completely outside of boundaries.
 * - movement between translation are expected to be proper (such as setting alpha based on the translation linearly).
 *
 * This mediator, calculates the linear alpha in the following way:
 * - 1.0 -> [selectedAlpha] is shown
 * - 0.75 -> middle way linearly  between [selectedAlpha] and [unselectedAlpha]
 * - 0.5f -> [unselectedAlpha]
 * - 0.25 -> middle way linearly between [unselectedAlpha] and 0 Alpha.
 * - 0.0 -> completely gone 0 Alpha.
 */
fun defaultNumberPickerTextAlphaModifier(
    modifier: Modifier = Modifier,
    unselectedAlpha: Float = TextPickerDefaults.unselectedAlpha,
    selectedAlpha: Float = TextPickerDefaults.selectedAlpha,
    numberPickerLabel: @Composable (text: String, alpha: Float) -> Unit = { text, alpha ->
        DefaultNumberPickerText(modifier = modifier.alpha(alpha), text = text)
    }
): @Composable (String, Float) -> Unit = { text: String, translation: Float ->
    val calculatedAlpha = if (translation < 0.5f) {
        val between0And1Proportionally = (translation * 2)
        between0And1Proportionally * unselectedAlpha
    } else {
        val between0And1Proportionally = (translation - 0.5f) * 2
        between0And1Proportionally * (selectedAlpha - unselectedAlpha) + unselectedAlpha
    }

    numberPickerLabel(alpha = calculatedAlpha, text = text)
}