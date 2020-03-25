package com.randomgametpnv.help.entities

fun Journal.toUiData(): JournalUiData {
    return JournalUiData(this.id.toString(), this.message, this.stamp)
}

data class JournalUiData (val id: String, val text: String, val data: String)