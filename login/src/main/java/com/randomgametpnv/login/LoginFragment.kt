package com.randomgametpnv.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.randomgametpnv.base.*
import com.randomgametpnv.common_value_objects.ApiCall
import com.randomgametpnv.login.entities.LoginEntities
import com.randomgametpnv.navigation.actionLoginFragmentToHomeFragmentId
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by sharedViewModel()
    private var userLogin: String? = null
    private var userPass: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.result.observe(this.viewLifecycleOwner, Observer {

            when (it) {
                is ApiCall.ResponseError -> {
                    val message = resources.getString(R.string.invalidLogin)
                    errorResponse(message)
                }

                is ApiCall.ConnectException -> {
                    val message = resources.getString(R.string.connectError)
                    errorResponse(message)
                }
                is ApiCall.Loading -> {
                    loading()
                }
                is ApiCall.Success -> {
                    successLogin(it.data)
                }
            }
        })

        loginButton.setOnClickListener {
            userLogin = editTextTextPersonName.text.toString()
            userPass = editTextTextPassword.text.toString()
            viewModel.login(userLogin!!, userPass!!)
        }


        val topText = resources.getText(com.randomgametpnv.base.R.string.auth).toString()
        this.initTopHeader(topText = topText, arrowVisibility = false, view = view)
    }


    private fun errorResponse(message: String) {
        loginButton.isEnabled = true
        editTextTextPersonName.isEnabled = true
        editTextTextPassword.isEnabled = true
        progressBar.setInvisible()
        showMessage(message)
    }

    private fun loading() {
        loginButton.isEnabled = false
        editTextTextPersonName.isEnabled = false
        editTextTextPassword.isEnabled = false
        progressBar.setVisible()
    }

    private fun successLogin(data: LoginEntities) {

        val navId = activity?.actionLoginFragmentToHomeFragmentId() ?: return
        findNavController().navigate(navId)
    }

    private fun showMessage(text: String) {
        val snack = Snackbar.make(view!!, text, Snackbar.LENGTH_LONG)
        customSnackView(snack)
        snack.show()
    }
}
