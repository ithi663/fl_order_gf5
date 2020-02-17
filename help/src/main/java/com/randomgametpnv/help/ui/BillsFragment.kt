package com.randomgametpnv.help.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.randomgametpnv.base.initTopHeader
import com.randomgametpnv.common_value_objects.ApiCall
import com.randomgametpnv.help.R
import kotlinx.android.synthetic.main.fragment_bills.*


class BillsFragment : BaseModuleFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bills, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val topText = resources.getText(com.randomgametpnv.base.R.string.bills).toString()
        this.initTopHeader(topText = topText, arrowVisibility = true, view = view)


        viewModel.billsResult.observe(this.viewLifecycleOwner, Observer {

            Log.d("QWERTYHH", "res -> $it")

            when(it) {
                is ApiCall.Success -> {
                    toPayTextVIew.text = it.data.toPay?.toString()?: ""
                    amountTextView.text = it.data.amount?.toString()?: ""
                    lastPayTextVIew.text = it.data.debt?.toString()?: ""
                }
            }
        })
        viewModel.getBillsFromApi()
    }
}
