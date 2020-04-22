package com.randomgametpnv.sip.util.sip_manager

import android.media.*
import android.media.audiofx.AcousticEchoCanceler
import net.sourceforge.peers.media.AbstractSoundManager

class SipAudioManager : AbstractSoundManager() {

    private var recorder: AudioRecord? = null
    private var player: AudioTrack? = null
    private val sampleRate = 8000
    private val channelConfig = AudioFormat.CHANNEL_IN_MONO
    private val audioFormat = AudioFormat.ENCODING_PCM_16BIT
    private var minBufSize = 0


    override fun init() {

        try {

            if (recorder?.state == AudioRecord.STATE_INITIALIZED) return

            minBufSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)
            recorder = AudioRecord(
                MediaRecorder.AudioSource.MIC,
                sampleRate,
                channelConfig,
                audioFormat,
                minBufSize
            )
            recorder?.audioSessionId?.let { echoCanceler(it) }
            recorder?.startRecording()

            // Initialize player
            player = AudioTrack(
                AudioManager.STREAM_MUSIC,
                sampleRate,
                AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                minBufSize,
                AudioTrack.MODE_STREAM
            )
            player?.playbackRate = sampleRate
            player?.play()
        } catch (e: Throwable) { }
    }

    override fun close() {

        try {

            if (recorder?.state == AudioRecord.STATE_UNINITIALIZED) return
            recorder?.stop()
            recorder?.release()

            if (player?.state == AudioTrack.STATE_UNINITIALIZED) return
            player?.stop()
            player?.release()
        } catch (e: Throwable) {}
    }

    override fun readData(): ByteArray? {
        return try {
            val buffer = ByteArray(minBufSize)
            minBufSize = recorder?.read(buffer, 0, buffer.size)?: 0
            buffer
        } catch (e: Throwable) {
            null
        }
    }

    override fun writeData(buffer: ByteArray, offset: Int, length: Int): Int {
        return try {
            return player?.write(buffer, offset, buffer.size)?:0
        } catch (e:Throwable) {0}
    }

    private fun echoCanceler(audioSessionId: Int) {
        try {
            val echo = AcousticEchoCanceler.create(audioSessionId)
            if (echo?.enabled == false) {
                echo.enabled = true
            }
        } catch (e: Throwable) { }
    }
}
