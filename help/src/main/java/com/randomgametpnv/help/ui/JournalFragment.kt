package com.randomgametpnv.help.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.randomgametpnv.base.initTopHeader
import com.randomgametpnv.common_value_objects.ApiCall
import com.randomgametpnv.help.R
import com.randomgametpnv.help.ui.adapter.JournalRvAdapter
import com.randomgametpnv.help.entities.JournalUiData
import kotlinx.android.synthetic.main.fragment_jornal.*
import org.koin.android.ext.android.inject


class JournalFragment : BaseModuleFragment() {


    private var journalRvAdapter: JournalRvAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_jornal, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val topText = resources.getText(com.randomgametpnv.base.R.string.journal).toString()
        this.initTopHeader(topText = topText, arrowVisibility = true, view = view)

        initRV()

        viewModel.journalResult.observe(this.viewLifecycleOwner, Observer {

            when(it) {
                is ApiCall.Success -> {
                    journalRvAdapter?.submitList(it.data)
                }
            }
        })
        viewModel.getJournalFromApi()
    }


    private fun initRV() {

        jornalRv.adapter = journalRvAdapter?: JournalRvAdapter(null).also { journalRvAdapter = it }
        jornalRv.layoutManager = LinearLayoutManager(this.context)
        jornalRv.addItemDecoration(DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL))
    }
}
