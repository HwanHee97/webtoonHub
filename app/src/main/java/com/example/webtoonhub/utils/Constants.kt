package com.example.webtoonhub.utils

object Constants {
    const val TAG:String="로그"
}
enum class RESPONSE_STATUS{
    OKAY,
    FAIL,
    NO_CONTENT
}
object API{
    const val BASE_URL:String="https://korea-webtoon-api.herokuapp.com/"
    const val GET_ALL:String="all"
    const val SEARCH:String=""
    const val SEARCH_WEEK:String="all/week"
    //모두 검색 https://korea-webtoon-api.herokuapp.com/all
    //검색시 https://korea-webtoon-api.herokuapp.com/?search="검색어"
    //요일별 검색 https://korea-webtoon-api.herokuapp.com/all/week?day=fri
}
enum class PLATFORM{
    NAVER,
    KAKAO

}
enum class API_DAY_WEEK(val dayWeek:String, val dayNum:Int,val systemDayNum:Int){
    MONDAY("mon",0,2),
    TUESDAY("tue",1,3),
    WEDNESDAY("wed",2,4),
    THURSDAY("thu",3,5),
    FRIDAY("fri",4,6),
    SATURDAY("sat",5,7),
    SUNDAY("sun",6,1);
    fun setDay(num:Int):API_DAY_WEEK{
        return when (num) {
            MONDAY.systemDayNum -> MONDAY
            TUESDAY.systemDayNum -> TUESDAY
            WEDNESDAY.systemDayNum -> WEDNESDAY
            THURSDAY.systemDayNum -> THURSDAY
            FRIDAY.systemDayNum -> FRIDAY
            systemDayNum -> SATURDAY
            systemDayNum -> SUNDAY
            else -> MONDAY
        }
    }
}