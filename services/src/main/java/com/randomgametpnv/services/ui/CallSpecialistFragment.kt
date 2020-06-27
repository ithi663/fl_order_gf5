package com.randomgametpnv.services.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.randomgametpnv.base.GMailSender
import com.randomgametpnv.base.initTopHeader
import com.randomgametpnv.services.R
import com.randomgametpnv.services.databinding.FragmentCallSpecialistBinding
import com.randomgametpnv.services.entities.SpecialistType
import kotlinx.android.synthetic.main.fragment_call_specialist.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CallSpecialistFragment : BaseModuleFragment() {

    val args: CallSpecialistFragmentArgs by navArgs()
    lateinit var binding: FragmentCallSpecialistBinding
    lateinit var topText: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCallSpecialistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.discButton.setOnClickListener {
           sendEmail()
        }

        topText = when(args.specialistType) {
            SpecialistType.CLEANING.name -> {resources.getText(com.randomgametpnv.base.R.string.cleaning).toString()}
            SpecialistType.PLUMBER.name -> {resources.getText(com.randomgametpnv.base.R.string.plumber).toString()}
            else -> {resources.getText(com.randomgametpnv.base.R.string.electrician).toString()}
        }

        this.initTopHeader(topText = topText, arrowVisibility = true, view = view)
    }



    private fun sendEmail() {
        if (binding.discText.text.toString() == "") return
        viewModel.getUseLogin().observe(this.viewLifecycleOwner, Observer {
            lifecycleScope.launch(Dispatchers.IO) {
                GMailSender("test123test123qwert1@gmail.com", "test123test123")
                    .sendMail("$it: ${topText}", binding.discText.text.toString()+".", "2660103@bk.ru")
                delay(1000)
                withContext(Dispatchers.Main) {
                    discText.setText("")
                }
            }
        })
    }
}
