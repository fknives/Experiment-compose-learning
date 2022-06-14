package org.fknives.android.compose.learning.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun AppTheme(content: @Composable () -> Unit) = MaterialTheme(

    colors = if (isSystemInDarkTheme()) {
        darkColors(
            primary = Color(0xFF7C43BD),
            primaryVariant = Color(0xFF7C43BD),
            secondary = Color(0xFF50B04A),
            secondaryVariant = Color(0xFF50B04A),
            background = Color(0xFF000000),
            surface = Color(0xFF1A1A1A),
            error = Color(0xFFFF5050),
            onPrimary = Color(0xFFFFFFFF),
            onSecondary = Color(0xFFFFFFFF),
            onBackground = Color(0xFFFFFFFF),
            onSurface = Color(0xFFFFFFFF),
            onError = Color(0xFF000000)
        )
    } else {
        lightColors(
            primary = Color(0xFF7C43BD),
            primaryVariant = Color(0xFF7C43BD),
            secondary = Color(0xFF50B04A),
            secondaryVariant = Color(0xFF50B04A),
            background = Color(0xFFFFFFFF),
            surface = Color(0xFFA1A1A1),
            error = Color(0xFFFF5050),
            onPrimary = Color(0xFFFFFFFF),
            onSecondary = Color(0xFFFFFFFF),
            onBackground = Color(0xFFFFFFFF),
            onSurface = Color(0xFFFFFFFF),
            onError = Color(0xFF000000)
        )
    },
    content = content
)