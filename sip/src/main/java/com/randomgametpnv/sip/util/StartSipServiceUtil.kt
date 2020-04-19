package com.randomgametpnv.sip.util

import android.Manifest
import android.app.Activity
import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.randomgametpnv.sip.CallService
import com.randomgametpnv.sip.util.sip_manager.CustomConfig

@Suppress("DEPRECATION")
fun Activity.checkSipService(login: String, pass: String, host: String) {

    val serviceIsRunning = (getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
        .getRunningServices(Int.MAX_VALUE)
        .any { it.service.className == CallService::javaClass.name }

    if (!serviceIsRunning) {
        CallService.startService(applicationContext)
    }

    val userConfig = object : CustomConfig() {
        override fun getPassword(): String {return pass}
        override fun getDomain(): String {return host}
        override fun getUserPart(): String {return login}
    }
    bindService(
        Intent(this, CallService::class.java),
        object : ServiceConnection {

            override fun onServiceDisconnected(p0: ComponentName?) {}

            override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
                val localBinder = p1 as CallService.LocalBinder
                localBinder.service.checkRegistration(userConfig)
            }
        },
        Context.BIND_AUTO_CREATE
    )
}

fun Activity.checkPermissions() {

    ActivityCompat.requestPermissions(this,
        arrayOf(
            Manifest.permission.USE_SIP,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.FOREGROUND_SERVICE,
            Manifest.permission.MODIFY_AUDIO_SETTINGS,
            Manifest.permission.VIBRATE
        ),
        1012)

    /*if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.USE_SIP)
            != PackageManager.PERMISSION_GRANTED) {


        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.USE_SIP)) {

        } else {
            ActivityCompat.requestPermissions(this,
                    arrayOf(
                            Manifest.permission.USE_SIP,
                            Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.FOREGROUND_SERVICE),
                    1012)

        }
    } else {
    }*/
}