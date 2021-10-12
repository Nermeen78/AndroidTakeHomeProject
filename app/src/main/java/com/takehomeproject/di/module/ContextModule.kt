package com.takehomeproject.di.module

import android.content.Context
import com.takehomeproject.di.ApplicationContext
import com.takehomeproject.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides



@Module
public class ContextModule(private val context: Context) {

    @Provides
    @ApplicationScope
    @ApplicationContext
    fun providesContext(): Context {
        return context
    }
}