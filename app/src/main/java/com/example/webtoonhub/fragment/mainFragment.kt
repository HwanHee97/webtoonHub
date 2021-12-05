package com.example.webtoonhub.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.webtoonhub.MainViewModel
import com.example.webtoonhub.R
import com.example.webtoonhub.databinding.FragmentMainBinding
import com.example.webtoonhub.utils.Constants
import com.google.android.material.tabs.TabLayoutMediator

private lateinit var binding: FragmentMainBinding
private lateinit var mainViewModel: MainViewModel

class mainFragment(val dayWeek:String) : Fragment() {
    // TODO: Rename and change types of parameters


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(Constants.TAG,"mainFragment - onCreate() called")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.d(Constants.TAG,"mainFragment - onCreateView() called - dayWeek= $dayWeek")

        setBinding()
        setObserve()
        Log.d(Constants.TAG,"mainFragment - onCreateView() called - mainViewModel.webtoonDataList.value?.get(0)?.week= ${mainViewModel.webtoonDataList.value?.get(0)?.title}")

        return binding.root
    }
    fun setBinding(){
        Log.d(Constants.TAG,"!!mainFragment - setBinding() called")
        binding= FragmentMainBinding.inflate(layoutInflater)
        mainViewModel=ViewModelProvider(this).get(MainViewModel::class.java)
        binding.fragmentTv.text="$dayWeek 요일 프래그먼트 입니다~~"
    }
    fun setObserve(){//
        Log.d(Constants.TAG,"!!mainFragment - setObserve() called")
        mainViewModel.webtoonDataList.observe(viewLifecycleOwner, Observer {
            Log.d(Constants.TAG,"!!mainFragment - setObserve() called  뷰모델의 웹툰데이터 변경됨 : 날짜= ${it[0].week}")

        })

    }

}