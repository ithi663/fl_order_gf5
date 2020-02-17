package com.randomgametpnv.cameras.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.randomgametpnv.base.initTopHeader
import com.randomgametpnv.cameras.R
import kotlinx.android.synthetic.main.fragment_camera_groups.*
import org.koin.android.ext.android.inject


class CameraGroupsFragment : BaseModuleFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera_groups, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val topText = resources.getText(com.randomgametpnv.base.R.string.video).toString()
        this.initTopHeader(topText = topText, arrowVisibility = true, view = view)

        button.setOnClickListener {
            findNavController().navigate(R.id.action_cameraGroupsFragment_to_allCamerasFragment, bundleOf("groupId" to "1"))
        }
        button2.setOnClickListener {
            findNavController().navigate(R.id.action_cameraGroupsFragment_to_allCamerasFragment, bundleOf("groupId" to "1"))
        }
        button3.setOnClickListener {
            findNavController().navigate(R.id.action_cameraGroupsFragment_to_allCamerasFragment, bundleOf("groupId" to "1"))
        }
        button4.setOnClickListener {
            findNavController().navigate(R.id.action_cameraGroupsFragment_to_allCamerasFragment, bundleOf("groupId" to "1"))
        }
        button5.setOnClickListener {
            findNavController().navigate(R.id.action_cameraGroupsFragment_to_allCamerasFragment, bundleOf("groupId" to "1"))
        }
        button6.setOnClickListener {
            findNavController().navigate(R.id.action_cameraGroupsFragment_to_allCamerasFragment, bundleOf("groupId" to "1"))
        }
    }
}
