package com.randomgametpnv.cameras.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.randomgametpnv.base.initTopHeader
import com.randomgametpnv.cameras.R
import com.randomgametpnv.cameras.entities.CameraDataUi
import com.randomgametpnv.cameras.ui.adapter.AllCamerasRvAdapter
import com.randomgametpnv.cameras.ui.base.BaseModuleFragment
import kotlinx.android.synthetic.main.fragment_all_cameras.*


class AllCamerasFragment : BaseModuleFragment(), AllCamerasRvAdapter.Interaction {

    private val allCamerasFragmentArgs: AllCamerasFragmentArgs by navArgs()

    private var adapter: AllCamerasRvAdapter? = null
    private val testData = listOf(
        CameraDataUi("1", "testUrsl1", "imgUrl1"),
        CameraDataUi("2", "testUrsl2", "imgUrl2"),
        CameraDataUi("3", "testUrsl3", "imgUrl3"),
        CameraDataUi("4", "testUrsl4", "imgUrl4"),
        CameraDataUi("5", "testUrsl5", "imgUrl5"),
        CameraDataUi("6", "testUrsl6", "imgUrl6"),
        CameraDataUi("7", "testUrsl7", "imgUrl7"),
        CameraDataUi("8", "testUrsl8", "imgUrl8"),
        CameraDataUi("9", "testUrsl9", "imgUrl9"),
        CameraDataUi("11", "testUrsl11", "imgUrl11"),
        CameraDataUi("12", "testUrsl12", "imgUrl12"),
        CameraDataUi("13", "testUrsl13", "imgUrl13"),
        CameraDataUi("14", "testUrsl14", "imgUrl14")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_all_cameras, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val cameraId = allCamerasFragmentArgs.groupId
        val topText = resources.getText(com.randomgametpnv.base.R.string.video).toString()
        this.initTopHeader(topText = "$topText $cameraId", arrowVisibility = true, view = view)

        initRV()
        adapter?.submitList(testData)
    }


    private fun initRV() {
        allCamerasRV.adapter = adapter ?: AllCamerasRvAdapter(this).also { adapter = it }
        allCamerasRV.layoutManager = GridLayoutManager(this.context, 2)
    }

    override fun onItemSelected(position: Int, item: CameraDataUi) {
        findNavController().navigate(R.id.action_allCamerasFragment_to_cameraFragment)
    }

}
