package com.randomgametpnv.base

import android.os.Build
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.TextView
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
            val snack = Snackbar.make(view!!, textMessage, Snackbar.LENGTH_LONG)
            customSnackView(snack)
            snack.show()
        }
        is ApiCall.ConnectException -> {
            val textMessage = resources.getText(R.string.connectError)
            val snack = Snackbar.make(view!!, textMessage, Snackbar.LENGTH_LONG)
            customSnackView(snack)
            snack.show()
        }
    }
}

fun customSnackView(snackbar: Snackbar) {

    val snackTextView: TextView = snackbar.view.findViewById(R.id.snackbar_text)
    snackTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        snackTextView.textAlignment = View.TEXT_ALIGNMENT_CENTER
    else
        snackTextView.gravity = Gravity.CENTER_HORIZONTAL
}
