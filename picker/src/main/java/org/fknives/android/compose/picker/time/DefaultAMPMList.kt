package org.fknives.android.compose.picker.time

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import java.text.DateFormatSymbols
import java.util.Locale

/**
 * Returns Default AM, PM texts for given [locale].
 */
fun defaultAmPmStrings(locale: Locale = Locale.getDefault()) =
    DateFormatSymbols.getInstance(locale).amPmStrings.toList()

/**
 * Caches the [defaultAmPmStrings].
 */
@Composable
fun rememberDefaultAMPMList(locale: Locale = Locale.getDefault()) = remember(locale) {
    defaultAmPmStrings(locale = locale)
}