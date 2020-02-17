package com.randomgametpnv.sip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CallActivity : AppCompatActivity() {

    lateinit var callReceiver: IncomingCallReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)
    }
}
