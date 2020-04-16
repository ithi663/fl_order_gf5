package com.randomgametpnv.sip.util

import android.content.Context
import android.media.MediaPlayer
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.VibrationEffect
import android.os.Vibrator

class DonDigidonHandler(private val context: Context) {

    private var ringtone: Ringtone? = null
    private var vibeService: Vibrator? = null

    fun callInvite() {

        val vibService = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibService.vibrate(500)

        val ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        ringtone = RingtoneManager.getRingtone(context, ringtoneUri)
        ringtone?.play()
    }

    fun stopPlaying() {

        ringtone?.stop()
        vibeService?.cancel()
    }
}