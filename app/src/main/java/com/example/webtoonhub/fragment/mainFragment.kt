package com.example.webtoonhub.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.example.webtoonhub.MainViewModel
import com.example.webtoonhub.databinding.FragmentMainBinding
import com.example.webtoonhub.model.WebToonData
import com.example.webtoonhub.utils.Constants
import okhttp3.internal.notify

private lateinit var binding: FragmentMainBinding
private lateinit var mainViewModel: MainViewModel

class mainFragment() : Fragment() {
    // TODO: Rename and change types of parameters


    private lateinit var webtoonDataList:ArrayList<WebToonData>

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

        setBinding()
        //setObserve()
        return binding.root
    }
    fun setBinding(){
        Log.d(Constants.TAG,"mainFragment - setBinding() called")
       // binding= FragmentMainBinding.inflate(layoutInflater)
        binding= FragmentMainBinding.inflate(LayoutInflater.from(this.context))
        mainViewModel=ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        //binding.fragmentTv.text="$dayWeek 요일 프래그먼트 입니다~~"
    }
    fun setData(responseDataArrayList: ArrayList<WebToonData>?) {
        Log.d(Constants.TAG,"mainFragment - setData() called ${responseDataArrayList?.get(0)?.title} // $this")

        if (responseDataArrayList != null) {
            webtoonDataList=responseDataArrayList
        }
        //binding.fragmentTv.text="${webtoonDataList[0].title} -> 첫번째 아이템 제목~~"
        binding.item="${webtoonDataList[0].title} -> 첫번째 아이템 제목~~"

    }
//    fun setObserve(){//
//        Log.d(Constants.TAG,"!!mainFragment - setObserve() called")
//       mainViewModel.webtoonDataList.observe(requireActivity(), androidx.lifecycle.Observer {
//            Log.d(Constants.TAG,"!!mainFragment - setObserve() called  뷰모델의 웹툰데이터 변경됨 : 첫번째 웹툰 제목 = ${it[0].title}")
//           //binding.fragmentTv.text="${it[0].title} -> 첫번째 아이템 제목~~"
//       })
//
//
//    }



}