package com.takehomeproject.di.module

import android.content.Context
import com.takehomeproject.di.scope.ApplicationScope
import com.takehomeproject.repository.ITunesContentRepository
import com.takehomeproject.repository.api.ApiService
import com.takehomeproject.repository.db.ItunesContentRoomDatabase

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

import okhttp3.logging.HttpLoggingInterceptor

import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@Module
public class RepositoryModule(private val context: Context)
{

    internal val httpLoggingInterceptor: HttpLoggingInterceptor
        @Provides
        @ApplicationScope
        get() {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return httpLoggingInterceptor
        }

    @Provides
    @ApplicationScope
    internal fun getRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(ApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient(httpLoggingInterceptor))
            .build()


    }


    @Provides
    @ApplicationScope
    fun provideITunesContentRepository(): ITunesContentRepository {
        return ITunesContentRepository(ItunesContentRoomDatabase.getDatabase(context)?.itunesContentDAO(),provideApiService(getRetrofit()))
    }

    @Provides
    @ApplicationScope
    internal fun getOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {

        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .readTimeout(150, TimeUnit.SECONDS)
            .connectTimeout(150, TimeUnit.SECONDS)
            .build()
    }


    @Provides
    @ApplicationScope
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create<ApiService>(ApiService::class.java!!)
    }

}