package com.randomgametpnv.cameras.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.randomgametpnv.base.initTopHeader
import com.randomgametpnv.cameras.R
import com.randomgametpnv.cameras.entities.CameraData
import com.randomgametpnv.cameras.ui.adapter.AllCamerasRvAdapter
import kotlinx.android.synthetic.main.fragment_all_cameras.*
import org.koin.android.ext.android.inject


class AllCamerasFragment : BaseModuleFragment(), AllCamerasRvAdapter.Interaction {


    private var adapter: AllCamerasRvAdapter? = null
    private val testData = listOf(
        CameraData("1", "testUrsl1", "imgUrl1", 1000),
        CameraData("2", "testUrsl2", "imgUrl2", 1000),
        CameraData("3", "testUrsl3", "imgUrl3", 1000),
        CameraData("4", "testUrsl4", "imgUrl4", 1000),
        CameraData("5", "testUrsl5", "imgUrl5", 1000),
        CameraData("6", "testUrsl6", "imgUrl6", 1000),
        CameraData("7", "testUrsl7", "imgUrl7", 1000),
        CameraData("8", "testUrsl8", "imgUrl8", 1000),
        CameraData("9", "testUrsl9", "imgUrl9", 1000),
        CameraData("11", "testUrsl11", "imgUrl11", 1000),
        CameraData("12", "testUrsl12", "imgUrl12", 1000),
        CameraData("13", "testUrsl13", "imgUrl13", 1000),
        CameraData("14", "testUrsl14", "imgUrl14", 1000)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_cameras, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val cameraId = arguments?.getString("groupId", null)?: throw Throwable("cameraId is null")
        val topText = resources.getText(com.randomgametpnv.base.R.string.video).toString()
        this.initTopHeader(topText = "$topText $cameraId", arrowVisibility = true, view = view)

        initRV()
        adapter?.submitList(testData)

        /*button1.setOnClickListener {
            findNavController().navigate(R.id.action_allCamerasFragment_to_cameraFragment)
        }*/
    }


    private fun initRV() {
        allCamerasRV.adapter = adapter ?: AllCamerasRvAdapter(this).also { adapter = it }
        allCamerasRV.layoutManager = GridLayoutManager(this.context, 2)
    }

    override fun onItemSelected(position: Int, item: CameraData) {
        findNavController().navigate(R.id.action_allCamerasFragment_to_cameraFragment)
    }

}
