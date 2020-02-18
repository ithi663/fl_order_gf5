package com.randomgametpnv.main_screen.ui

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import com.randomgametpnv.base.setInvisible
import com.randomgametpnv.base.setVisible
import com.randomgametpnv.main_screen.R
import com.randomgametpnv.main_screen.ui.utils.ControllerType
import com.randomgametpnv.main_screen.ui.utils.Status
import com.randomgametpnv.main_screen.ui.utils.fastRotation
import com.randomgametpnv.main_screen.ui.utils.slowRotation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

open class BaseControllerFragment : BaseModuleFragment() {


    var requestStatus: Status = Status.LOADING
    var status: Boolean? = null
    private val longClickTime: Long = 3000
    lateinit var loadStatus: ImageView
    lateinit var controllerType: ControllerType


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadStatus = view.findViewById(R.id.load_status)
    }


    fun initButton(view: View) {

        view.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_UP -> { handleActionUp() }
                MotionEvent.ACTION_DOWN -> { handleActionDown() }
            }
            false
        }
    }


    private fun handleActionUp() {
        lifecycleScope.launch {
            if (requestStatus == Status.LOADING || status == null) return@launch
            delay(longClickTime)
            loadStatus.fastRotation()
            loadStatus.setInvisible()
        }
    }

    private fun handleActionDown() {
        lifecycleScope.launch {
            if (requestStatus == Status.LOADING || status == null) return@launch
            delay(longClickTime)
            loadStatus.slowRotation()
            loadStatus.setVisible()
            viewModel.updateControllerStatus(controllerType, status!!.not())
        }
    }

    fun loadWithError() {

        loadStatus.clearAnimation()
        loadStatus.setInvisible()
    }

    open fun changeStatus(status: Boolean) {

        if (status) {
            loadStatus.clearAnimation()
            loadStatus.setInvisible()
        } else {
            loadStatus.clearAnimation()
            loadStatus.setInvisible()
        }
    }
}