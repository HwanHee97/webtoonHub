package com.example.webtoonhub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.webtoonhub.retrofit.RetrofitManager
import com.example.webtoonhub.utils.Constants
import com.example.webtoonhub.utils.RESPONSE_STATUS

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(Constants.TAG, "MainActivity - onCreate() called")




    }

    //splashActivity로부터 인텐트 받기
    fun getIntents() {

    }

}