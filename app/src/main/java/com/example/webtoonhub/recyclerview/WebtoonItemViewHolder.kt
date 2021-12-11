package com.example.webtoonhub.recyclerview

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.webtoonhub.databinding.LayoutWebtoonItemBinding
import com.example.webtoonhub.model.WebToonData
import com.example.webtoonhub.utils.Constants

import com.bumptech.glide.request.RequestOptions
import com.example.webtoonhub.*

class WebtoonItemViewHolder (val binding: LayoutWebtoonItemBinding,val context: Context):RecyclerView.ViewHolder(binding.root){


    fun bindWithView(item:WebToonData){
        Log.d(Constants.TAG,"PhotoItemViewHolder - bindWithView() called/${item.thumbnail}/")
        with(binding){
            webtoonItem=item
            loadPhotoImage(webtoonThumbnail,item.thumbnail,item.url)
        }
    }
    fun loadPhotoImage(view: ImageView, imageUrl: String?, showUrl:String?) {
        Log.d(Constants.TAG,"!!!!PhotoItemViewHolder - loadPhotoImage() called/$imageUrl")
        //kipMemoryCache() : 메모리에 캐싱하지 않으려면 true로 준다//diskCacheStrategy() : 디스크에 캐싱하지 않으려면 DiskCacheStrategy.NONE로 준다. 다음과 같은 옵션이 있다. (ALL, AUTOMATIC, DATA, NONE, RESOURCE)
        val options = RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
        //val glideUrl = GlideUrl(imageUrl)

        Glide.with(context)
            .load(imageUrl.toString())//표시할 이미지 값(링크).
            .placeholder(R.drawable.ic_baseline_insert_photo_24)//이미지가 없으면 나오는 기본 화면
            .apply(options)
            .into(view)//표시할 이미지 뷰
        view.setOnClickListener{
            //mainaFragment에서 웹뷰 액티비티를 호출위해
            // mainaFragment에서의 context를 어댑터 생성시에 받아오고 그것을 holder생성시에 받아와서 사용
            //액티비티가아닌 viewholderd에서 사용하기 때문에 context가 필요
            val nextIntent = Intent(context, WebViewActivity::class.java)
            nextIntent.putExtra("view_url", showUrl)
            context.startActivity(nextIntent)
        }
    }


}
