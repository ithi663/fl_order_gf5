package com.randomgametpnv.cameras.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.randomgametpnv.base.initTopHeader
import com.randomgametpnv.cameras.R
import com.randomgametpnv.cameras.ui.base.BaseModuleFragment
import kotlinx.android.synthetic.main.fragment_camera_groups.*


class CameraGroupsFragment : BaseModuleFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_camera_groups, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val topText = resources.getText(com.randomgametpnv.base.R.string.video).toString()
        this.initTopHeader(topText = topText, arrowVisibility = true, view = view)

        button.setOnClickListener {navigate("1")}
        button2.setOnClickListener {navigate("1")}
        button3.setOnClickListener {navigate("1")}
        button4.setOnClickListener {navigate("1")}
        button5.setOnClickListener {navigate("1")}
        button6.setOnClickListener {navigate("1")}
    }

    fun navigate(cameraGroup: String) {

        val action
                = CameraGroupsFragmentDirections.actionCameraGroupsFragmentToAllCamerasFragment(cameraGroup)
        findNavController().navigate(action)
    }
}
