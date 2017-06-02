package com.example.mhjg43.kotlinendlessrecycler.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by mhjg43 on 6/2/2017.
 */
object ServiceGenerator {

    fun <S> createService(serviceClass: Class<S>):S{
        val httpClient = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl("http://www.sab99r.com/demos/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient).build()

        return retrofit.create(serviceClass)

    }
}