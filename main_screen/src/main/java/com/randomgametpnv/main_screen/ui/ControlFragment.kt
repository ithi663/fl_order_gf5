package com.randomgametpnv.main_screen.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.randomgametpnv.base.initTopHeader
import com.randomgametpnv.main_screen.R
import kotlinx.android.synthetic.main.fragment_control.*


class ControlFragment : BaseModuleFragment() {


    var defVal = true

    private lateinit var strPowerOff: String
    lateinit var strPowerOn: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_control, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val topText = resources.getText(com.randomgametpnv.base.R.string.power_management).toString()
        this.initTopHeader(topText = topText, arrowVisibility = true, view = view)

        strPowerOff = resources.getString(R.string.power_off)
        strPowerOn = resources.getString(R.string.power_on)

        defVal = false


        load_status.visibility = View.INVISIBLE

        powerButton.setOnTouchListener { view, motionEvent ->

            when(motionEvent.action) {

                MotionEvent.ACTION_UP -> {
                    load_status.visibility = View.INVISIBLE
                }
                MotionEvent.ACTION_DOWN -> {
                    load_status.visibility = View.VISIBLE
                }
            }

            false
        }


        powerButton.setOnLongClickListener {

            changeStatus()
            true
        }
    }


    private fun changeStatus() {

        if (!defVal) {

            defVal = true
            power_text.text = strPowerOn
            power_text.setTextColor(resources.getColor(R.color.text_green))
            power_img.setImageResource(R.drawable.power_top_on_img)
            load_status.visibility = View.INVISIBLE
            powerButton.setImageResource(R.drawable.power_on_img)
        } else {

            defVal = false
            power_text.text = strPowerOff
            power_text.setTextColor(resources.getColor(R.color.text_grey))
            power_img.setImageResource(R.drawable.power_top_off_img)
            load_status.visibility = View.INVISIBLE
            powerButton.setImageResource(R.drawable.power_off_img)
        }

    }
}
