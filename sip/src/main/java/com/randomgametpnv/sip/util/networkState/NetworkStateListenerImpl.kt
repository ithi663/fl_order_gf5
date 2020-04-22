package com.randomgametpnv.sip.util.networkState

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.MutableLiveData
import com.randomgametpnv.sip.entities.NetState
import com.randomgametpnv.sip.util.RegistrationHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NetworkStateListenerImpl(context: Context, val scope: CoroutineScope, val registerHandler: RegistrationHandler): NetworkStateListener {


    private val connectivityManager: ConnectivityManager? = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    private val stateListener = MutableLiveData<NetState>()
    private var dnsAdress: String? = null

    init { registerListener() }

    override fun getStateListener() = stateListener

    @SuppressLint("MissingPermission")
    private fun registerListener() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager?.registerDefaultNetworkCallback(createCallBack())
        } else {
            connectivityManager?.registerNetworkCallback(createRequest(), createCallBack())
        }
    }


    override suspend fun waitActiveNetworkState(): Boolean {
        return false
    }

    override fun getInternetConnectionState(): Boolean {
        return connectivityManager?.isDefaultNetworkActive?:false
    }

    private fun createRequest() = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()


    private fun createCallBack(): NetworkCallback {
        return object : NetworkCallback() {
            override fun onAvailable(network: Network) {

                val netDns = connectivityManager?.getLinkProperties(network)?.dnsServers?.firstOrNull()?.toString()
                scope.launch(Dispatchers.Main) {
                    stateListener.value = NetState.Active("internet", System.currentTimeMillis())
                }
                registerHandler.handleRegistration()
/*                if (dnsAdress != netDns) {
                }
                dnsAdress  = netDns*/
            }

            override fun onLost(network: Network) {
                scope.launch(Dispatchers.Main) {
                    stateListener.value = NetState.Lose(System.currentTimeMillis())
                }
            }
        }
    }
}