package com.randomgametpnv.help.entities

data class AlarmUiData(val alarmText:String = "", val alarmDate: String = "")


fun Alarms.toUiData(): AlarmUiData {
    return AlarmUiData(this.open, this.stamp)
}