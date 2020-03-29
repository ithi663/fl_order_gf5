package com.randomgametpnv.help.entities

import com.randomgametpnv.base.util.AppDataFormat

fun Journal.toUiData(): JournalUiData {

    val dateString = AppDataFormat.fromApiDateTo_dd_MM_yyyy(stamp)
    return JournalUiData(this.id.toString(), this.message, dateString)
}

data class JournalUiData (val id: String, val text: String, val data: String)