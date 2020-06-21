package com.randomgametpnv.navigation

import android.app.Activity

fun Activity.actionLoginFragmentToHomeFragmentId() = this.getNavigationId("action_loginFragment_to_homeFragment")
fun Activity.actionLaunchFragmentToHomeFragmentId() = this.getNavigationId("action_launchFragment_to_homeFragment")
fun Activity.actionLaunchFragmentToLoginFragmentId() = this.getNavigationId("action_launchFragment_to_loginFragment")