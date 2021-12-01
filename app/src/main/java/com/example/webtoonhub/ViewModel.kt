package com.example.webtoonhub

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.webtoonhub.model.WebToonData

class ViewModel {
    //요일별 웹툰 데이터 리스트
    private var _webtoonDataList=MutableLiveData<ArrayList<WebToonData>>()
    val webtoonDataList:LiveData<ArrayList<WebToonData>>
        get() =_webtoonDataList


    // 요일을 매개변수로 받아 api 호출
    fun getWebtoonData(week:String){
        //        RetrofitManager.instance.getWeekWebtoonData(searchTerm = week, completion = { //completion을 사용한 이유는 비동기 처리를 위함
//                responseState, responseDataArrayList ->
//            when (responseState) {
//                RESPONSE_STATUS.OKAY -> {
//                    Log.d(Constants.TAG, "MainActivity - api 호출 성공: ${responseDataArrayList?.size}")
//
//                }
//                RESPONSE_STATUS.FAIL -> {
//
//                    Log.d(Constants.TAG, "MainActivity - api 호출 실패: $responseDataArrayList")
//                }
//                RESPONSE_STATUS.NO_CONTENT -> {
//
//                    Log.d(Constants.TAG, "MainActivity - 검색 결과가 없습니다.")
//                }
//            }
//        })
    }

}