package com.randomgametpnv.sip.ui

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.os.PowerManager
import android.util.Log
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.randomgametpnv.sip.CallService
import com.randomgametpnv.sip.R
import com.randomgametpnv.sip.entities.SipCallState


class CallActivity : AppCompatActivity() {

    var mBound = false
    var service: CallService? = null
    lateinit var wakeLock: PowerManager.WakeLock


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)



        Log.d("QWEIUQOWIUE", "start")

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);


        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN or
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
            WindowManager.LayoutParams.FLAG_FULLSCREEN or
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        )


        wakeLock = wakeLock()

        service?.getCallEvents()?.observe(this, Observer {

            Log.d("QWEIUQOWIUE", "observe")

            when (it) {
                is SipCallState.IncomingCall -> {
                    Log.d("QWEIUQOWIUE", "IncomingCall")
                }
                is SipCallState.ActiveConversation -> {}
                is SipCallState.NoActiveState -> {
                    Log.d("QWEIUQOWIUE", "end Call")
                    wakeLock.release()
                    finishAffinity()
                }
            }
        })
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

    private fun wakeLock(): PowerManager.WakeLock {
        return (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
            newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "SmartHouse::Wakelock").apply {
                acquire()
            }
        }
    }


    override fun onStart() {
        super.onStart()
        if (!isMyServiceRunning(CallService::class.java)) {
            CallService.startService(applicationContext)
        }
        val intent = Intent(this, CallService::class.java)
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        wakeLock.release()
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
