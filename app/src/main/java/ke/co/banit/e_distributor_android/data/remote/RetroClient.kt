package com.example.bigheartfoundation.data.remote

import ke.co.banit.e_distributor_android.data.PrefManager
import ke.co.banit.e_distributor_android.data.remote.APIService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetroClient {
    companion object {
        private const val BASEURL = "https://www.bigheart.banit.co.ke/api/"
        private var mHttpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        private var mOkHttpClient = OkHttpClient
            .Builder()
            .addNetworkInterceptor(Interceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                val sessionToken = PrefManager().getSessionToken()
                requestBuilder.addHeader("Authorization", sessionToken)
                chain.proceed(requestBuilder.build())
            })
            .addInterceptor(mHttpLoggingInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
        private var instance: APIService? = null

        fun getApi(): APIService {
            if (instance == null) {
                instance = Retrofit.Builder()
                    .baseUrl(BASEURL)
                    .client(mOkHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(APIService::class.java)
            }
            return instance!!
        }

    }
}