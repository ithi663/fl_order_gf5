package com.randomgametpnv.counters.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.randomgametpnv.base.initTopHeader
import com.randomgametpnv.counters.R
import com.randomgametpnv.counters.entities.TypeOfEnergy
import com.randomgametpnv.counters.ui.base.BaseModuleFragment
import kotlinx.android.synthetic.main.fragment_counters.*


class CountersFragment : BaseModuleFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_counters, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val topText = resources.getText(com.randomgametpnv.base.R.string.counters).toString()
        this.initTopHeader(topText = topText, arrowVisibility = true, view = view)



        coldWaterButton.setOnClickListener { navigate(TypeOfEnergy.COLD_WATER) }
        gvsButton.setOnClickListener {navigate(TypeOfEnergy.HOT_WATER)}
        el_power.setOnClickListener {navigate(TypeOfEnergy.EL_POWER)}
        heatButton.setOnClickListener {navigate(TypeOfEnergy.HEATING)}
    }

    private fun navigate(typeOfEnergy: TypeOfEnergy) {
        val action =
            CountersFragmentDirections
                .actionCountersFragmentToGraphFragment(typeOfEnergy)
        findNavController().navigate(action)
    }
}
