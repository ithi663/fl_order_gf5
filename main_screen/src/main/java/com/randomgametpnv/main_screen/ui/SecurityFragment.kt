package com.randomgametpnv.main_screen.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.randomgametpnv.base.initTopHeader
import com.randomgametpnv.base.setInvisible
import com.randomgametpnv.base.setVisible
import com.randomgametpnv.base.showErrorMessage
import com.randomgametpnv.common_value_objects.ApiCall
import com.randomgametpnv.main_screen.R
import com.randomgametpnv.main_screen.databinding.FragmentSecurityBinding
import com.randomgametpnv.main_screen.entities.ControllerDataUi
import com.randomgametpnv.main_screen.ui.base.BaseModuleFragment
import com.randomgametpnv.main_screen.ui.utils.*
import kotlinx.coroutines.*
import kotlin.properties.Delegates

class SecurityFragment : BaseModuleFragment() {


    private var currentState: Boolean = false
    private var longPress: Boolean = false
    private val longClickTime: Long = 3000
    private val uiEvents: MutableLiveData<UiStatus> = MutableLiveData()
    private lateinit var binding: FragmentSecurityBinding

    private var controllerImageOff by Delegates.notNull<Int>()
    private var controllerImageOn by Delegates.notNull<Int>()
    private lateinit var topText: String
    private lateinit var controllerTextOff: String
    private lateinit var controllerTextOn: String
    private var controllerButtonOff by Delegates.notNull<Int>()
    private var controllerButtonOn by Delegates.notNull<Int>()
    private var controllerButtonNon by Delegates.notNull<Int>()
    private lateinit var controllerType: ControllerType
    private var pressScope: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.getString(ControllerBundleKey.ControllerType.name)?.let {
            when (it) {
                ControllerType.SECURITY.name -> {
                    controllerButtonOff = R.drawable.securu_off_img
                    controllerButtonOn = R.drawable.secure_on_img
                    controllerButtonNon = R.drawable.secure_non
                    controllerImageOff = R.drawable.secure_top_off_img
                    controllerImageOn = R.drawable.secure_off_on_img
                    controllerTextOff = resources.getString(R.string.secure_off)
                    controllerTextOn = resources.getString(R.string.secure_on)
                    controllerType = ControllerType.SECURITY
                    topText = resources.getText(com.randomgametpnv.base.R.string.security).toString()
                }
                ControllerType.POWER.name -> {
                    controllerButtonOff = R.drawable.power_off_img
                    controllerButtonOn = R.drawable.power_on_img
                    controllerButtonNon = R.drawable.power_non
                    controllerImageOff = R.drawable.power_top_off_img
                    controllerImageOn = R.drawable.power_top_on_img
                    controllerTextOff = resources.getString(R.string.power_off)
                    controllerTextOn = resources.getString(R.string.power_on)
                    controllerType = ControllerType.POWER
                    topText = resources.getText(com.randomgametpnv.base.R.string.power_management).toString()
                }
            }
        }

        binding = FragmentSecurityBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.initTopHeader(topText = topText, arrowVisibility = true, view = view)

        viewModel.getControllerRequestStatus()

        viewModel.networkControllerEvents.observe(this.viewLifecycleOwner, Observer {
           handleNetworkEvents(it)
        })

        uiEvents.observe(this.viewLifecycleOwner, Observer {
            handleUiEvents(it)
        })

        binding.controllerButton.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_UP -> {handleLongPressEnd()}
                MotionEvent.ACTION_DOWN -> {handleLongPressStart()}
            }
            false
        }
    }


    private fun handleNetworkEvents(apiCall: ApiCall<ControllerDataUi>) {

        when (apiCall) {
            is ApiCall.ResponseError -> {
                showErrorMessage(apiCall)
                uiEvents.value = UiStatus.LOADED_ERROR
            }
            is ApiCall.Loading -> {
                uiEvents.value = UiStatus.LOADING
            }
            is ApiCall.ConnectException -> {
                showErrorMessage(apiCall)
                uiEvents.value = UiStatus.LOADED_ERROR
            }
            is ApiCall.Success -> {
                currentState = apiCall.data.status
                if (apiCall.data.status) {
                    uiEvents.value = UiStatus.LOADED_SECURE_ON
                } else {
                    uiEvents.value = UiStatus.LOADED_SECURE_OFF
                }
            }
        }
    }


    private fun handleUiEvents(status: UiStatus) {

        when (status) {
            UiStatus.INIT -> initState()
            UiStatus.LOADING -> loadingStatus()
            UiStatus.LOADED_SECURE_ON -> secureOn()
            UiStatus.LOADED_SECURE_OFF -> secureOff()
            UiStatus.LOADED_ERROR -> loadWithError()
            UiStatus.LONG_PRESS_START -> longPressStart()
            UiStatus.LONG_PRESS_SECCUSE -> longPressSeccuse()
            UiStatus.LONG_PRESS_FAIL -> longPressFail()
        }
    }

    private fun initState() {
        binding.controllerText.setInvisible()
        binding.controllerImg.setInvisible()
        binding.controllerButton.setImageResource(controllerButtonNon)
    }

    private fun loadingStatus() {
        binding.controllerProgressCircle.fastRotation()
        binding.controllerText.setInvisible()
        binding.controllerImg.setInvisible()
        binding.controllerButton.setImageResource(controllerButtonNon)
        binding.controllerButton.animate().translationY(0f).scaleX(1f).scaleY(1f).setDuration(150).start()
    }

    private fun secureOn(){
        binding.controllerProgressCircle.hideRotation()
        binding.controllerText.setVisible()
        binding.controllerImg.setVisible()
        binding.controllerText.text = controllerTextOn
        binding.controllerText.setTextColor(resources.getColor(R.color.text_green))
        binding.controllerImg.setImageResource(controllerImageOn)
        binding.controllerButton.setImageResource(controllerButtonOn)
        binding.controllerButton.animate().translationY(-4.1f).scaleX(1.03f).scaleY(1.03f).setDuration(150).start()
    }

    private fun secureOff() {
        binding.controllerProgressCircle.hideRotation()
        binding.controllerText.setVisible()
        binding.controllerImg.setVisible()
        binding.controllerText.text = controllerTextOff
        binding.controllerText.setTextColor(resources.getColor(R.color.text_read))
        binding.controllerImg.setImageResource(controllerImageOff)
        binding.controllerButton.setImageResource(controllerButtonOff)
        binding.controllerButton.animate().translationY(0f).scaleX(1f).scaleY(1f).setDuration(150).start()
    }


    private fun loadWithError() {
        binding.controllerProgressCircle.hideRotation()
        binding.controllerText.setInvisible()
        binding.controllerImg.setInvisible()
        binding.controllerButton.setImageResource(controllerButtonNon)
        binding.controllerButton.animate().translationY(0f).scaleX(1f).scaleY(1f).setDuration(150).start()
    }

    private fun longPressStart() {
        binding.controllerProgressCircle.slowRotation()
    }

    private fun longPressSeccuse() {
        viewModel.updateControllerStatus(controllerType, currentState)
    }

    private fun longPressFail() {
        binding.controllerProgressCircle.hideRotation()
    }

    private fun handleLongPressStart() {
        if (uiEvents.value == UiStatus.LONG_PRESS_START) return
        pressScope?.cancel()
        pressScope = lifecycleScope.launch {
            longPress = true
            uiEvents.value = UiStatus.LONG_PRESS_START
            delay(longClickTime)
            if(longPress) {
                longPress = false
                uiEvents.value = UiStatus.LONG_PRESS_SECCUSE
            }
        }
    }

    private fun handleLongPressEnd() {
        if (!longPress) return
        else {
            longPress = false
            uiEvents.value = UiStatus.LONG_PRESS_FAIL
        }
    }
}
