package com.randomgametpnv.main_screen.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.randomgametpnv.main_screen.R
import com.randomgametpnv.main_screen.ui.base.BaseModuleFragment
import com.randomgametpnv.main_screen.ui.utils.ControllerBundleKey
import com.randomgametpnv.main_screen.ui.utils.ControllerType
import com.randomgametpnv.navigation.*
import com.randomgametpnv.sip.util.checkAndAskForBatteryOptimization
import com.randomgametpnv.sip.util.checkPermissions
import com.randomgametpnv.sip.util.checkSipService
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseModuleFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().checkAndAskForBatteryOptimization()
        requireActivity().checkPermissions()
        requireActivity().checkSipService (
            "tesst",
            "xxx27146",
            "sip.antisip.com"
        )

/*        requireActivity().checkSipService (
            "kyjw",
            "hh6a",
            "office2.2242000.ru:5060"
        )*/


        helpButton.setOnClickListener {
            val navId = activity?.actionHomeFragmentToHelpNav()?: return@setOnClickListener
            findNavController().navigate(navId)
        }

        servicesButton.setOnClickListener {
            val navId = activity?.actionHomeFragmentToServicesNav()?: return@setOnClickListener
            findNavController().navigate(navId)
        }

        countersButton.setOnClickListener {
            val navInt = activity?.actionHomeFragmentToCountersNav()?: return@setOnClickListener
            findNavController().navigate(navInt)
        }

        securityButton.setOnClickListener {
            val navInt = activity?.actionHomeFragmentToSecurityFragment()?: return@setOnClickListener
            findNavController().navigate(navInt, bundleOf(ControllerBundleKey.ControllerType.name to ControllerType.SECURITY.name))
        }

        controlButton.setOnClickListener {
            val navId = activity?.actionHomeFragmentToSecurityFragment()?: return@setOnClickListener
            findNavController().navigate(navId, bundleOf(ControllerBundleKey.ControllerType.name to ControllerType.POWER.name))
        }

        cameraButton.setOnClickListener {
            val navId = activity?.actionHomeFragmentToCameraNav()?: return@setOnClickListener
            findNavController().navigate(navId)
        }
    }
}
