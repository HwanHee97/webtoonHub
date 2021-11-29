package com.example.webtoonhub.retrofit

import com.example.webtoonhub.utils.API
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IRetrofit {
@GET(API.GET_ALL)
fun getAll():Call<JsonElement>
@GET(API.SEARCH)
fun search(@Query("search")searchTerm:String):Call<JsonElement>
@GET(API.SEARCH_WEEK)
fun searchWeek(@Query("day")searchTerm:String):Call<JsonElement>

}