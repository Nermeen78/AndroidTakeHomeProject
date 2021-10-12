package com.takehomeproject.utils

import android.util.Log
import kotlin.properties.Delegates

object Utilities {
    val networkNotifyListeners = ArrayList<InterfaceNetworkNotify>()
    var isNetworkConnected: Boolean by Delegates.observable(false) { property, oldValue, newValue ->
        Log.i("Network connectivity", "$newValue")
        networkNotifyListeners.forEach {
            it.networkChange(oldValue, newValue)
        }

    }
    interface InterfaceNetworkNotify {
        fun networkChange(old: Boolean, new: Boolean)
    }


}