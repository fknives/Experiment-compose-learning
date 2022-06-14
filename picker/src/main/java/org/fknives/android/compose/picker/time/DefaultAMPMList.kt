package org.fknives.android.compose.picker.time

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import java.text.DateFormatSymbols
import java.util.Locale

fun defaultAmPmStrings(locale: Locale = Locale.getDefault()) =
    DateFormatSymbols.getInstance(locale).amPmStrings.toList()

@Composable
fun rememberDefaultAMPMList(locale: Locale = Locale.getDefault()) = remember(locale) {
    DateFormatSymbols.getInstance(locale).amPmStrings.toList()
}