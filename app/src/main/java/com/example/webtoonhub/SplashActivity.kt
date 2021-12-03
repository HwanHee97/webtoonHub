package com.example.webtoonhub

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.example.webtoonhub.databinding.ActivitySplashBinding
import com.example.webtoonhub.model.WebToonData
import com.example.webtoonhub.utils.API_DAY_WEEK
import com.example.webtoonhub.utils.Constants
import java.time.DayOfWeek
import java.util.*
import kotlin.collections.ArrayList

private lateinit var binding:ActivitySplashBinding
lateinit var dataList:ArrayList<WebToonData>
var dayWeek: API_DAY_WEEK=API_DAY_WEEK.MONDAY//기본요일을 월요일로 설정

class SplashActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(Constants.TAG,"SplashActivity - onCreate() called")
        super.onCreate(savedInstanceState)
        setBinding()
        setContentView(binding.root)
        setLodingImageListener()
        startLoadingImage()
        //오늘 요일 판별  dayWeek에 저장 타입은 enum클래스타입
        getTDayWeek()
        //오늘 요일 웹툰 데이터 호출

        //데이터를 메인 액티비티에  넘김


    }//onCreate()

    fun getTDayWeek(){
        //시스템요일(일~토 1~7) 과 api요일을 판단하는 숫자가달라서 enum쿨래스로 선언하여 when을 통해 판별하여 api요일 숫자로 저장
        val instance = Calendar.getInstance()
        val week=instance.get(Calendar.DAY_OF_WEEK)
        Log.d(Constants.TAG,"SplashActivity - getTDayWeek() called/week = $week")
//        when(week){//코드 간단하게 하는법 생각해보기
//            API_DAY_WEEK.MONDAY.systemDayNum->{
//                dayWeek=API_DAY_WEEK.MONDAY
//            }
//            API_DAY_WEEK.TUESDAY.systemDayNum->{
//                dayWeek=API_DAY_WEEK.TUESDAY
//            }
//            API_DAY_WEEK.WEDNESDAY.systemDayNum->{
//                dayWeek=API_DAY_WEEK.WEDNESDAY
//            }
//            API_DAY_WEEK.THURSDAY.systemDayNum->{
//                dayWeek=API_DAY_WEEK.THURSDAY
//            }
//            API_DAY_WEEK.FRIDAY.systemDayNum->{
//                dayWeek=API_DAY_WEEK.FRIDAY
//            }
//            API_DAY_WEEK.SATURDAY.systemDayNum->{
//                dayWeek=API_DAY_WEEK.SATURDAY
//            }
//            API_DAY_WEEK.SUNDAY.systemDayNum->{
//                dayWeek=API_DAY_WEEK.SUNDAY
//            }
//        }
        dayWeek= dayWeek.setDay(week)
        Log.d(Constants.TAG,"SplashActivity - getTDayWeek() called/dayWeek = ${dayWeek.dayWeek}//${dayWeek.dayNum}//${dayWeek.systemDayNum}")
    }


    //통신을 통해 데이터 가져오기 (viewModel)
    fun getData(){

    }

    fun setBinding(){
        binding= ActivitySplashBinding.inflate(layoutInflater)
    }
    fun startLoadingImage(){
        binding.loadingImage.apply {
            playAnimation()
            repeatCount=3//반복횟수   이것을 지정 안하면 무한반복이다. 나중에 애니매이션 무한반복 시키고 통신이 성공했을때 애니매이션 끝내고 액티비티 변환할것
        }
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
                startActivity(intent)//메인 액티비티 시작 , !!나중에는 통신성공 데이터 함께 넘겨야함
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

}