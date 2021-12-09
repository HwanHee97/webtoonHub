package com.example.webtoonhub.recyclerview

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.webtoonhub.databinding.LayoutWebtoonItemBinding
import com.example.webtoonhub.model.WebToonData
import com.example.webtoonhub.utils.Constants

class WebtoonRecyclerViewAdapter(var webtoonList: ArrayList<WebToonData>, val context: Context) :
    RecyclerView.Adapter<WebtoonItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebtoonItemViewHolder {
        Log.d(Constants.TAG,"PhotoGridRecyclerViewAdapter - onCreateViewHolder() called")
        val binding = LayoutWebtoonItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return WebtoonItemViewHolder(binding,context)
    }
    //뷰홀더에 있는 바인드 함수 호출,  리스트값 하나씩 전달
    override fun onBindViewHolder(holder: WebtoonItemViewHolder, position: Int) {
        holder.bindWithView(this.webtoonList[position])
    }
    //보여줄 목록의 갯수
    override fun getItemCount(): Int {
        return this.webtoonList.size
    }
    fun notifyDataChange(list:ArrayList<WebToonData>){
        webtoonList=list
        notifyDataSetChanged()
    }

}