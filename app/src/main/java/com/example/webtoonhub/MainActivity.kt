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
import com.example.webtoonhub.retrofit.RetrofitManager
import com.example.webtoonhub.utils.Constants
import com.example.webtoonhub.utils.PLATFORM
import com.example.webtoonhub.utils.RESPONSE_STATUS
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import androidx.annotation.NonNull
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.webtoonhub.fragment.mainFragment
import java.util.*


private lateinit var binding: ActivityMainBinding
lateinit var platform:PLATFORM

class MainActivity : AppCompatActivity() {
    var fragments : ArrayList<Fragment> = ArrayList()
    var week : ArrayList<String> = arrayListOf("월","화","수","목","금","토","일","완결")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBinding()//바인딩
        setContentView(binding.root)
        Log.d(Constants.TAG, "MainActivity - onCreate() called")

        setActionBar()// 액션바의 오버라이드 함수로 (네이버,카카오)플랫폼 선택하는 액션바 설정
        setTabs()//요일 선택하는 탭바의 클릭(포커스) 이벤트
        setFragment()//프래그먼트 생성하고 그수만큼 탭바 생성






    }//onCreate
    //프래그먼트 생성하고 그수만큼 탭바 생성
    fun setFragment(){

        val pagerAdapter = WeekFragmentStateAdapter(fragmentActivity = this)
        for (a in week){
            var fragment=mainFragment()
            fragments.add(fragment)
            pagerAdapter.addFragment(fragment)
        }

        binding.viewPager.adapter=pagerAdapter
        //탭바를 프래그먼트 수만클 생성
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text =week[position]
            Log.d(Constants.TAG,"포지션 : $position / tab : $tab")
        }.attach()

    }
    fun setBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
    }

    fun setActionBar() {
        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setTitle("WebtoonHub")
    }
    //탭바의 클릭(포커스) 이벤트
    fun setTabs(){
        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            // 탭 버튼을 선택할 때 이벤트
            override fun onTabSelected(tab: TabLayout.Tab?) {
//                when(tab?.text) {
//                    week[0] ->Log.d(Constants.TAG, "월 선택 ")
//                    week[1] -> Log.d(Constants.TAG, "화 선택 ")
//                    week[2] -> Log.d(Constants.TAG, "수 선택 ")
//                    week[3] -> Log.d(Constants.TAG, "목 선택 ")
//                    week[4] -> Log.d(Constants.TAG, "금 선택 ")
//                    week[5] -> Log.d(Constants.TAG, "토 선택 ")
//                    week[6] -> Log.d(Constants.TAG, "일 선택 ")
//                    week[7] -> Log.d(Constants.TAG, "완결 선택 ")
//
//                }
                var day= week.filter { tab?.text ==it }[0]
                if (tab != null) {
                    Log.d(Constants.TAG, "$day 선택, ${fragments[tab.position]} ")
                }
            }
            // 선택된 탭 버튼을 다시 선택할 때 이벤트
            override fun onTabReselected(tab: TabLayout.Tab?) {  }

            // 다른 탭 버튼을 눌러 선택된 탭 버튼이 해제될 때 이벤트
            override fun onTabUnselected(tab: TabLayout.Tab?) {  }
        })
    }

    //splashActivity로부터 인텐트 받기
    fun getIntents() {

    }


//액션바 사용을 위한 오버라이드 함수
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.day_menu, menu)
        Log.d(Constants.TAG, "MainActivity - onCreateOptionsMenu() calle ")
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.menu_naver -> {
                Toast.makeText(this, "네이버 선택", Toast.LENGTH_SHORT).show()
                Log.d(Constants.TAG, "MainActivity - onOptionsItemSelected() called/ 네이버 선택 ")
                binding.mainActivityLayout.setBackgroundColor(Color.GREEN)
                platform=PLATFORM.NAVER

            }
            R.id.menu_kakao -> {
                Toast.makeText(this, "카카오 선택", Toast.LENGTH_SHORT).show()
                Log.d(Constants.TAG, "MainActivity - onOptionsItemSelected() called/ 카카오 선택 ")
                binding.mainActivityLayout.setBackgroundColor(Color.YELLOW)
                platform=PLATFORM.KAKAO
            }
        }
        return super.onOptionsItemSelected(item)
    }

}