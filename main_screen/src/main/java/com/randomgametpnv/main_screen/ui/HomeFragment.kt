package com.randomgametpnv.main_screen.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.randomgametpnv.main_screen.R
import com.randomgametpnv.main_screen.ui.base.BaseModuleFragment
import com.randomgametpnv.navigation.*
import com.randomgametpnv.sip.util.checkSipService
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseModuleFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        requireActivity().checkSipService (
            "tesst",
            "xxx27146",
            "sip.antisip.com"
        )


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
            findNavController().navigate(navInt)
        }

        controlButton.setOnClickListener {
            val navId = activity?.actionHomeFragmentToControlFragment()?: return@setOnClickListener
            findNavController().navigate(navId)
        }

        cameraButton.setOnClickListener {
            val navId = activity?.actionHomeFragmentToCameraNav()?: return@setOnClickListener
            findNavController().navigate(navId)
        }
    }
}
