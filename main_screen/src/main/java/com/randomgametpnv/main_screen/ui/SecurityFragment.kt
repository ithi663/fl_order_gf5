package com.randomgametpnv.main_screen.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.randomgametpnv.base.initTopHeader
import com.randomgametpnv.base.setInvisible
import com.randomgametpnv.base.setVisible
import com.randomgametpnv.main_screen.R
import com.randomgametpnv.main_screen.ui.utils.fastRotation
import com.randomgametpnv.main_screen.ui.utils.slowRotation
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
        initButton()

        secureButton.setOnTouchListener { view, motionEvent ->

            when(motionEvent.action) {

                MotionEvent.ACTION_UP -> {
                    load_status.fastRotation()
                    load_status.setInvisible()
                }
                MotionEvent.ACTION_DOWN -> {
                    load_status.slowRotation()
                    load_status.setVisible()
                }
            }

            false
        }


        secureButton.setOnLongClickListener {

            changeStatus()
            true
        }
    }


    fun initButton() {

        secureButton.setImageResource(R.drawable.secure_off_non)
        load_status.fastRotation()
        load_status.clearAnimation()
        load_status.setInvisible()
        secure_text.setInvisible()
        secure_img.setInvisible()
    }


    private fun changeStatus() {

        if (!defVal) {

            secure_text.setVisible()
            secure_img.setVisible()

            defVal = true
            secure_text.text = strSecureOn
            secure_text.setTextColor(resources.getColor(R.color.text_green))
            secure_img.setImageResource(R.drawable.secure_off_on_img)
            secureButton.setImageResource(R.drawable.secure_on_img)
            load_status.clearAnimation()
            load_status.setInvisible()
        } else {

            secure_text.setVisible()
            secure_img.setVisible()

            defVal = false
            secure_text.text = strSecureOff
            secure_text.setTextColor(resources.getColor(R.color.text_read))
            secure_img.setImageResource(R.drawable.secure_top_off_img)
            secureButton.setImageResource(R.drawable.securu_off_img)
            load_status.clearAnimation()
            load_status.setInvisible()
        }


    }
}
