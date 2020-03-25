package com.randomgametpnv.help.entities

data class AlarmUiData(val alarmText:String = "")


fun Alarms.toUiData(): AlarmUiData {
    return AlarmUiData()
}