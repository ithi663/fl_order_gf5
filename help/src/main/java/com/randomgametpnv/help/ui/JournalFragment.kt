package com.randomgametpnv.help.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.randomgametpnv.base.initTopHeader
import com.randomgametpnv.base.setInvisible
import com.randomgametpnv.base.setVisible
import com.randomgametpnv.base.showErrorMessage
import com.randomgametpnv.common_value_objects.ApiCall
import com.randomgametpnv.help.R
import com.randomgametpnv.help.ui.adapter.JournalRvAdapter
import com.randomgametpnv.help.ui.base.BaseModuleFragment
import kotlinx.android.synthetic.main.fragment_jornal.*


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


        viewModel.getJournalFromApi().observe(this.viewLifecycleOwner, Observer {

            when(it) {
                is ApiCall.Success -> {
                    progressBar4.setInvisible()
                    journalRvAdapter?.submitList(it.data)
                }
                is ApiCall.ConnectException -> {
                    progressBar4.setInvisible()
                    showErrorMessage(it)
                }
                is ApiCall.ResponseError -> {
                    progressBar4.setInvisible()
                    showErrorMessage(it)
                }
                is ApiCall.Loading -> {
                    progressBar4.setVisible()
                }
            }
        })
    }


    private fun initRV() {

        jornalRv.adapter = journalRvAdapter?: JournalRvAdapter(null).also { journalRvAdapter = it }
        jornalRv.layoutManager = LinearLayoutManager(this.context)
        jornalRv.addItemDecoration(DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL))
    }
}
