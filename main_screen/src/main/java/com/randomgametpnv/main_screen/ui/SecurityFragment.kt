package com.randomgametpnv.main_screen.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.randomgametpnv.base.initTopHeader
import com.randomgametpnv.main_screen.R
import kotlinx.android.synthetic.main.fragment_security.*

class SecurityFragment : BaseModuleFragment() {


    var defVal = true

    lateinit var strSecureOff: String
    lateinit var strSecureOn: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_security, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val topText = resources.getText(com.randomgametpnv.base.R.string.security).toString()
        this.initTopHeader(topText = topText, arrowVisibility = true, view = view)

        strSecureOff = resources.getString(R.string.secure_off)
        strSecureOn = resources.getString(R.string.secure_on)

        defVal = false


        load_status.visibility = View.INVISIBLE

        secureButton.setOnTouchListener { view, motionEvent ->

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


        secureButton.setOnLongClickListener {

            changeStatus()
            true
        }
    }


    private fun changeStatus() {

        if (!defVal) {

            defVal = true
            secure_text.text = strSecureOn
            secure_text.setTextColor(resources.getColor(R.color.text_green))
            secure_img.setImageResource(R.drawable.secure_off_on_img)
            load_status.visibility = View.INVISIBLE
            secureButton.setImageResource(R.drawable.secure_on_img)
        } else {

            defVal = false
            secure_text.text = strSecureOff
            secure_text.setTextColor(resources.getColor(R.color.text_read))
            secure_img.setImageResource(R.drawable.secure_top_off_img)
            load_status.visibility = View.INVISIBLE
            secureButton.setImageResource(R.drawable.securu_off_img)
        }


    }
}
