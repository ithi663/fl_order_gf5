package com.randomgametpnv.base.util

import java.text.SimpleDateFormat
import java.util.*


object AppDataFormat {

    private val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.US)
    private val formatCounters = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    fun fromApiDateTo_dd_MM_yyyy(apiDate: String): String {
        val date = format.parse(apiDate)
        return SimpleDateFormat("dd.MM.yyyy", Locale.US).format(date?: 0)
    }



    fun fromCountersApiDateToDate(apiDate: String): Date? = formatCounters.parse(apiDate)
    fun fromDateTo_dd_MM(date: Long) = SimpleDateFormat("dd.MM", Locale.US).format(date)
}