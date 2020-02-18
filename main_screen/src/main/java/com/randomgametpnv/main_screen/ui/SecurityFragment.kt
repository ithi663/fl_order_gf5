package com.randomgametpnv.main_screen.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.randomgametpnv.base.initTopHeader
import com.randomgametpnv.base.setInvisible
import com.randomgametpnv.base.setVisible
import com.randomgametpnv.common_value_objects.ApiCall
import com.randomgametpnv.main_screen.R
import com.randomgametpnv.main_screen.ui.utils.ControllerType
import com.randomgametpnv.main_screen.ui.utils.Status
import com.randomgametpnv.main_screen.ui.utils.fastRotation
import com.randomgametpnv.main_screen.ui.utils.slowRotation
import kotlinx.android.synthetic.main.fragment_security.*

class SecurityFragment : BaseControllerFragment() {

    private lateinit var strSecureOff: String
    private lateinit var strSecureOn: String

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

        controllerType = ControllerType.SECURITY
        strSecureOff = resources.getString(R.string.secure_off)
        strSecureOn = resources.getString(R.string.secure_on)

        initState()
        initButton(secureButton)

        viewModel.secureRes.observe(this.viewLifecycleOwner, Observer {

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
        viewModel.getSecurityStatus()
    }


    private fun initState() {

        secureButton.setImageResource(R.drawable.secure_off_non)
        loadStatus.fastRotation()
        loadStatus.clearAnimation()
        loadStatus.setInvisible()
        secure_text.setInvisible()
        secure_img.setInvisible()
    }


    override fun changeStatus(status: Boolean) {

        if (status) {

            secure_text.setVisible()
            secure_img.setVisible()

            secure_text.text = strSecureOn
            secure_text.setTextColor(resources.getColor(R.color.text_green))
            secure_img.setImageResource(R.drawable.secure_off_on_img)
            secureButton.setImageResource(R.drawable.secure_on_img)
        } else {

            secure_text.setVisible()
            secure_img.setVisible()

            secure_text.text = strSecureOff
            secure_text.setTextColor(resources.getColor(R.color.text_read))
            secure_img.setImageResource(R.drawable.secure_top_off_img)
            secureButton.setImageResource(R.drawable.securu_off_img)
        }
    }

}
