package com.randomgametpnv.help.entities

import com.randomgametpnv.base.util.formatDate
import java.sql.Timestamp
import java.text.SimpleDateFormat

fun Journal.toUiData(): JournalUiData {

    val dateString = formatDate(this.stamp)
    return JournalUiData(this.id.toString(), this.message, dateString)
}

data class JournalUiData (val id: String, val text: String, val data: String)