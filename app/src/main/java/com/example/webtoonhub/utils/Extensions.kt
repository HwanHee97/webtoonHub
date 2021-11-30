package com.example.webtoonhub.utils

//문자열이 json 형태인지
fun String?.isJsonObject():Boolean{
//    if (this?.startsWith("{")==true &&this.endsWith("}")){
//        return true
//    }else{
//        return false
//    }
    return this?.startsWith("{")==true &&this.endsWith("}")//위 코드를 한줄로 줄일수있다.
}
//json 배열 형태인지
fun String?.isJsonArray():Boolean{
//    if (this?.startsWith("[")==true &&this.endsWith("]")){
//        return true
//    }else{
//        return false
//    }
    return this?.startsWith("[")==true &&this.endsWith("]")    //위 코드를 한줄로 줄일수있다.
}