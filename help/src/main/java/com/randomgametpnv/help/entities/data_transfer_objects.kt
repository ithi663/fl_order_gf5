package com.randomgametpnv.help.entities

fun Journal.toUiData(): JournalUiData {
    return JournalUiData(this.id.toString(), this.message, this.stamp)
}

fun Bills.toUiData(): BillsUiData {
    val _p0 = this.invoices?.firstOrNull()
    return BillsUiData(_p0?.toPay, _p0?.debt, _p0?.amount)
}