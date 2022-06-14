package org.fknives.android.compose.picker.text.content

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import org.fknives.android.compose.picker.text.util.TextPickerDefaults

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