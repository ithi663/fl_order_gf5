package com.randomgametpnv.services.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.randomgametpnv.base.initTopHeader
import com.randomgametpnv.services.R
import com.randomgametpnv.services.entities.SpecialistType


class CallSpecialistFragment : BaseModuleFragment() {

    val args: CallSpecialistFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_call_specialist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        val topText = when(args.specialistType) {
            SpecialistType.CLEANING.name -> {resources.getText(com.randomgametpnv.base.R.string.cleaning).toString()}
            SpecialistType.PLUMBER.name -> {resources.getText(com.randomgametpnv.base.R.string.plumber).toString()}
            else -> {resources.getText(com.randomgametpnv.base.R.string.electrician).toString()}
        }

        this.initTopHeader(topText = topText, arrowVisibility = true, view = view)

    }

}
