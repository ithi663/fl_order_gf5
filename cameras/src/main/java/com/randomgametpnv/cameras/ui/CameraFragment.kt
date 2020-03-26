package com.randomgametpnv.cameras.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import androidx.navigation.fragment.navArgs
import com.randomgametpnv.base.setInvisible
import com.randomgametpnv.base.setVisible
import com.randomgametpnv.cameras.R
import com.randomgametpnv.cameras.ui.base.BaseModuleFragment
import kotlinx.android.synthetic.main.fragment_camera.*


class CameraFragment : BaseModuleFragment() {

    val cameraId: CameraFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cameraId = cameraId.cameraId


        Log.d("WEURYIUW", "## $cameraId")
        //val uri = "rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov";
        val videoView: VideoView = view.findViewById (R.id.videoView);

        videoView.setOnPreparedListener {
            videoView.setVisible()
            videoProgressBar.setInvisible()
        }

        videoView.setOnErrorListener { mediaPlayer, i, i2 ->
            videoProgressBar.setInvisible()
            videoView.setVisible()
            return@setOnErrorListener false
        }

        videoView.setVideoURI(Uri.parse(cameraId));
        videoView.requestFocus();
        videoView.start();
    }
}
