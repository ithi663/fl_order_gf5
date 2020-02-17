package com.randomgametpnv.cameras.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import com.randomgametpnv.cameras.R
import org.koin.android.ext.android.inject


class CameraFragment : BaseModuleFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val uri = "rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov";
        val v: VideoView = view.findViewById (R.id.videoView);
        v.setVideoURI(Uri.parse(uri));
        //v.setMediaController(MediaController (this.context!!));
        v.requestFocus();
        v.start();
    }
}
