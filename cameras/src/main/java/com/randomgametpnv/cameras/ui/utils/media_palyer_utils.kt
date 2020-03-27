package com.randomgametpnv.cameras.ui.utils

import android.util.Log
import android.view.View
import com.randomgametpnv.base.setInvisible
import com.randomgametpnv.cameras.R
import com.randomgametpnv.cameras.ui.CameraFragment
import org.videolan.libvlc.MediaPlayer
import java.lang.ref.WeakReference

internal class MyPlayerListener(owner: CameraFragment) : MediaPlayer.EventListener {
    private val mOwner: WeakReference<CameraFragment> = WeakReference<CameraFragment>(owner)
    override fun onEvent(event: MediaPlayer.Event) {
        val player: CameraFragment? = mOwner.get()

        if (event.buffering == 100f) {
            player?.view?.findViewById<View>(R.id.videoProgressBar)?.setInvisible()
        }

        when (event.type) {
            MediaPlayer.Event.EndReached -> {
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