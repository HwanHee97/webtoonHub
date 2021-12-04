package com.example.webtoonhub

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.webtoonhub.model.WebToonData
import com.example.webtoonhub.retrofit.RetrofitManager
import com.example.webtoonhub.utils.Constants
import com.example.webtoonhub.utils.RESPONSE_STATUS

class MainViewModel: ViewModel() {
    //요일별 웹툰 데이터 리스트
    private var _webtoonDataList=MutableLiveData<ArrayList<WebToonData>>()
    val webtoonDataList:LiveData<ArrayList<WebToonData>>
        get() =_webtoonDataList


    // 요일을 매개변수로 받아 api 호출
    fun getWebtoonData(platform: String,week:String){
            var queryWeek=changeWeekQuery(week)
            RetrofitManager.instance.getWeekWebtoonData(searchPlatform=platform, searchTerm = queryWeek, completion = { //completion을 사용한 이유는 비동기 처리를 위함
                responseState, responseDataArrayList ->
            when (responseState) {
                RESPONSE_STATUS.OKAY -> {
                    Log.d(Constants.TAG, "MainActivity - api 호출 성공: ${responseDataArrayList?.size}")

                }
                RESPONSE_STATUS.FAIL -> {

                    Log.d(Constants.TAG, "MainActivity - api 호출 실패: $responseDataArrayList")
                }
                RESPONSE_STATUS.NO_CONTENT -> {

                    Log.d(Constants.TAG, "MainActivity - 검색 결과가 없습니다.")
                }
            }
        })
    }
    fun changeWeekQuery(week: String) :String{
        return when(week) {
            "월" ->"mon"
            "화" -> "tue"
            "수" -> "wed"
            "목" -> "thu"
            "금" ->"fri"
            "토" ->"sat"
            "일" -> "sun"
            else -> ""
        }
    }

}