package com.randomgametpnv.cameras.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.navigation.fragment.navArgs
import com.randomgametpnv.base.setInvisible
import com.randomgametpnv.cameras.R
import com.randomgametpnv.cameras.ui.base.BaseModuleFragment
import com.randomgametpnv.cameras.ui.utils.MyPlayerListener
import kotlinx.android.synthetic.main.fragment_camera.*
import org.videolan.libvlc.IVLCVout
import org.videolan.libvlc.LibVLC
import org.videolan.libvlc.Media
import org.videolan.libvlc.MediaPlayer
import java.util.*


class CameraFragment : BaseModuleFragment(), IVLCVout.Callback {

    val cameraId: CameraFragmentArgs by navArgs()

    // display surface
    var holder: SurfaceHolder? = null
    lateinit var surfaceView: SurfaceView

    // media player
    private var libvlc: LibVLC? = null
    private lateinit var mMediaPlayer: MediaPlayer
    private lateinit var mPlayerListener: MediaPlayer.EventListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        surfaceView = view.findViewById(R.id.surfaceView)
        mPlayerListener = MyPlayerListener(this)

        val cameraId = cameraId.cameraId
        val uri = "rtsp://admin:Bk173322@81.30.218.25:48554/pub/cam35";

        prepareVideoPlayer(uri)
    }

    fun prepareVideoPlayer(url: String) {

        holder = surfaceView.holder
        val options = ArrayList<String>()
        options.add("-vvv")
        options.add("--aout=opensles")
        options.add("--network-caching=1500")

        //options.add("--audio-time-stretch") // time stretching
        //options.add("--avcodec-codec=h264")

        libvlc = LibVLC(requireContext(), options)
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
        updateSize()
    }

    fun releasePlayer() {
        if (libvlc == null) return
        mMediaPlayer.stop()
        val vout = mMediaPlayer.vlcVout
        vout.removeCallback(this)
        vout.detachViews()
        holder = null
        libvlc?.release()
        libvlc = null
    }


    fun updateSize() {
        val width = requireActivity().window.decorView.width
        val height = width / 16 * 9
        mMediaPlayer.vlcVout.setWindowSize(width, height)
        mMediaPlayer.aspectRatio = "16:9"
    }


    override fun onSurfacesCreated(vlcVout: IVLCVout?) {}
    override fun onSurfacesDestroyed(vlcVout: IVLCVout?) {
        mMediaPlayer.setEventListener(null)
        releasePlayer()
    }

}
