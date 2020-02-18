package com.randomgametpnv.base

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.randomgametpnv.common_value_objects.ApiCall

fun View.setVisible() {
    this.visibility = View.VISIBLE
}


fun View.setInvisible() {
    this.visibility = View.INVISIBLE
}


fun Fragment.showErrorMessage(apiError: ApiCall<Nothing>) {


    when(apiError) {
        is ApiCall.ResponseError -> {
            val textMessage = resources.getText(R.string.responseError)
            Snackbar.make(view!!, textMessage, Snackbar.LENGTH_LONG).show()
        }
        is ApiCall.ConnectException -> {
            val textMessage = resources.getText(R.string.connectError)
            Snackbar.make(view!!, textMessage, Snackbar.LENGTH_LONG).show()
        }
    }
}
