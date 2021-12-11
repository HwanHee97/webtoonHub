package com.example.webtoonhub.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.webtoonhub.MainViewModel
import com.example.webtoonhub.R
import com.example.webtoonhub.databinding.FragmentMainBinding
import com.example.webtoonhub.model.WebToonData
import com.example.webtoonhub.recyclerview.WebtoonRecyclerViewAdapter
import com.example.webtoonhub.utils.Constants

class mainFragment() : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private var webtoonDataList=ArrayList<WebToonData>()
    private  val webtoonRecyclerViewAdapter : WebtoonRecyclerViewAdapter by lazy {
        WebtoonRecyclerViewAdapter(webtoonDataList,this.requireContext())
    }//선언과 동시에 초기화

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(Constants.TAG,"mainFragment - onCreate() called")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.d(Constants.TAG,"mainFragment - onCreateView() called")
        //binding=DataBindingUtil.inflate(inflater,R.layout.fragment_main,container,false)
        binding= FragmentMainBinding.inflate(layoutInflater)
        setRecyclerView()
        return binding.root
    }

    fun setRecyclerView(){
        binding.myWebtoonRecyclerView.apply {
            layoutManager= GridLayoutManager(this.context,2, GridLayoutManager.VERTICAL,false)
            adapter= webtoonRecyclerViewAdapter
        }
    }

    fun setData(responseDataArrayList: ArrayList<WebToonData>?) {

        Log.d(Constants.TAG,"mainFragment - setData() called ${responseDataArrayList?.get(1)?.title} // $this")
        if (responseDataArrayList == null) {
            Log.e(Constants.TAG,"Null!!!!!!")
        }
        if (responseDataArrayList != null) {
            webtoonDataList=responseDataArrayList
        }
        //webtoonRecyclerViewAdapter.notifyDataChange(webtoonDataList)
        webtoonRecyclerViewAdapter.notifyDataChange(webtoonDataList)
    }

}