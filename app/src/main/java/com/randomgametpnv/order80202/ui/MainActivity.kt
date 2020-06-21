package com.randomgametpnv.order80202.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.randomgametpnv.base.setInvisible
import com.randomgametpnv.base.setVisible
import com.randomgametpnv.main_screen.ui.utils.ControllerBundleKey
import com.randomgametpnv.main_screen.ui.utils.ControllerType
import com.randomgametpnv.order80202.R
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
                R.id.launchFragment -> { launch() }
                R.id.loginFragment -> { showLogin() }
                R.id.homeFragment -> { showHome() }
                else -> { showMain() }
            }
        }
        initBottomNavigation()
    }


    private fun launch() {
        include13.setInvisible()
    }

    private fun showLogin() {
        main_layout.setBackgroundResource(R.drawable.launch_img)
        lifecycleScope.launch {
            include13.setVisible()
            delay(15)
            home_include.visibility = View.INVISIBLE
            main_include.visibility = View.INVISIBLE
            login_include.visibility = View.VISIBLE
        }
    }

    private fun showHome() {
        lifecycleScope.launch {
            main_layout.setBackgroundResource(R.drawable.launch_img1)
            include13.setVisible()
            delay(15)
            home_include.visibility = View.VISIBLE
            main_include.visibility = View.INVISIBLE
            login_include.visibility = View.INVISIBLE
        }
    }

    private fun showMain() {
        lifecycleScope.launch {
            include13.setVisible()
            delay(15)
            home_include.visibility = View.INVISIBLE
            main_include.visibility = View.VISIBLE
            login_include.visibility = View.INVISIBLE
        }
    }

    private fun initBottomNavigation() {

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
                bundleOf(ControllerBundleKey.ControllerType.name to ControllerType.SECURITY.name),
                bottomNavOptions
            )
        }
        controlButton_b.setOnClickListener {
            navController.navigate(
                R.id.securityFragment,
                bundleOf(ControllerBundleKey.ControllerType.name to ControllerType.POWER.name),
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
