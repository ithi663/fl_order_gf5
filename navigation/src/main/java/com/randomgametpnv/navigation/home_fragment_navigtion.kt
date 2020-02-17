package com.randomgametpnv.navigation

import android.app.Activity


fun Activity.homeFragmentNavId() = this.getNavigationId("homeFragment")
fun Activity.actionHomeFragmentToHelpNav() = this.getNavigationId("action_homeFragment_to_help_nav")
fun Activity.actionHomeFragmentToServicesNav() = this.getNavigationId("action_homeFragment_to_services_nav")
fun Activity.actionHomeFragmentToCountersNav() = this.getNavigationId("action_homeFragment_to_counters_nav")
fun Activity.actionHomeFragmentToControlFragment() = this.getNavigationId("action_homeFragment_to_controlFragment")
fun Activity.actionHomeFragmentToSecurityFragment() = this.getNavigationId("action_homeFragment_to_securityFragment")
fun Activity.actionHomeFragmentToCameraNav() = this.getNavigationId("action_homeFragment_to_camera_nav")
