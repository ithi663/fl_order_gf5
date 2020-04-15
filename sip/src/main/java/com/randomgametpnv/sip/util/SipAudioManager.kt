package com.randomgametpnv.sip.util

import android.media.*
import android.media.audiofx.AcousticEchoCanceler
import net.sourceforge.peers.media.AbstractSoundManager

class SipAudioManager : AbstractSoundManager() {

    var recorder: AudioRecord? = null
    var player: AudioTrack? = null
    private val sampleRate = 8000
    private val channelConfig = AudioFormat.CHANNEL_IN_MONO
    private val audioFormat = AudioFormat.ENCODING_PCM_16BIT
    var minBufSize = 0


    override fun init() {

        if(recorder?.state == AudioRecord.STATE_INITIALIZED) return

        minBufSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)
        recorder = AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate, channelConfig, audioFormat, minBufSize)
        recorder?.startRecording()
        recorder?.audioSessionId?.let { echoCanceler(it) }

        // Initialize player
        player = AudioTrack(AudioManager.STREAM_MUSIC, sampleRate, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT, minBufSize, AudioTrack.MODE_STREAM)
        player?.playbackRate = sampleRate
        player?.play()
    }

    override fun close() {

        if(recorder?.state == AudioRecord.STATE_UNINITIALIZED) return
        recorder?.stop()
        recorder?.release()

        player?.stop()
        player?.release()
    }

    override fun readData(): ByteArray? {
        val buffer = ByteArray(minBufSize)
        minBufSize = recorder?.read(buffer, 0, buffer.size)?: 0
        return buffer
    }

    override fun writeData(buffer: ByteArray, offset: Int, length: Int): Int {
        return player?.write(buffer, offset, buffer.size)?:0
    }

    private fun echoCanceler(audioSessionId: Int) {
        val echo = AcousticEchoCanceler.create(audioSessionId)
        if (echo?.enabled == false) {
            echo.enabled = true
        }
    }
}
