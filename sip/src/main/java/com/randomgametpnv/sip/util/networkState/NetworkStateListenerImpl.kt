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
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NetworkStateListenerImpl(context: Context, val scope: CoroutineScope): NetworkStateListener {


    private val connectivityManager: ConnectivityManager? = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    private val stateListener = MutableLiveData<NetState>()
    private var netOk: CompletableDeferred<Boolean> = CompletableDeferred()

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
        if (connectivityManager?.isDefaultNetworkActive == true) return true
        netOk = CompletableDeferred()
        return netOk.await()
    }


    private fun createRequest() = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()


    private fun createCallBack(): NetworkCallback? {
        return object : NetworkCallback() {
            override fun onAvailable(network: Network) {
                netOk.complete(true)
                scope.launch(Dispatchers.Main) {
                    stateListener.value = NetState.Active("internet", System.currentTimeMillis())
                }
            }

            override fun onLost(network: Network) {
                scope.launch(Dispatchers.Main) {
                    stateListener.value = NetState.Lose(System.currentTimeMillis())
                }
            }
        }
    }
}

