@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.example.kadesubmission3

import android.view.View
import java.text.SimpleDateFormat
import java.util.*

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun String.parseLineup(delimiter: String = "; ", replacement: String = ";\n" ): String {
    return this.replace(delimiter, replacement)
}

fun String.parseDetail(delimiter: String = ";", replacement: String = ";\n"): String {
    return this.replace(delimiter, replacement)
}

fun String.formatDate(oldFormat: String="dd/MM/yy", newFormat: String = "EEE, d MMM yyyy"): String {
    val date = SimpleDateFormat(oldFormat, Locale.US).parse(this)
    val format = SimpleDateFormat(newFormat, Locale.US)
    return format.format(date)
}

fun String.formatTime(oldFormat: String="HH:mm:ss", newFormat: String = "hh:mm aa"): String {
    val time = SimpleDateFormat(oldFormat, Locale.US).parse(this)
    val format = SimpleDateFormat(newFormat, Locale.US)
    format.timeZone = TimeZone.getTimeZone("Asia/Jakarta")
    return format.format(time)
}