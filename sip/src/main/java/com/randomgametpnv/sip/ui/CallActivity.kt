package com.randomgametpnv.sip.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.PowerManager
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.video_player.AppVideoPlayer
import com.randomgametpnv.base.R
import com.randomgametpnv.base.initTopHeader
import com.randomgametpnv.base.setInvisible
import com.randomgametpnv.base.setVisible
import com.randomgametpnv.sip.CallService
import com.randomgametpnv.sip.databinding.ActivityCallBinding
import com.randomgametpnv.sip.entities.SipCallState
import kotlinx.android.synthetic.main.activity_call.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.scope.scope


class CallActivity : AppCompatActivity() {

    private var mBound = false
    private var incomingCall = false
    private var service: CallService? = null
    private lateinit var binding: ActivityCallBinding
    private var appPlayer: AppVideoPlayer? = null
    private var state: SipCallState? = null
    private var endJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.initTopHeader(topText = getString(com.randomgametpnv.sip.R.string.sip_top_text), arrowVisibility = true, view = binding.root)

        //init player
        appPlayer = AppVideoPlayer(this, binding.surfaceView, binding.sipProgressBar)
        appPlayer?.playVideo("rtsp://admin:Bk173322@81.30.218.25:48554/pub/cam35")

        wakeLock()
        initDisplayFullScr()
        val intent = Intent(this, CallService::class.java)
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE)

        //init buttons
        rejectButton.setOnClickListener { service?.endCall() }
        answerCallButton.setOnClickListener {
            when (service?.getCallEvents()?.value) {
                is SipCallState.IncomingCall -> {service?.pickUp()}
            }
        }
        openDoorButton.setOnClickListener {
            service?.open()
        }
    }

    private fun initDisplayFullScr() {

        this.window.setFlags(
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        )
    }


    private fun wakeLock(): PowerManager.WakeLock {
        return (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
            newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "SmartHouse::Wakelock").apply {
                acquire()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d("GJGJGJ", "onStop()")
/*        appPlayer?.releaseAppPlayer()
        service?.endCall()
        if (mBound) {
            unbindService(mConnection)
            mBound = false
        }*/
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("GJGJGJ", "onDestroy()")
        appPlayer?.releaseAppPlayer()
        service?.endCall()
        if (mBound) {
            unbindService(mConnection)
            mBound = false
        }
    }

    private val mConnection = object : ServiceConnection {
        override fun onServiceDisconnected(p0: ComponentName?) { mBound = false }
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            val localBinder = p1 as CallService.LocalBinder
            service = localBinder.service
            service?.getCallEvents()?.observe(this@CallActivity, createCallListener())
            mBound = true
        }
    }

    private fun createCallListener() = Observer<SipCallState> {
        state = it
        when (it) {
            is SipCallState.IncomingCall -> {
                incomingCall = true
            }
            is SipCallState.ActiveConversation -> { }
            is SipCallState.NoActiveState -> {
                lifecycleScope.launch {
                    if (incomingCall) {
                        delay(1000)
                        finish()
                    }
                }
            }
        }
    }


    private fun initTopHeader(topText: String, arrowVisibility: Boolean, view: View) {

        val arrow: ImageView = view.findViewById(R.id.backArrow)
        val headText: TextView = view.findViewById(R.id.headText)

        if (arrowVisibility) arrow.setVisible()
        else arrow.setInvisible()
        arrow.setOnClickListener {
            service?.endCall()
            finish()
        }
        headText.text = topText
    }
}
