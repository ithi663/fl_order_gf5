package com.randomgametpnv.services.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.randomgametpnv.base.initTopHeader
import com.randomgametpnv.services.R
import com.randomgametpnv.services.entities.SpecialistType
import kotlinx.android.synthetic.main.fragment_services.*


class ServicesFragment : BaseModuleFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_services, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val topText = resources.getText(com.randomgametpnv.base.R.string.services).toString()
        this.initTopHeader(topText = topText, arrowVisibility = true, view = view)


        electricianButton.setOnClickListener {navigateToSpecialist(SpecialistType.ELECTRICIAN.name)}
        plumberButton.setOnClickListener {navigateToSpecialist(SpecialistType.PLUMBER.name)}
        cleaningButton.setOnClickListener {showToast("Сервис временно недоступен")}
        deliveryButton.setOnClickListener { showToast("Сервис временно недоступен") }
    }


    fun showToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
    }

    private fun navigateToSpecialist(data: String) {
        val action
                =
            ServicesFragmentDirections.actionServicesFragmentToCallSpecialistFragment(
                data
            )
        findNavController().navigate(action)
    }
}
