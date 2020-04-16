package com.randomgametpnv.sip.entities

sealed class NetState {

    data class Active(val type: String, val changeTime: Long): NetState()
    data class Lose(val changeTime: Long): NetState()
}