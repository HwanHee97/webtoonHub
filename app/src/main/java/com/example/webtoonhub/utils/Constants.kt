package com.example.webtoonhub.utils

import java.util.*

object Constants {
    const val TAG:String="로그"
}
enum class RESPONSE_STATUS{
    OKAY,
    FAIL,
    NO_CONTENT
}
object API{
    const val BASE_URL:String="https://korea-webtoon-api.herokuapp.com"
    const val GET_ALL:String="all"
    const val SEARCH:String="/{platform}/week"
    const val SEARCH_CUSTOMIZE:String="/search"

    //모두 검색 https://korea-webtoon-api.herokuapp.com/all
    //검색시 https://korea-webtoon-api.herokuapp.com/search?keyword="검색어"
    //요일별 검색 https://korea-webtoon-api.herokuapp.com/naver/week?day=2
}
enum class PLATFORM(val string: String) {
    KAKAOPAGE("kakao-page"),
    NAVER("naver"),
    KAKAO("kakao"),
    CUSTOM_SEARCH("custom-search")

}
enum class API_DAY_WEEK(val dayWeek:String, val dayNum:Int,val systemDayNum:Int){
    MONDAY("월",0,2),
    TUESDAY("화",1,3),
    WEDNESDAY("수",2,4),
    THURSDAY("목",3,5),
    FRIDAY("금",4,6),
    SATURDAY("토",5,7),
    SUNDAY("일",6,1);
    fun setDay(num:Int):API_DAY_WEEK{
        return when (num) {
            MONDAY.systemDayNum -> MONDAY
            TUESDAY.systemDayNum -> TUESDAY
            WEDNESDAY.systemDayNum -> WEDNESDAY
            THURSDAY.systemDayNum -> THURSDAY
            FRIDAY.systemDayNum -> FRIDAY
            SATURDAY.systemDayNum -> SATURDAY
            SUNDAY.systemDayNum -> SUNDAY
            else -> MONDAY//기본요일을 월요일로 설정
        }
    }
}
fun getTodayOfTheWeek(): API_DAY_WEEK {
    val instance = Calendar.getInstance()
    val week=instance.get(Calendar.DAY_OF_WEEK)
    return API_DAY_WEEK.MONDAY.setDay(week)
}
fun getApiDayWeek(week: String):API_DAY_WEEK{
    return when(week){
        API_DAY_WEEK.MONDAY.dayWeek -> API_DAY_WEEK.MONDAY
        API_DAY_WEEK.TUESDAY.dayWeek -> API_DAY_WEEK.TUESDAY
        API_DAY_WEEK.WEDNESDAY.dayWeek -> API_DAY_WEEK.WEDNESDAY
        API_DAY_WEEK.THURSDAY.dayWeek -> API_DAY_WEEK.THURSDAY
        API_DAY_WEEK.FRIDAY.dayWeek -> API_DAY_WEEK.FRIDAY
        API_DAY_WEEK.SATURDAY.dayWeek -> API_DAY_WEEK.SATURDAY
        API_DAY_WEEK.SUNDAY.dayWeek -> API_DAY_WEEK.SUNDAY
        else -> API_DAY_WEEK.MONDAY
    }
}
