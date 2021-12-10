package com.example.webtoonhub

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.webkit.WebViewClient
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.webtoonhub.databinding.ActivityWebViewBinding
import com.example.webtoonhub.utils.Constants

class WebViewActivity: AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding
    private lateinit var url:String
    private lateinit var actionBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(Constants.TAG,"DownloadWedViewActivity - onCreate() called ")
        setBinding()
        setContentView(binding.root)
        setActionBar()
        getIntents()
        setWebView()

    }//onCreate()

    fun setBinding(){
        Log.d(Constants.TAG,"DownloadWedViewActivity - setBinding() called ")
        binding = ActivityWebViewBinding.inflate(layoutInflater)
    }
    //액션바 설정
    fun setActionBar(){
        setSupportActionBar(binding.webViewActivityToolbar)
        actionBar=supportActionBar!!
        actionBar.setDisplayHomeAsUpEnabled(true)
    }
    //url 받기
    fun getIntents(){
        url=intent.getStringExtra("view_url") as String
        Log.d(Constants.TAG,"WebViewActivity - getIntents() called / url: $url ")
    }
    //웹뷰에 url연결해서 띄우기
    fun setWebView(){
        binding.webview.apply {
            webViewClient = WebViewClient()//하이퍼링크 클릭시 새창 띄우기 방지
            settings.javaScriptEnabled = true//자바스크립트 허용
            settings.loadsImagesAutomatically = true
        }
        binding.webview.loadUrl(url)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}