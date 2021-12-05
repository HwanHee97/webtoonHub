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
                //Log.d(Constants.TAG, "RetorfitManager-onResponse() called / response: ${response.body()}")
                when(response.code()){//응답 코드기 200(정상)일떄만 completion을(메인 뷰모델로) 보낸다.
                    200->{
                        response.body()?.let {
                        //response.body()에 데이터가 있다면
                            var parsedWeebtoonDataArrayList=ArrayList<WebToonData>()
                            //데이터 파싱
                            val body=it.asJsonArray
                            body.forEach{ resultItem->
                                val resultItemObject = resultItem.asJsonObject//하나의 웹툰 정보모음

                                val title: String = resultItemObject.get("title").asString
                                val author: String = resultItemObject.get("author").asString
                                val url: String = resultItemObject.get("url").asString
                                val thumbnail: String = resultItemObject.get("img").asString
                                val platform: String = resultItemObject.get("service").asString
                                val week:Int = resultItemObject.get("week").asInt

                                val webtoonItem = WebToonData(
                                    title = title,
                                    author = author,
                                    url = url,
                                    thumbnail = thumbnail,
                                    platform = platform,
                                    week = week
                                )
                                parsedWeebtoonDataArrayList.add(webtoonItem)

                            }
                            completion(RESPONSE_STATUS.OKAY,parsedWeebtoonDataArrayList)
                        }
                    }
                }
            }
            //응답 실패시
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(Constants.TAG, "RetorfitManager-onFailure() called / t:$t")
            }

        })
    }


}