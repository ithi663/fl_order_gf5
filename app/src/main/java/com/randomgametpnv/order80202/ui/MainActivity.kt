package com.randomgametpnv.order80202.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.randomgametpnv.order80202.R
import com.randomgametpnv.sip.util.checkAndAskForBatteryOptimization
import com.randomgametpnv.sip.util.checkPermissions
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_footer.*
import kotlinx.android.synthetic.main.main_scr.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private val bottomNavOptions: NavOptions =
        NavOptions
            .Builder()
            .setLaunchSingleTop(true)
            .setPopUpTo(R.id.homeFragment, false)
            .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)

        //window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        navController = findNavController(R.id.fragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->

            when (destination.id) {
                R.id.loginFragment -> {
                    showLogin()
                }
                R.id.homeFragment -> {
                    showHome()
                }
                else -> {
                    showMain()
                }
            }
        }

        initInitBottomNavigation()
    }

    private fun showLogin() {
        main_layout.setBackgroundResource(R.drawable.launch_img)
        lifecycleScope.launch {
            delay(15)
            home_include.visibility = View.INVISIBLE
            main_include.visibility = View.INVISIBLE
            login_include.visibility = View.VISIBLE
        }
    }

    private fun showHome() {
        lifecycleScope.launch {
            main_layout.setBackgroundResource(R.drawable.launch_img1)
            delay(15)
            home_include.visibility = View.VISIBLE
            main_include.visibility = View.INVISIBLE
            login_include.visibility = View.INVISIBLE
        }
    }

    private fun showMain() {
        lifecycleScope.launch {
            delay(15)
            home_include.visibility = View.INVISIBLE
            main_include.visibility = View.VISIBLE
            login_include.visibility = View.INVISIBLE
        }
    }

    private fun initInitBottomNavigation() {

        cameraButton_b.setOnClickListener {
            navController.navigate(
                R.id.camera_nav,
                null,
                bottomNavOptions
            )
        }
        securityButton_b.setOnClickListener {
            navController.navigate(
                R.id.securityFragment,
                null,
                bottomNavOptions
            )
        }
        controlButton_b.setOnClickListener {
            navController.navigate(
                R.id.controlFragment,
                null,
                bottomNavOptions
            )
        }
        servicesButton_b.setOnClickListener {
            navController.navigate(
                R.id.services_nav,
                null,
                bottomNavOptions
            )
        }
        helpButton_b.setOnClickListener {
            navController.navigate(
                R.id.help_nav,
                null,
                bottomNavOptions
            )
        }
    }
}
