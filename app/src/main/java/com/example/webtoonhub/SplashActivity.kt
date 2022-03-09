package com.example.webtoonhub

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.webtoonhub.databinding.ActivitySplashBinding
import com.example.webtoonhub.utils.API_DAY_WEEK
import com.example.webtoonhub.utils.Constants
import com.example.webtoonhub.utils.PLATFORM
import java.util.*
import kotlin.collections.ArrayList

class SplashActivity:AppCompatActivity() {
    private lateinit var binding:ActivitySplashBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(Constants.TAG,"SplashActivity - onCreate() called")
        super.onCreate(savedInstanceState)
        setBinding()
        setContentView(binding.root)
        setObserve()
    }//onCreate()

    private fun setBinding(){
        Log.d(Constants.TAG,"SplashActivity - setBinding() called")
        binding= ActivitySplashBinding.inflate(layoutInflater)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private fun setLodingImageListener(){
        binding.loadingImage.addAnimatorListener(object :Animator.AnimatorListener{
            override fun onAnimationStart(animation: Animator?) {
                //TODO("Not yet implemented")
                Log.d(Constants.TAG,"애니매이션 시작")
            }

            override fun onAnimationEnd(animation: Animator?) {
                //TODO("Not yet implemented")
                Log.d(Constants.TAG,"애니매이션 끝")
            }

            override fun onAnimationCancel(animation: Animator?) {
                // TODO("Not yet implemented")
                Log.d(Constants.TAG,"애니매이션 취소")
            }

            override fun onAnimationRepeat(animation: Animator?) {
                //TODO("Not yet implemented")
                Log.d(Constants.TAG,"애니매이션 반복")
            }
        })
    }

    private fun startLoadingImage(){
        binding.loadingImage.apply {
            playAnimation()
            //repeatCount=3//반복횟수 이것을 지정 안하면 무한반복이다. 나중에 애니매이션 무한반복 시키고 통신이 성공했을때 애니매이션 끝내고 액티비티 변환할것
        }
    }
    private fun setObserve() {//api통신가능 일때 메인 액티비티 실행한다.
        //StateFlow는 LiveData와 달리 생명주기를 자동으로 핸들링하지 못하기에 뷰에서
        // 1. lifecycleScope를 선언해 처리해 주거나
        //2. lifecycleScope의 사용이 번거롭다면 asLiveData()를 사용할 수 있다.
        mainViewModel.uiState.asLiveData().observe(this, androidx.lifecycle.Observer
        {
            when (it) {
                is UiState.Loading -> {
                    Log.d(Constants.TAG,"UiState.Loading")
                    mainViewModel.getResponseState()
                    setLodingImageListener()
                    startLoadingImage()
                }
                is UiState.Empty -> {
                    Log.d(Constants.TAG,"UiState.Empty")
                    mainViewModel.getResponseState()
                }
                is UiState.Success -> {
                    Log.d(Constants.TAG,"UiState.Success")
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)//메인 액티비티 시작
                    finish()
                }
                is UiState.Error -> {
                    Log.d(Constants.TAG,"UiState.Error")
                    mainViewModel.getResponseState()
                }
            }
        })
    }
}