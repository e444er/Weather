package com.moon.network.di

import com.moon.network.data.api.Api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuleNetwork {

    private const val BASE_URL = "https://api.weatherapi.com/v1/"

    @Provides
    fun baseUrl() = BASE_URL

    @Provides
    fun logging() = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun okHttpClient() = OkHttpClient.Builder()
        .addInterceptor(logging())
        .build()


    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient())
        .build()


    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)
}