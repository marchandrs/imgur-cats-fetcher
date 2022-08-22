package com.stefanini.imgurcatfetcher.api

import com.stefanini.imgurcatfetcher.model.ImgurImage
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiInterface {

    // move key to .env file would be nice
    @Headers("Authorization: Client-ID 1ceddedc03a5d71")
    @GET("gallery/search/{page}/?q=cats")
    fun getData(@Path("page") page: String): Call<ImgurImage>

}