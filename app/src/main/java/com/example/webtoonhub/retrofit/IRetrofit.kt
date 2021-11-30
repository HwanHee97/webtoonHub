package com.example.webtoonhub.retrofit

import com.example.webtoonhub.utils.API
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IRetrofit {

    @GET(API.GET_ALL)//모든데이터 가져오기
    fun getAll(): Call<JsonElement>

    @GET(API.SEARCH)//제목,저자등을 검색
    fun search(@Query("search") searchTerm: String): Call<JsonElement>

    @GET(API.SEARCH_WEEK)//요일별 데이터 검색
    fun searchWeek(@Query("day") searchTerm: String): Call<JsonElement>

}