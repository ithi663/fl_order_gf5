package com.randomgametpnv.cameras.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.randomgametpnv.base.initTopHeader
import com.randomgametpnv.base.showErrorMessage
import com.randomgametpnv.cameras.R
import com.randomgametpnv.cameras.entities.CameraDataUi
import com.randomgametpnv.cameras.ui.adapter.AllCamerasRvAdapter
import com.randomgametpnv.cameras.ui.base.BaseModuleFragment
import com.randomgametpnv.common_value_objects.ApiCall
import kotlinx.android.synthetic.main.fragment_all_cameras.*


class AllCamerasFragment : BaseModuleFragment(), AllCamerasRvAdapter.Interaction {

    //private val allCamerasFragmentArgs: AllCamerasFragmentArgs by navArgs()

    private var adapter: AllCamerasRvAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_all_cameras, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //val cameraId = allCamerasFragmentArgs.groupId
        val topText = resources.getText(com.randomgametpnv.base.R.string.video).toString()
        this.initTopHeader(topText = "$topText" /*$cameraId*/, arrowVisibility = true, view = view)


        initRV()
        viewModel.res.observe(this.viewLifecycleOwner, Observer {

            when(it) {
                is ApiCall.Loading -> {
                    cameraProgress.visibility = View.VISIBLE
                }
                is ApiCall.Success -> {
                    cameraProgress.visibility = View.INVISIBLE
                    adapter?.submitList(it.data)
                }
                is ApiCall.ResponseError -> {
                    showErrorMessage(it)
                    cameraProgress.visibility = View.INVISIBLE
                }
                is ApiCall.ConnectException -> {
                    showErrorMessage(it)
                    cameraProgress.visibility = View.INVISIBLE
                }
            }
        })
    }


    private fun initRV() {
        allCamerasRV.adapter = adapter ?: AllCamerasRvAdapter(this).also { adapter = it }
        allCamerasRV.layoutManager = GridLayoutManager(this.context, 2)
    }

    override fun onItemSelected(position: Int, item: CameraDataUi) {
        val direction
                = AllCamerasFragmentDirections.actionAllCamerasFragmentToCameraFragment(item.videoUrl)
        findNavController().navigate(direction)
    }

}
