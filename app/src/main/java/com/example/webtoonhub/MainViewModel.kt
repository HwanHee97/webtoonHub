package com.example.webtoonhub

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.getColor
import androidx.lifecycle.*
import com.example.webtoonhub.databinding.ActivityMainBinding
import com.example.webtoonhub.model.WebToonData
import com.example.webtoonhub.retrofit.RetrofitManager
import com.example.webtoonhub.utils.Constants
import com.example.webtoonhub.utils.PLATFORM
import com.example.webtoonhub.utils.RESPONSE_STATUS
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    //요일별 웹툰 데이터 리스트
    private var _webtoonDataList=MutableLiveData<ArrayList<WebToonData>>()
    val webtoonDataList:LiveData<ArrayList<WebToonData>>
        get() =_webtoonDataList

    private var _platform=MutableLiveData<PLATFORM>()
    val platform:LiveData<PLATFORM>
        get() =_platform

    private var _responseCode=MutableLiveData<Int>()
    val responseCode:LiveData<Int>
        get() =_responseCode

    fun getResponseState(){
        viewModelScope.launch {
            RetrofitManager.instance.getResponseStateCode(completion = {
                    responseState ->
                when (responseState) {
                    RESPONSE_STATUS.OKAY -> {
                        Log.d(Constants.TAG, "MainViewModel - api 연결 성공: ")
                        _responseCode.value=200
                    }
                    RESPONSE_STATUS.FAIL -> {
                        Log.d(Constants.TAG, "MainViewModel - api 연결 실패: ")
                    }
                }
            })
        }
    }
    // 요일을 매개변수로 받아 api 호출
    fun getWebtoonData(platform: PLATFORM,week:String){
        viewModelScope.launch {
            var queryWeek=changeWeekQuery(week)

            RetrofitManager.instance.getWeekWebtoonData(type = "search",searchPlatform=platform, searchTerm = null,searchWeek = queryWeek, completion = {
                responseState, responseDataArrayList ->
                when (responseState) {
                    RESPONSE_STATUS.OKAY -> {
                        Log.d(
                            Constants.TAG,
                            "MainViewModel - api 호출 성공: ${responseDataArrayList?.size}"
                        )
                        _webtoonDataList.value = responseDataArrayList
                        _platform.value = platform
                    }
                    RESPONSE_STATUS.FAIL -> {
                        Toast.makeText(App.instance, "데이터 로드 실패", Toast.LENGTH_SHORT).show()
                        Log.d(Constants.TAG, "MainViewModel - api 호출 실패: $responseDataArrayList")
                    }
                    RESPONSE_STATUS.NO_CONTENT -> {
                        Log.d(Constants.TAG, "MainViewModel - 검색 결과가 없습니다.")
                        Toast.makeText(App.instance, "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }
    fun getSearchCustomizeWebtoonData(searchQuery:String){
        viewModelScope.launch {

            RetrofitManager.instance.getWeekWebtoonData(type = "searchCustomize",searchPlatform=null, searchTerm = searchQuery,searchWeek = null ,completion = {
                    responseState, responseDataArrayList ->
                when (responseState) {
                    RESPONSE_STATUS.OKAY -> {
                        Log.d(Constants.TAG, "MainViewModel - api 호출 성공: ${responseDataArrayList?.size}")
                        _webtoonDataList.value=responseDataArrayList
                        _platform.value=PLATFORM.CUSTOM_SEARCH
                    }
                    RESPONSE_STATUS.FAIL -> {
                        Log.d(Constants.TAG, "MainViewModel - api 호출 실패: $responseDataArrayList")
                    }
                    RESPONSE_STATUS.NO_CONTENT -> {
                        Log.d(Constants.TAG, "MainViewModel - 검색 결과가 없습니다.")
                        Toast.makeText(App.instance,"검색 결과가 없습니다.",Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    private fun changeWeekQuery(week: String) :Int{
        return when(week) {
            "월" -> 0
            "화" -> 1
            "수" -> 2
            "목" -> 3
            "금" -> 4
            "토" -> 5
            "일" -> 6
            else -> 0
        }
    }
}