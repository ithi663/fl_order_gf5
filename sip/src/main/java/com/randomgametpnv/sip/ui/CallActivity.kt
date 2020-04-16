package com.randomgametpnv.sip.ui

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.randomgametpnv.sip.CallService
import com.randomgametpnv.sip.R
import com.randomgametpnv.sip.entities.SipCallState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CallActivity : AppCompatActivity() {

    var mBound = false
    var service: CallService? = null
    var callStatus: SipCallState =
        SipCallState.NoActiveState


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)
    }


    private fun observe() {

        lifecycleScope.launch {
            delay(1000)

            service?.sipManager?.state?.observe(this@CallActivity, Observer {
                callStatus = it
                when(it) {
                    /*is SipServiceState.ActiveConversation -> {
                        eventText.text = it.sipAddress
                    }
                    is SipServiceState.Registering -> {
                        eventText.text = it.message
                    }
                    is SipServiceState.NoActiveState -> { eventText.text = "---" }
                    is SipServiceState.Ringing -> { eventText.text = "дозвон: "+it.sipAddress}
                    is SipServiceState.RegisteringSuccess -> { eventText.text = it.message }
                    is SipServiceState.RegisteringError -> { eventText.text = it.message }
                    is SipServiceState.IncomingCall -> { eventText.text = it.sipAddress }
                    is SipServiceState.Unreg -> {eventText.text = it.message}*/
                }
            })
        }
    }


    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }



    override fun onStart() {
        super.onStart()
        val intent = Intent(this, CallService::class.java)
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        if (mBound) {
            unbindService(mConnection)
            mBound = false
        }
    }



    private val mConnection = object : ServiceConnection {

        override fun onServiceDisconnected(p0: ComponentName?) {
            mBound = false
        }

        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            val localBinder = p1 as CallService.LocalBinder
            service = localBinder.service
            mBound = true
        }
    }
}
