package com.example.webtoonhub.utils

object Constants {
    const val TAG:String="로그"
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