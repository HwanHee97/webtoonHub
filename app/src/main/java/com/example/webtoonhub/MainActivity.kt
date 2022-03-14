package com.example.webtoonhub

import android.animation.Animator
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import com.example.webtoonhub.databinding.ActivityMainBinding
import com.example.webtoonhub.fragment.WeekFragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.webtoonhub.fragment.mainFragment
import com.example.webtoonhub.utils.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener,
androidx.appcompat.widget.SearchView.OnQueryTextListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var menuItem: MenuItem
    private lateinit var pagerAdapter: WeekFragmentStateAdapter
    private var platform: PLATFORM = PLATFORM.NAVER//네이버가 기본설정
    private lateinit var mySearchView: androidx.appcompat.widget.SearchView
    private lateinit var mySearchViewEditText: EditText
    private var fragments: ArrayList<mainFragment> = ArrayList()
    private var dayWeek: API_DAY_WEEK = getToDayWeek()//기본요일을 월요일로 설정
    private var startToDayWeeks: ArrayList<String> = ArrayList()//인텐트로 받아올 오늘요일부터 시작하는 요일 리스트
    private var selectTab: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(Constants.TAG, "MainActivity - onCreate() called")
        setBinding()//바인딩
        setContentView(binding.root)
        setObserve()
        Log.d(
            Constants.TAG,
            "MainActivity - getTDayWeek() called/dayWeek = ${dayWeek.dayWeek}//${dayWeek.dayNum}//${dayWeek.systemDayNum}"
        )

    }//onCreate

    //    private fun getTDayWeek(){
//        //시스템요일(일~토 1~7) 과 api요일을 판단하는 숫자가달라서 enum쿨래스로 선언하여 when을 통해 판별하여 api요일 숫자로 저장
//        val instance = Calendar.getInstance()
//        val week=instance.get(Calendar.DAY_OF_WEEK)
//        dayWeek= dayWeek.setDay(week)
//        Log.d(Constants.TAG,"MainActivity - getTDayWeek() called/dayWeek = ${dayWeek.dayWeek}//${dayWeek.dayNum}//${dayWeek.systemDayNum}")
//    }
    //월~완결 순서를  오늘요일부터 시작하게 리스트 만들기.
    private fun makeWeekList(daynum: Int) {
        var baseDayweeks: Array<out String> =
            resources.getStringArray(R.array.base_week_array)//arrays.xml에서 가져오기
        var i = daynum
        //요일 리스트 생성하는 알고리즘
        for (e in baseDayweeks) {
            startToDayWeeks.add(baseDayweeks[i % baseDayweeks.size])
            i++
        }
    }

    //바인딩
    private fun setBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        Log.d(
            Constants.TAG,
            "${mainViewModel.uiState},${mainViewModel.uiState.value},${mainViewModel.uiState.toString()}"
        )
    }

    // 액션바의 오버라이드 함수로 (네이버,카카오)플랫폼 선택하는 액션바 설정
    private fun setActionBar() {
        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setTitle("WebtoonHub")
    }

    //요일 선택하는 탭바의 클릭(포커스) 이벤트리스너//통신
    private fun setTabsListener() {
        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            // 탭 버튼을 선택할 때 이벤트
            override fun onTabSelected(tab: TabLayout.Tab?) {
                //선택퇸 요일 구하기
                var day = startToDayWeeks.filter { tab?.text == it }[0]
                //선택된 요일 데이터 호출
                if (tab != null) {
                    selectTab = tab.position
                    Log.d(
                        Constants.TAG,
                        "----------$day 선택, / tab 포지션 = ${tab.position} -----------"
                    )
                    mainViewModel.getWebtoonData(platform = platform, day) //선택된 요일의 웹툰 데이터 검색하기
                }
            }

            // 선택된 탭 버튼을 다시 선택할 때 이벤트
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            // 다른 탭 버튼을 눌러 선택된 탭 버튼이 해제될 때 이벤트
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
        })
    }

    //프래그먼트 생성하고 그수만큼 탭바 생성
    private fun setTabsFragment() {
        //요일만큼 프래그먼트 생성
        startToDayWeeks.forEach {
            fragments.add(mainFragment())
        }
        pagerAdapter = WeekFragmentStateAdapter(fragments, this)
        binding.viewPager.adapter = pagerAdapter
        //탭바를 프래그먼트 수만클 생성
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = startToDayWeeks[position]
            //tabs.add(tab)
        }.attach()
    }

    //액션바 사용을 위한 오버라이드 함수
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.platform_menu, menu)
        if (menu != null) {
            menuItem = menu.findItem(R.id.menu_naver)
        }
        onOptionsItemSelected(menuItem)
        this.mySearchView =
            menu?.findItem(R.id.search_menu_item)?.actionView as androidx.appcompat.widget.SearchView
        this.mySearchView.apply {
            this.queryHint = "검색어를 입력해 주세요"
            this.setOnQueryTextListener(this@MainActivity)
            this.setOnQueryTextFocusChangeListener { _, hasExpaned ->
                when (hasExpaned) {
                    true -> {
                        Log.d(Constants.TAG, "서치뷰 열림 ")
                    }
                    false -> {
                        Log.d(Constants.TAG, "서치뷰 닫힘 ")
                    }
                }
            }
            //searchviewEditText 가져오기
            mySearchViewEditText = this.findViewById(androidx.appcompat.R.id.search_src_text)
        }
        this.mySearchViewEditText.apply {
            this.filters = arrayOf(InputFilter.LengthFilter(12))//검색글자수 12 로 제한
            this.setTextColor(Color.WHITE)
            this.setHintTextColor(Color.WHITE)
        }
        //Log.d(Constants.TAG, "MainActivity - onCreateOptionsMenu() calle ")
        return super.onCreateOptionsMenu(menu)
    }

    //검색 눌렸을때
    override fun onQueryTextSubmit(query: String?): Boolean {
        //Log.d(Constants.TAG, "MainActivity - onQueryTextSubmit() called / query: ${query}")
        if (!query.isNullOrEmpty()) {
            //api호출!!!!!
            mainViewModel.getSearchCustomizeWebtoonData(query)
        }
        return true
    }

    //텍스트 입력시
    override fun onQueryTextChange(newText: String?): Boolean {
        Log.d(Constants.TAG, "MainActivity - onQueryTextSubmit() called / newText: ${newText}")
        val userInputText = newText ?: ""//입력된값이 없으면 ""을 넣겠다
        if (userInputText.count() == 12) {
            Toast.makeText(this, "12자까지 검색가능 합니다.", Toast.LENGTH_SHORT).show()
            Log.d(Constants.TAG, "12자까지 검색가능 합니다.${userInputText.count()}")
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        binding.apply {
            tabsLayout.visibility = View.VISIBLE
            viewPager.isUserInputEnabled = true
        }
        when (item?.itemId) {
            R.id.menu_naver -> {
                changePlatform(PLATFORM.NAVER)
                mainViewModel.getWebtoonData(platform = platform, startToDayWeeks[selectTab]) //선택된 요일의 웹툰 데이터 검색하기
            }
            R.id.menu_kakao -> {
                changePlatform(PLATFORM.KAKAO)
                mainViewModel.getWebtoonData(platform = platform, startToDayWeeks[selectTab]) //선택된 요일의 웹툰 데이터 검색하기
            }
            R.id.menu_kakao_page -> {
                changePlatform(PLATFORM.KAKAOPAGE)
                mainViewModel.getWebtoonData(platform = platform, startToDayWeeks[selectTab]) //선택된 요일의 웹툰 데이터 검색하기
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun changePlatform(platform: PLATFORM) {
        Log.d(
            Constants.TAG,
            "MainActivity - onOptionsItemSelected()-changePlatform() called/ ${platform.string} 선택 "
        )
        Toast.makeText(this, "${platform.string} 선택", Toast.LENGTH_SHORT).show()
        this.platform = platform
    }

    private fun setObserve() {//
        //Log.d(Constants.TAG,"!!MainActivity - setObserve() called")
        mainViewModel.apply {

            platform.observe(this@MainActivity, androidx.lifecycle.Observer {
                setBackgroundColorByPlatform(it)
            })
            uiState.asLiveData().observe(this@MainActivity, androidx.lifecycle.Observer
            {
                when (it) {
                    is UiState.Loading -> {
                        Log.d(Constants.TAG, "UiState.Loading")
                        //탭 생성할 요일 리스트 생성
                        //getTDayWeek()//오늘요일구하기
                        makeWeekList(dayWeek.dayNum)//오늘요일을 시작으로 요일리스트 생성
                        setActionBar()// 액션바의 오버라이드 함수로 (네이버,카카오)플랫폼 선택하는 액션바 설정
                        setTabsListener()//요일 선택하는 탭바의 클릭 이벤트리스너//통신
                        setTabsFragment()//프래그먼트 생성하고 그수만큼 탭바 생성
                        setChangeLodingImageListener()
                    }
                    is UiState.Empty -> {
                        Log.d(Constants.TAG, "UiState.Empty")
                        startChangeLoadingImage()
                    }
                    is UiState.Success -> {
                        Log.d(Constants.TAG, "UiState.Success")
                        stopChangeLoadingImage()
                        pagerAdapter.getFragment(selectTab).setData(webtoonDataList.value)
                    }
                    is UiState.Error -> {
                        Log.d(Constants.TAG, "UiState.Error")
                        getWebtoonData(platform = this@MainActivity.platform, startToDayWeeks[selectTab]) //선택된 요일의 웹툰 데이터 검색하기
                    }
                }
            })
        }
    }

    private fun setBackgroundColorByPlatform(platform: PLATFORM) {
        when (platform) {
            PLATFORM.CUSTOM_SEARCH -> {
                binding.apply {
                    mainActivityLayout.setBackgroundColor(getColor(R.color.search_background))
                    topAppBar.apply {
                        collapseActionView()//탑바에 액션뷰가 닫힘//키보드 사라짐
                        setBackgroundColor(getColor(R.color.search_app_bar_background))
                    }
                    tabsLayout.visibility = View.GONE
                    viewPager.isUserInputEnabled = false
                }
            }
            PLATFORM.NAVER -> {
                binding.apply {
                    mainActivityLayout.setBackgroundColor(getColor(R.color.naver_background))
                    topAppBar.setBackgroundColor(getColor(R.color.naver_background))
                }
            }
            PLATFORM.KAKAO -> {
                binding.apply {
                    mainActivityLayout.setBackgroundColor(getColor(R.color.kakao_background))
                    topAppBar.setBackgroundColor(getColor(R.color.kakao_background))
                }
            }
            PLATFORM.KAKAOPAGE -> {
                binding.apply {
                    mainActivityLayout.setBackgroundColor(getColor(R.color.kakao_page_background))
                    topAppBar.setBackgroundColor(getColor(R.color.kakao_page_background))
                }
            }
        }
    }

    private fun setChangeLodingImageListener() {
        binding.changeLoadingImage.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
                //TODO("Not yet implemented")
                Log.d(Constants.TAG, "애니매이션 시작")
            }

            override fun onAnimationEnd(animation: Animator?) {
                //TODO("Not yet implemented")
                Log.d(Constants.TAG, "애니매이션 끝")
            }

            override fun onAnimationCancel(animation: Animator?) {
                // TODO("Not yet implemented")
                Log.d(Constants.TAG, "애니매이션 취소")
                onAnimationEnd(animation)
            }

            override fun onAnimationRepeat(animation: Animator?) {
                //TODO("Not yet implemented")
                Log.d(Constants.TAG, "애니매이션 반복")
            }
        })
    }

    private fun startChangeLoadingImage(){
        binding.apply {
            changeLoadingImage.apply {
                visibility = View.VISIBLE
                playAnimation()
            }//애니매이션 시작
        }
    }

    private fun stopChangeLoadingImage(){
        binding.apply {
            changeLoadingImage.apply {
                cancelAnimation()//애니매이션 취소
                visibility=View.GONE
            }
        }
    }
}