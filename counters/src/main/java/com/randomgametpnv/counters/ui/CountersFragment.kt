package com.randomgametpnv.counters.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.randomgametpnv.base.initTopHeader
import com.randomgametpnv.counters.R
import com.randomgametpnv.counters.entities.TypeOfEnergy
import kotlinx.android.synthetic.main.fragment_counters.*


class CountersFragment : BaseModuleFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_counters, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val topText = resources.getText(com.randomgametpnv.base.R.string.counters).toString()
        this.initTopHeader(topText = topText, arrowVisibility = true, view = view)


        coldWaterButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_countersFragment_to_graphFragment,
                bundleOf("typeOfEnergy" to TypeOfEnergy.COLD_WATER.name)
            )
        }

        gvsButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_countersFragment_to_graphFragment,
                bundleOf("typeOfEnergy" to TypeOfEnergy.HOT_WATER.name)
            )
        }

        el_power.setOnClickListener {
            findNavController().navigate(
                R.id.action_countersFragment_to_graphFragment,
                bundleOf("typeOfEnergy" to TypeOfEnergy.EL_POWER.name)
            )
        }

        heatButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_countersFragment_to_graphFragment,
                bundleOf("typeOfEnergy" to TypeOfEnergy.HEATING.name)
            )
        }
    }
}
