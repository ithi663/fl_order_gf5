package com.randomgametpnv.main_screen.ui.utils

import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import com.randomgametpnv.base.setInvisible
import com.randomgametpnv.base.setVisible
import com.randomgametpnv.main_screen.R

fun View.fastRotation() {
    this.setVisible()
    this.clearAnimation()
    animation = AnimationUtils.loadAnimation(this.context, R.anim.rotation_anim_fast)
        .also { it.interpolator = LinearInterpolator() }
}

fun View.slowRotation() {
    this.setVisible()
    this.clearAnimation()
    animation = AnimationUtils.loadAnimation(this.context, R.anim.rotation_anim_slow)
        .also { it.interpolator = LinearInterpolator() }
}

fun View.hideRotation() {
    this.clearAnimation()
    this.setInvisible()
}


enum class UiStatus {INIT, LOADING, LONG_PRESS_START, LONG_PRESS_SECCUSE, LONG_PRESS_FAIL, LOADED_SECURE_ON, LOADED_SECURE_OFF, LOADED_ERROR}
enum class ControllerType {SECURITY, POWER}
enum class ControllerBundleKey {ControllerType}