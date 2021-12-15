package com.example.webtoonhub.retrofit

import android.util.Log
import android.widget.Toast
import com.example.webtoonhub.App
import com.example.webtoonhub.model.WebToonData
import com.example.webtoonhub.utils.API
import com.example.webtoonhub.utils.Constants
import com.example.webtoonhub.utils.PLATFORM
import com.example.webtoonhub.utils.RESPONSE_STATUS
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import org.w3c.dom.Element
import retrofit2.Call
import retrofit2.Response

class RetrofitManager {
    companion object {
        //싱글턴처럼 적용되도록 companion을 사용하여 선언
        val instance = RetrofitManager()
    }
    //레트로핏 인터페이스 만들기(가져오기): BASE_URL을 매매변수로 getClient 함수 호출한 결과
    private val iRetrofit: IRetrofit? = RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)
    lateinit var call:Call<JsonElement>
    var week: Int=0
    fun getWeekWebtoonData(type:String,searchPlatform: String?,searchTerm: String?, completion: (RESPONSE_STATUS, ArrayList<WebToonData>?) -> Unit) {
        Log.d(Constants.TAG, "RetorfitManager - getWebtoonData()")
        if (type=="search") {
            val week = searchTerm.let { it } ?: ""//searchTerm이 비어있으면""을 반환 아니면 그대로(it) //enum쿨래스의 mon~sun 값이다.
            val platform = searchPlatform.let { it } ?: ""
             call = iRetrofit?.search(platform = platform, searchTerm = week).let { it } ?: return //값이 없으면 return한다.있으면 it return
        }else if(type=="searchCustomize"){
            val searchQuery = searchTerm.let { it } ?: ""
            call=iRetrofit?.searchCustomize(searchTerm = searchQuery).let { it } ?: return //값이 없으면 return한다.있으면 it return
        }

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
                            try {
                                val body=it.asJsonArray
                                body.forEach{ resultItem->//오류 처리
                                    try {
                                        val resultItemObject = resultItem.asJsonObject//하나의 웹툰 정보모음
                                        val title: String = resultItemObject.get("title").asString
                                        val author: String = resultItemObject.get("author").asString
                                        val url: String = resultItemObject.get("url").asString
                                        val thumbnail: String = resultItemObject.get("img").asString
                                        val platform: String = resultItemObject.get("service").asString
                                        if (type=="search") {//커스텀 검색시 week값이 json에 없기때운에 조건문 처리
                                            week = resultItemObject.get("week").asInt
                                        }
                                        val additionalObject = resultItemObject.getAsJsonObject("additional").asJsonObject
                                        val new:Boolean=additionalObject.get("new").asBoolean
                                        val up:Boolean=additionalObject.get("up").asBoolean

                                        val webtoonItem = WebToonData(
                                            title = title,
                                            author = author,
                                            url = url,
                                            thumbnail = thumbnail,
                                            platform = platform,
                                            week = week,
                                            new = new,
                                            up =up
                                        )
                                        parsedWeebtoonDataArrayList.add(webtoonItem)
                                    }catch (e:NullPointerException){
                                        return@forEach
                                    }
                                }
                                completion(RESPONSE_STATUS.OKAY,parsedWeebtoonDataArrayList)
                            }catch (e:IllegalStateException){
                                Toast.makeText(App.instance,"검색 결과가 없습니다.",Toast.LENGTH_SHORT).show()
                            }
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