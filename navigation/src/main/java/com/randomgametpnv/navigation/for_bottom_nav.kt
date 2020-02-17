package com.randomgametpnv.navigation

import android.app.Activity

fun Activity.loginFragment() = this.getNavigationId("loginFragment")
fun Activity.homeFragment() = this.getNavigationId("homeFragment")