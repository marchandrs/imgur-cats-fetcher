package com.stefanini.imgurcatfetcher.api

import com.stefanini.imgurcatfetcher.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {

    private var api: ApiInterface? = null

    fun getApi(): ApiInterface? {
        if (api == null) {
            api = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(ApiInterface::class.java)
        }
        return api
    }

}