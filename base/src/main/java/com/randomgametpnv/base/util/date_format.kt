package com.randomgametpnv.base.util

import java.text.SimpleDateFormat
import java.util.*

fun formatDate(date: String): String {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.US)
    val date = format.parse(date)
    return SimpleDateFormat("dd.MM.yyyy", Locale.US).format(date?: 0)
}