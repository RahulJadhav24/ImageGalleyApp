package com.rahul.imgur.data.remote


import com.rahul.imgur.model.BaseData
import com.rahul.imgur.ui.utils.Constants.IMGUR_API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiList {

    @Headers("Authorization:CLIENT-ID $IMGUR_API_KEY")
    @GET("gallery/search/{page}")
    suspend fun getSearchList(@Path("page") page: String, @Query("q") q: String): Response<BaseData>

    companion object {
        const val BASE_PATH = "https://api.imgur.com/3/"
    }
}
