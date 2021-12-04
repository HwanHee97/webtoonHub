package com.example.webtoonhub.retrofit

import android.util.Log
import com.example.webtoonhub.model.WebToonData
import com.example.webtoonhub.utils.API
import com.example.webtoonhub.utils.Constants
import com.example.webtoonhub.utils.PLATFORM
import com.example.webtoonhub.utils.RESPONSE_STATUS
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response

class RetrofitManager {
    companion object {
        //싱글턴처럼 적용되도록 companion을 사용하여 선언
        val instance = RetrofitManager()
    }
    //레트로핏 인터페이스 만들기(가져오기): BASE_URL을 매매변수로 getClient 함수 호출한 결과
    private val iRetrofit: IRetrofit? = RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    fun getWeekWebtoonData(searchPlatform: String?,searchTerm: String?, completion: (RESPONSE_STATUS, ArrayList<WebToonData>?) -> Unit) {

        Log.d(Constants.TAG, "RetorfitManager - getWebtoonData()")
        val week = searchTerm.let { it } ?: ""//searchTerm이 비어있으면""을 반환 아니면 그대로(it) //enum쿨래스의 mon~sun 값이다.
        val platform=searchPlatform.let{it}?:""
        val call = iRetrofit?.search(platform=platform,searchTerm = week).let { it } ?: return //값이 없으면 return한다.있으면 it return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            //응답성공시
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(Constants.TAG, "RetorfitManager-onResponse() called / response: ${response.body()}")
            }
            //응답 실패시
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(Constants.TAG, "RetorfitManager-onFailure() called / t:$t")
            }

        })
    }


}