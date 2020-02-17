package com.randomgametpnv.base

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment


fun Fragment.initTopHeader(topText: String, arrowVisibility: Boolean, view: View) {

    val arrow: ImageView = view.findViewById(R.id.backArrow)
    val headText: TextView = view.findViewById(R.id.headText)

    if (arrowVisibility) arrow.setVisible()
    else arrow.setInvisible()
    arrow.setOnClickListener {
        this.activity?.onBackPressed()
    }

    headText.text = topText
}