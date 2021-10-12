package com.takehomeproject

import android.app.Activity
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import com.takehomeproject.di.component.ApplicationComponent
import com.takehomeproject.di.component.DaggerApplicationComponent
import com.takehomeproject.di.module.ContextModule
import com.takehomeproject.di.module.RepositoryModule
import com.takehomeproject.utils.Utilities


class MyApplication : Application() {
    lateinit var applicationComponent: ApplicationComponent
        internal set

    override fun onCreate() {
        super.onCreate()
        dependencyInjection()
        startNetworkCallback()
    }
    override fun onTerminate() {
        super.onTerminate()
        stopNetworkCallback()
    }
    private fun dependencyInjection() {
        applicationComponent = DaggerApplicationComponent.builder()
            .repositoryModule(RepositoryModule(this))
            .contextModule(ContextModule(this)).build()
        applicationComponent.injectApplication(this)
    }

    companion object {
        operator fun get(activity: Activity): MyApplication {
            return activity.application as MyApplication
        }
        operator fun get(ctx: Context): MyApplication {
            return ctx as MyApplication
        }
    }

    private fun startNetworkCallback() {
        val cm: ConnectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val builder: NetworkRequest.Builder = NetworkRequest.Builder()

        cm.registerNetworkCallback(
            builder.build(),
            object : ConnectivityManager.NetworkCallback() {

                override fun onAvailable(network: Network) {
                    Utilities.isNetworkConnected = true
                }

                override fun onLost(network: Network) {
                    Utilities.isNetworkConnected = false
                }
            })
    }

    private fun stopNetworkCallback() {
        val cm: ConnectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.unregisterNetworkCallback(ConnectivityManager.NetworkCallback())
    }
}