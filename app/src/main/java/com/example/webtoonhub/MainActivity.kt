package com.example.webtoonhub

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.webtoonhub.databinding.ActivityMainBinding
import com.example.webtoonhub.fragment.WeekFragmentStateAdapter
import com.example.webtoonhub.utils.Constants
import com.example.webtoonhub.utils.PLATFORM
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.webtoonhub.fragment.mainFragment
import java.util.*
import kotlin.collections.ArrayList


private lateinit var binding: ActivityMainBinding
private  var platform:PLATFORM=PLATFORM.naver//네이버가 기본설정
private lateinit var mainViewModel: MainViewModel
private lateinit var menuItem:MenuItem
class MainActivity : AppCompatActivity() {
    var fragments : ArrayList<Fragment> = ArrayList()
   // var tabs : ArrayList<TabLayout.Tab> = ArrayList()
    var dayweeks : ArrayList<String> = arrayListOf("월","화","수","목","금","토","일","완결")//텝 생성을 위한 배열
    var startToDayWeeks:ArrayList<String> = ArrayList()
    var dayWeeknum:Int=0//인텐트로 받아올 요일 데이터




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getIntents()//오늘 요일받아오기
        makeWeekList(dayWeeknum)
        setBinding()//바인딩
        setContentView(binding.root)
        Log.d(Constants.TAG, "MainActivity - onCreate() called")

        setActionBar()// 액션바의 오버라이드 함수로 (네이버,카카오)플랫폼 선택하는 액션바 설정
        setTabsListener()//요일 선택하는 탭바의 클릭 이벤트리스너//통신
        setTabsFragment()//프래그먼트 생성하고 그수만큼 탭바 생성
        setObserve()
        Log.d(Constants.TAG,"mainFragment - onCreateView() called - mainViewModel.webtoonDataList.value?.get(0)?.week= ${mainViewModel.webtoonDataList.value?.get(0)?.title}")

    }//onCreate

    fun setObserve(){//
        Log.d(Constants.TAG,"!!MainActivity - setObserve() called")
        mainViewModel.webtoonDataList.observe(this, androidx.lifecycle.Observer {
            Log.d(Constants.TAG,"!!MainActivity - setObserve() called  뷰모델의 웹툰데이터 변경됨 : 날짜= ${it[0].title}")

        })

    }


    //월~완결 순서를  오늘요일부터 시작하게 리스트 만들기.//tab을 오늘요일로 포커스 할 필요없다.
    fun makeWeekList( daynum:Int){
        var i=daynum
        do {
            startToDayWeeks.add(dayweeks[i])
            i++
            //Log.d(Constants.TAG,"i = $i / added ${dayweeks[i-1]}")
            if (i==8){
                Log.d(Constants.TAG,"i가 7이다 = $i")
                i=0
            }
        }while (startToDayWeeks.size<8)
        //Log.d(Constants.TAG,"tempDayWeeks $startToDayWeeks tempDayWeeks.size=${startToDayWeeks.size}")
    }
    //splashActivity로부터 인텐트 받기
    fun getIntents() {
        dayWeeknum=intent.getIntExtra("daynum",0)
    }
    //바인딩
    fun setBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        mainViewModel=ViewModelProvider(this).get(MainViewModel::class.java)
    }
    // 액션바의 오버라이드 함수로 (네이버,카카오)플랫폼 선택하는 액션바 설정
    fun setActionBar() {
        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setTitle("WebtoonHub")
    }
    //요일 선택하는 탭바의 클릭(포커스) 이벤트리스너//통신
    fun setTabsListener(){
        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            // 탭 버튼을 선택할 때 이벤트
            override fun onTabSelected(tab: TabLayout.Tab?) {
                var day= dayweeks.filter { tab?.text ==it }[0]
                if (tab != null) {
                    Log.d(Constants.TAG, "$day 선택, ${fragments[tab.position]} ")
                }
                mainViewModel.getWebtoonData(platform = platform.toString(),day)//선택된 요일의 웹툰 데이터 검색하기
            }
            // 선택된 탭 버튼을 다시 선택할 때 이벤트
            override fun onTabReselected(tab: TabLayout.Tab?) {  }

            // 다른 탭 버튼을 눌러 선택된 탭 버튼이 해제될 때 이벤트
            override fun onTabUnselected(tab: TabLayout.Tab?) {  }
        })
    }
    //프래그먼트 생성하고 그수만큼 탭바 생성
    fun setTabsFragment(){

        val pagerAdapter = WeekFragmentStateAdapter(fragmentActivity = this)
        for (a in startToDayWeeks){
            var fragment=mainFragment(a)
            fragments.add(fragment)
            pagerAdapter.addFragment(fragment)
        }

        binding.viewPager.adapter=pagerAdapter
        //탭바를 프래그먼트 수만클 생성
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text =startToDayWeeks[position]
            //tabs.add(tab)
            //Log.d(Constants.TAG,"포지션 : $position / tab : $tab")
        }.attach()
        //tabs[dayWeeknum].select()//오늘요일로 tab 포커스

    }

//액션바 사용을 위한 오버라이드 함수
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.platform_menu, menu)
        if (menu != null) {
            menuItem=menu.findItem(R.id.menu_naver)
        }
    onOptionsItemSelected(menuItem)
        Log.d(Constants.TAG, "MainActivity - onCreateOptionsMenu() calle ")
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.menu_naver -> {
                Toast.makeText(this, "네이버 선택", Toast.LENGTH_SHORT).show()
                Log.d(Constants.TAG, "MainActivity - onOptionsItemSelected() called/ 네이버 선택 ")
                binding.mainActivityLayout.setBackgroundColor(Color.GREEN)
                platform=PLATFORM.naver

            }
            R.id.menu_kakao -> {
                Toast.makeText(this, "카카오 선택", Toast.LENGTH_SHORT).show()
                Log.d(Constants.TAG, "MainActivity - onOptionsItemSelected() called/ 카카오 선택 ")
                binding.mainActivityLayout.setBackgroundColor(Color.YELLOW)
                platform=PLATFORM.kakao
            }
        }
        return super.onOptionsItemSelected(item)
    }

}