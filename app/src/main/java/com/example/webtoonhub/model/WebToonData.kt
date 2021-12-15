package com.example.webtoonhub.model

import java.io.Serializable


class WebToonData(
    var title: String?,//제목
    var author: String?,//작가
    var url: String?,//웹툰 사이트
    var thumbnail: String?,//썸네일 사진 url
    var platform: String?,//연재중인 플랫폼
    var week: Int?,//0~6월~일//7=완결
    //추가적인 정보(신규,휴재,새로운회차 업로드, 19세 이상)들도 있음
    var new :Boolean,
    var up : Boolean

) : Serializable {

}