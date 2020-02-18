package com.randomgametpnv.help.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.randomgametpnv.base.initTopHeader
import com.randomgametpnv.base.setInvisible
import com.randomgametpnv.base.setVisible
import com.randomgametpnv.base.showErrorMessage
import com.randomgametpnv.common_value_objects.ApiCall
import com.randomgametpnv.help.R
import com.randomgametpnv.help.ui.base.BaseModuleFragment
import kotlinx.android.synthetic.main.fragment_bills.*


class BillsFragment : BaseModuleFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bills, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val topText = resources.getText(com.randomgametpnv.base.R.string.bills).toString()
        this.initTopHeader(topText = topText, arrowVisibility = true, view = view)


        viewModel.getBillsFromApi().observe(this.viewLifecycleOwner, Observer {

            when(it) {
                is ApiCall.Loading -> {
                    loading()
                }
                is ApiCall.ConnectException -> {
                    showErrorMessage(it)
                    withError()
                }
                is ApiCall.ResponseError -> {
                    showErrorMessage(it)
                    withError()
                }
                is ApiCall.Success -> {
                    loaded()
                    toPayTextVIew.text = it.data.toPay?.toString()?: ""
                    amountTextView.text = it.data.amount?.toString()?: ""
                    lastPayTextVIew.text = it.data.debt?.toString()?: ""
                }
            }
        })
    }


    private fun loading() {

        toPayTextVIew.setInvisible()
        amountTextView.setInvisible()
        lastPayTextVIew.setInvisible()

        progressBar.setVisible()
        progressBar2.setVisible()
        progressBar3.setVisible()
    }

    private fun loaded() {

        toPayTextVIew.setVisible()
        amountTextView.setVisible()
        lastPayTextVIew.setVisible()

        progressBar.setInvisible()
        progressBar2.setInvisible()
        progressBar3.setInvisible()
    }

    private fun withError() {

        toPayTextVIew.setInvisible()
        amountTextView.setInvisible()
        lastPayTextVIew.setInvisible()

        progressBar.setInvisible()
        progressBar2.setInvisible()
        progressBar3.setInvisible()
    }
}
