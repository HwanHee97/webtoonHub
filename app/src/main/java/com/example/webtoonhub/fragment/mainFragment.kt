package com.example.webtoonhub.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.webtoonhub.MainViewModel
import com.example.webtoonhub.databinding.FragmentMainBinding
import com.example.webtoonhub.model.WebToonData
import com.example.webtoonhub.utils.Constants

private lateinit var binding: FragmentMainBinding
private lateinit var mainViewModel: MainViewModel

class mainFragment(val dayWeek:String) : Fragment() {
    // TODO: Rename and change types of parameters



    private lateinit var webtoonDataList:ArrayList<WebToonData>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(Constants.TAG,"mainFragment - onCreate() called $this")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.d(Constants.TAG,"mainFragment - onCreateView() called - dayWeek= $dayWeek")

        setBinding()

        return binding.root
    }
    fun setBinding(){
        Log.d(Constants.TAG,"!!mainFragment - setBinding() called")
        binding= FragmentMainBinding.inflate(layoutInflater)
        mainViewModel=ViewModelProvider(this).get(MainViewModel::class.java)
        binding.fragmentTv.text="$dayWeek 요일 프래그먼트 입니다~~"
    }
    fun getData(responseDataArrayList: ArrayList<WebToonData>?) {
        if (responseDataArrayList != null) {
            webtoonDataList=responseDataArrayList
        }
        binding.fragmentTv.text="${webtoonDataList[0].title} -> 첫번째 아이템 제목~~"
    }


}