package com.example.webtoonhub.retrofit

import android.util.Log
import com.example.webtoonhub.utils.Constants
import com.example.webtoonhub.utils.isJsonArray
import com.example.webtoonhub.utils.isJsonObject
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.concurrent.TimeUnit

object RetrofitClient {
    //레트로핏 클라이언트 선언
    private var retrofitClient: Retrofit?=null

    fun getClient(baseUrl: String):Retrofit?{
        //okhttp 인스턴스 생성
        val client= OkHttpClient.Builder()
        //로그를 찍기 위해 로깅인터셉터 설정
        val loggingInterceptor= HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger{
            override fun log(message: String) {
                //Log.d(Constants.TAG,"RetrofitClient - log() called / message : $message")
                when{
                    message.isJsonObject()-> {
                        //Log.d(Constants.TAG, "RetrofitClient -message.isJsonObject!!")
                        //Log.d(Constants.TAG, JSONObject(message).toString(4))//indentSpace=4는 들여쓰기하기
                    }
                    message.isJsonArray()-> {
                        //Log.d(Constants.TAG, "RetrofitClient -message.isJsonArray!!")
                        //Log.d(Constants.TAG, JSONArray(message).toString(4))//indentSpace=4는 들여쓰기하기
                    }
                    else->{
                        try {
                            //Log.d(Constants.TAG, JSONObject(message).toString(4))//indentSpace=4는 들여쓰기하기
                        }catch (e: Exception){
                            //Log.d(Constants.TAG,message)//indentSpace=4는 들여쓰기하기
                        }
                    }
                }
            }
        })
        //로깅 레벨 설정
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        //위에서 생성한 로깅 인터셉터를 okhttp 클라이언트에 추가한다.
        client.addInterceptor(loggingInterceptor)
        //커넥션 타임아웃
        client.connectTimeout(20, TimeUnit.SECONDS)
        client.readTimeout(10, TimeUnit.SECONDS)
        client.writeTimeout(10, TimeUnit.SECONDS)
        //실패시 다시시도 여부
        client.retryOnConnectionFailure(true)
        if (retrofitClient == null){
            //레트로핏 빌더를 통해 인스턴스 생성
            retrofitClient=Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                //위에서 설정한 클라이언트로 레트로핏 클라이언트를 설정한다.
                .client(client.build())
                .build()//레트로핏 빌드할때 .옵션 들을 추가하고 가장 마지막에 .build()로 마무리
        }
        return retrofitClient
    }

}