package com.takehomeproject.di.component

import android.content.Context
import com.takehomeproject.MyApplication

import com.takehomeproject.di.scope.ApplicationScope
import com.takehomeproject.di.ApplicationContext
import com.takehomeproject.di.module.ContextModule
import com.takehomeproject.di.module.RepositoryModule
import com.takehomeproject.repository.ITunesContentRepository
import com.takehomeproject.repository.api.ApiService
import com.takehomeproject.ui.MainActivity
import com.takehomeproject.ui.viewmodel.ITunesContentViewModel
import dagger.Component


@ApplicationScope
@Component(modules = [RepositoryModule::class,ContextModule::class])
interface ApplicationComponent {

    val apiService: ApiService

    val iTunesContentRepository:ITunesContentRepository

    @get:ApplicationContext
    val context: Context

    fun injectApplication(application: MyApplication)

}