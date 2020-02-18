package com.randomgametpnv.main_screen.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.randomgametpnv.base.initTopHeader
import com.randomgametpnv.base.setInvisible
import com.randomgametpnv.base.setVisible
import com.randomgametpnv.common_value_objects.ApiCall
import com.randomgametpnv.main_screen.R
import com.randomgametpnv.main_screen.ui.base.BaseControllerFragment
import com.randomgametpnv.main_screen.ui.utils.ControllerType
import com.randomgametpnv.main_screen.ui.utils.Status
import com.randomgametpnv.main_screen.ui.utils.fastRotation
import kotlinx.android.synthetic.main.fragment_control.*

class PowerControlFragment : BaseControllerFragment() {

    private lateinit var strPowerOff: String
    private lateinit var strPowerOn: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_control, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val topText =
            resources.getText(com.randomgametpnv.base.R.string.power_management).toString()
        this.initTopHeader(topText = topText, arrowVisibility = true, view = view)

        controllerType = ControllerType.POWER
        strPowerOff = resources.getString(R.string.power_off)
        strPowerOn = resources.getString(R.string.power_on)

        initState()
        initButton(powerButton)

        viewModel.powerRes.observe(this.viewLifecycleOwner, Observer {

            when (it) {
                is ApiCall.ResponseError -> {
                    requestStatus = Status.LOADED
                    loadWithError()
                }
                is ApiCall.Loading -> {
                    requestStatus = Status.LOADING
                    loadStatus.fastRotation()
                }
                is ApiCall.ConnectException -> {
                    requestStatus = Status.LOADED
                    loadWithError()
                }
                is ApiCall.Success -> {
                    requestStatus = Status.LOADED
                    status = it.data.status
                    changeStatus(it.data.status)
                }
            }
        })

        viewModel.getPowerStatus()
    }



    private fun initState() {

        powerButton.setImageResource(R.drawable.power_non)
        loadStatus.fastRotation()
        loadStatus.clearAnimation()
        loadStatus.setInvisible()
        power_text.setInvisible()
        power_img.setInvisible()
    }

    override fun changeStatus(status: Boolean) {

        if (status) {

            power_text.setVisible()
            power_img.setVisible()

            power_text.text = strPowerOn
            power_text.setTextColor(resources.getColor(R.color.text_green))
            power_img.setImageResource(R.drawable.power_top_on_img)
            powerButton.setImageResource(R.drawable.power_on_img)
        } else {

            power_text.setVisible()
            power_img.setVisible()

            power_text.text = strPowerOff
            power_text.setTextColor(resources.getColor(R.color.text_read))
            power_img.setImageResource(R.drawable.power_top_off_img)
            powerButton.setImageResource(R.drawable.power_off_img)
        }
    }

}
