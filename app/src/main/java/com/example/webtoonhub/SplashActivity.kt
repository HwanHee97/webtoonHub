package com.example.webtoonhub

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.webtoonhub.databinding.ActivitySplashBinding
import com.example.webtoonhub.utils.API_DAY_WEEK
import com.example.webtoonhub.utils.Constants
import java.util.*
import kotlin.collections.ArrayList

class SplashActivity:AppCompatActivity() {
    private lateinit var binding:ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(Constants.TAG,"SplashActivity - onCreate() called")
        super.onCreate(savedInstanceState)
        setBinding()
        setContentView(binding.root)
        setLodingImageListener()
        startLoadingImage()

    }//onCreate()
    fun setBinding(){
        binding= ActivitySplashBinding.inflate(layoutInflater)
    }
    fun setLodingImageListener(){
        binding.loadingImage.addAnimatorListener(object :Animator.AnimatorListener{
            override fun onAnimationStart(animation: Animator?) {
                //TODO("Not yet implemented")
                Log.d(Constants.TAG,"애니매이션 시작")
            }

            override fun onAnimationEnd(animation: Animator?) {
                //TODO("Not yet implemented")
                Log.d(Constants.TAG,"애니매이션 끝")
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)//메인 액티비티 시작
                finish()//스플래시 액티비티 종효
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

    fun startLoadingImage(){
        binding.loadingImage.apply {
            playAnimation()
            repeatCount=3//반복횟수 이것을 지정 안하면 무한반복이다. 나중에 애니매이션 무한반복 시키고 통신이 성공했을때 애니매이션 끝내고 액티비티 변환할것
        }
    }

}