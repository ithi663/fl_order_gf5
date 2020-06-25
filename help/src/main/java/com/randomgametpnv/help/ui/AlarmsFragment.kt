package com.randomgametpnv.help.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.randomgametpnv.base.GMailSender
import com.randomgametpnv.base.initTopHeader

import com.randomgametpnv.help.R
import com.randomgametpnv.help.ui.adapter.AlarmsRvAdapter
import com.randomgametpnv.help.ui.base.BaseModuleFragment
import kotlinx.android.synthetic.main.fragment_message.*

class AlarmsFragment : BaseModuleFragment() {

    lateinit var topText: String
    val nagArgs: AlarmsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_message, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        topText = when(nagArgs.messageType) {
            TypeMessage.ALARM -> {"Авария"}
            TypeMessage.MESSAGE_TO_YK -> {"Сообщение в УК"}
            TypeMessage.REQUEST -> {"Заказ пропуска"}
        }

        val topText = resources.getText(R.string.crash).toString()
        this.initTopHeader(topText = topText, arrowVisibility = true, view = view)

        discButton.setOnClickListener {
            sendEmail()
        }
    }


    private fun sendEmail() {
        viewModel.getUseLogin().observe(this.viewLifecycleOwner, Observer {
            GMailSender("test123test123qwert1@gmail.com", "test123test123")
                .sendMail("${topText}", discText.text.toString(), "ithi663@gmail.com")
        })
    }
}

enum class TypeMessage {
    ALARM, MESSAGE_TO_YK, REQUEST
}
