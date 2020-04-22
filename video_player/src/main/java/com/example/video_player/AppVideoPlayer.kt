package com.example.video_player

import android.app.Activity
import android.net.Uri
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.ProgressBar
import com.randomgametpnv.base.setInvisible
import org.videolan.libvlc.IVLCVout
import org.videolan.libvlc.LibVLC
import org.videolan.libvlc.Media
import org.videolan.libvlc.MediaPlayer
import java.util.*

class AppVideoPlayer(
    private val activity: Activity,
    private val surfaceView: SurfaceView,
    private val progressBar: ProgressBar
): IVLCVout.Callback {

    // display surface
    private var holder: SurfaceHolder? = surfaceView.holder

    // media player
    private var libvlc: LibVLC? = null
    private lateinit var mMediaPlayer: MediaPlayer
    private var mPlayerListener: MediaPlayer.EventListener? = null


    fun releaseAppPlayer() {
        releasePlayer()
    }

    fun playVideo(url: String) {

        mPlayerListener = createEventListener()

        holder = surfaceView.holder
        val options = ArrayList<String>()
        options.add("-vvv")
        options.add("--aout=opensles")
        options.add("--network-caching=1500")

        libvlc = LibVLC(activity, options)
        holder?.setKeepScreenOn(true)

        // Create media player
        mMediaPlayer = MediaPlayer(libvlc)
        mMediaPlayer.setEventListener(mPlayerListener)

        val vout = mMediaPlayer.vlcVout
        vout.setVideoView(surfaceView)
        vout.addCallback(this)
        vout.attachViews()

        val m = Media(libvlc, Uri.parse(url))

        mMediaPlayer.media = m
        mMediaPlayer.play()
        //updateSize()
    }

    private fun updateSize() {
        val width = activity.window.decorView.width
        val height = width / 16 * 9
        mMediaPlayer.vlcVout.setWindowSize(width, height)
        mMediaPlayer.aspectRatio = "16:9"
    }

    private fun releasePlayer() {
        if (libvlc == null) return
        mMediaPlayer.stop()
        val vout = mMediaPlayer.vlcVout
        vout.removeCallback(this)
        vout.detachViews()
        mPlayerListener = null
        holder = null
        libvlc?.release()
        libvlc = null
    }


    override fun onSurfacesCreated(vlcVout: IVLCVout?) {}
    override fun onSurfacesDestroyed(vlcVout: IVLCVout?) {
        mMediaPlayer.setEventListener(null)
        releasePlayer()
    }

    private fun createEventListener() = MediaPlayer.EventListener {

        if (it.buffering == 100f) {
            progressBar.setInvisible()
        }

        when (it.type) {
            MediaPlayer.Event.EndReached -> {
                releasePlayer()
            }

            MediaPlayer.Event.Playing -> {
                updateSize()
            }

            MediaPlayer.Event.Paused, MediaPlayer.Event.Stopped -> { }
            else -> { }
        }
    }

}