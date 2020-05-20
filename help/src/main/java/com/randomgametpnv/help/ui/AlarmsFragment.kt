package com.randomgametpnv.help.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.randomgametpnv.base.initTopHeader
import com.randomgametpnv.base.setInvisible
import com.randomgametpnv.base.setVisible
import com.randomgametpnv.base.showErrorMessage
import com.randomgametpnv.common_value_objects.ApiCall

import com.randomgametpnv.help.R
import com.randomgametpnv.help.ui.adapter.AlarmsRvAdapter
import com.randomgametpnv.help.ui.base.BaseModuleFragment
import kotlinx.android.synthetic.main.fragment_alarms.*

class AlarmsFragment : BaseModuleFragment() {


    var adapter: AlarmsRvAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_alarms, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val topText = resources.getText(com.randomgametpnv.base.R.string.services).toString()
        this.initTopHeader(topText = topText, arrowVisibility = true, view = view)

        createAdapter()
        viewModel.getAlarms().observe(this.viewLifecycleOwner, Observer {
            when (it) {
                is ApiCall.Success -> {
                    alarmProgress.setInvisible()
                    adapter?.submitList(it.data)
                }
                is ApiCall.ResponseError -> {
                    alarmProgress.setInvisible()
                    showErrorMessage(it)
                }
                is ApiCall.Loading -> {
                    alarmProgress.setVisible()
                }
                is ApiCall.ConnectException -> {
                    alarmProgress.setInvisible()
                    showErrorMessage(it)
                }
            }
        })
    }

    private fun createAdapter() {

        alarmRv.adapter = adapter?: AlarmsRvAdapter().also { adapter = it }
        alarmRv.layoutManager = LinearLayoutManager(this.requireContext())
    }
}
