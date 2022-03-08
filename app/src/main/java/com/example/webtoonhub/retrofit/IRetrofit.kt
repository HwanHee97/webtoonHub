package com.example.webtoonhub.retrofit

import com.example.webtoonhub.utils.API
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IRetrofit {

    @GET(API.BASE_URL)//api연결 테스트 url
    fun getState(): Call<JsonElement>

    @GET(API.SEARCH)//플랫폼, 요일을 매개변수로 받아 검색
    fun search(@Path("platform") platform:String, @Query("day") searchTerm: Int): Call<JsonElement>

    @GET(API.SEARCH_CUSTOMIZE)// 데이터 검색
    fun searchCustomize(@Query("keyword") searchTerm: String): Call<JsonElement>

}