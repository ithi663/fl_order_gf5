package com.randomgametpnv.cameras.ui.utils

import android.util.Log
import com.randomgametpnv.cameras.ui.CameraFragment
import org.videolan.libvlc.MediaPlayer
import java.lang.ref.WeakReference

internal class MyPlayerListener(owner: CameraFragment) : MediaPlayer.EventListener {
    private val mOwner: WeakReference<CameraFragment> = WeakReference<CameraFragment>(owner)
    override fun onEvent(event: MediaPlayer.Event) {
        val player: CameraFragment? = mOwner.get()
        when (event.type) {
            MediaPlayer.Event.EndReached -> {
                Log.d(TAG, "MediaPlayerEndReached")
                player?.releasePlayer()
            }
            MediaPlayer.Event.Playing, MediaPlayer.Event.Paused, MediaPlayer.Event.Stopped -> {
            }
            else -> {
            }
        }
    }

    companion object {
        private const val TAG = "PlayerListener"
    }

}