package com.example.webtoonhub.recyclerview

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.webtoonhub.App
import com.example.webtoonhub.R
import com.example.webtoonhub.databinding.LayoutWebtoonItemBinding
import com.example.webtoonhub.model.WebToonData
import com.example.webtoonhub.utils.Constants
import retrofit2.http.Url
import java.net.URI
import java.net.URL
import com.bumptech.glide.load.model.LazyHeaders

import com.bumptech.glide.load.model.GlideUrl
import com.example.webtoonhub.WebViewActivity


class WebtoonItemViewHolder (val binding: LayoutWebtoonItemBinding,val context: Context):RecyclerView.ViewHolder(binding.root){


    fun bindWithView(item:WebToonData){
        Log.d(Constants.TAG,"PhotoItemViewHolder - bindWithView() called/${item.thumbnail}/")
        //이미지만을 위한 어댑터를  Photo클래스 안에 이미지 바인딩 어댑터함수를 따로 선언하고 xml파일에서 함수이름=@{이미지url} 을 선언히여 사용가능
        with(binding){
            webtoonItem=item
            loadPhotoImage(webtoonThumbnail,item.thumbnail,item.url)
        }
    }
    fun loadPhotoImage(view: ImageView, imageUrl: String?, downloadUrl:String?) {
        Log.d(Constants.TAG,"!!!!PhotoItemViewHolder - loadPhotoImage() called/$imageUrl")
//        val glideUrl = GlideUrl(
//            imageUrl, LazyHeaders.Builder()
//                .addHeader(
//                    "User-Agent",
//                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_2) AppleWebKit / 537.36(KHTML, like Gecko) Chrome  47.0.2526.106 Safari / 537.36"
//                )
//                .build()
//        )
        val glideUrl = GlideUrl(imageUrl)
        Glide.with(App.instance)
            .load(glideUrl)//표시할 이미지 값(링크).
            .placeholder(R.drawable.ic_baseline_insert_photo_24)//이미지가 없으면 나오는 기본 화면
            .into(view)//표시할 이미지 뷰
        view.setOnClickListener{
            //photocollectionactivity에서 다운로드 액티비티를 호출위해
            // photocollectionactivity의 context를 어댑터 생성시에 받아오고 그것을 holder생성시에 받아와서 사용
            //액티비티가아닌 viewholderd에서 사용하기 때문에 context가 필요
            val nextIntent = Intent(context, WebViewActivity::class.java)
            nextIntent.putExtra("view_url", downloadUrl)
            context.startActivity(nextIntent)
        }
    }


}
