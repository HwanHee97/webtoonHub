package com.example.webtoonhub

import android.app.Application

class App :Application(){//  manifests에 앱이름을 이 클래스로 선언 해줘야함
    companion object{
        lateinit var instance:App
        private set
    }

    override fun onCreate() {
        super.onCreate()
        instance=this
    }
}