package com.example.webtoonhub.recyclerview

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.view.isGone
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.model.GlideUrl
import com.example.webtoonhub.databinding.LayoutWebtoonItemBinding
import com.example.webtoonhub.model.WebToonData
import com.example.webtoonhub.utils.Constants
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.bumptech.glide.signature.ObjectKey
import com.example.webtoonhub.*


class WebtoonItemViewHolder (val binding: LayoutWebtoonItemBinding,val context: Context):RecyclerView.ViewHolder(binding.root){

    fun bindWithView(item:WebToonData){
        Log.d(Constants.TAG,"WebtoonItemViewHolder - bindWithView() called/${item.thumbnail}/")
        with(binding){
            wbImg.visibility=View.GONE
            webtoonThumbnail.visibility=View.VISIBLE
            webtoonItem=item
            loadThumbnailImage(webtoonThumbnail,item.thumbnail,item.url)
        }
    }
    fun bindWithNaverView(item: WebToonData) {
        Log.d(Constants.TAG, "WebtoonItemViewHolder - bindWithNaverView() called/${item.thumbnail}/")
        with(binding) {
            webtoonThumbnail.visibility=View.GONE
            wbImg.visibility=View.VISIBLE
            webtoonItem = item
            wbImg.apply {
                settings.javaScriptEnabled = false
                webViewClient = WebViewClient()
                //webChromeClient = WebChromeClient()
                settings.apply {
                    useWideViewPort = true
                    loadWithOverviewMode = true
                }
                item.thumbnail?.let { loadUrl(it) }
            }
            setOnClickListener(itemLayoutId,item.url)
        }
    }
    fun loadThumbnailImage(view: ImageView, imageUrl: String?, showUrl:String?) {
        Log.d(Constants.TAG,"!!!!WebtoonItemViewHolder - loadPhotoImage() called/$imageUrl")
        //skipMemoryCache() : 메모리에 캐싱하지 않으려면 true로 준다//diskCacheStrategy() : 디스크에 캐싱하지 않으려면 DiskCacheStrategy.NONE로 준다. 다음과 같은 옵션이 있다. (ALL, AUTOMATIC, DATA, NONE, RESOURCE)
        val options = RequestOptions()
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .signature(ObjectKey(System.currentTimeMillis()))
        val glideUrl = GlideUrl(imageUrl)
        Glide.with(context)
            .load(glideUrl)//표시할 이미지 값(링크).
            .placeholder(R.drawable.ic_baseline_insert_photo_24)//이미지가 로딩중 기본 화면
            .error(R.drawable.ic_launcher_foreground)//이미지 불러오기 애러시 나오는 화면
            .apply(options)
            .into(view) //표시할 이미지 뷰
            setOnClickListener(binding.itemLayoutId,showUrl)
    }
    fun setOnClickListener(layoutView: CardView, showUrl: String?) {
        layoutView.setOnClickListener {
            val nextIntent = Intent(context, WebViewActivity::class.java)
            nextIntent.putExtra("view_url", showUrl)
            context.startActivity(nextIntent.addFlags(FLAG_ACTIVITY_NEW_TASK))//액티비티가 아닌곳에서 startActivity를 하기때문에  FLAG_ACTIVITY_NEW_TASK추가
        }
    }

}
