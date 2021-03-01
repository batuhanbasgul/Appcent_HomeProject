package com.example.appcent_homeproject.data.service

import com.example.appcent_homeproject.utils.Constants.API_KEY
import com.example.appcent_homeproject.utils.Constants.BASE_URL
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object PhotoListClient {
    fun getClient():PhotoListAPI{
        val requestInterceptor = Interceptor{chain ->
            var url :HttpUrl = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("api_key",API_KEY)
                .build()

            var request :Request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            return@Interceptor chain.proceed(request)
        }
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .connectTimeout(60,TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PhotoListAPI::class.java)
    }
}