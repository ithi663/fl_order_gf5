package com.randomgametpnv.help.entities

data class BillsUiData(val toPay: Int?, val debt: Int?, val amount: Int?)


fun List<Bills>.toUiData(): BillsUiData {
    val _p0 = this.firstOrNull()
    return BillsUiData(_p0?.toPay, _p0?.debt, _p0?.amount)
}