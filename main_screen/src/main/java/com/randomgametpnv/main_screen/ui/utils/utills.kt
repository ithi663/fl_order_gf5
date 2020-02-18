package com.randomgametpnv.main_screen.ui.utils

import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import com.randomgametpnv.main_screen.R

fun View.fastRotation() {
    animation = AnimationUtils.loadAnimation(this.context, R.anim.rotation_anim_fast)
        .also { it.interpolator = LinearInterpolator() }
}

fun View.slowRotation() {
    animation = AnimationUtils.loadAnimation(this.context, R.anim.rotation_anim_slow)
        .also { it.interpolator = LinearInterpolator() }
}


enum class Status {LOADING, LOADED}
enum class ControllerType {SECURITY, POWER}