package com.example.webtoonhub.fragment

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.webtoonhub.utils.Constants

class WeekFragmentStateAdapter(fragmentManager: FragmentManager,lifecycle: Lifecycle,val startToDayWeeks:ArrayList<String>): FragmentStateAdapter(fragmentManager,lifecycle) {

    var fragments : ArrayList<mainFragment> = ArrayList()

    override fun getItemCount(): Int {
        //Log.d(Constants.TAG,"startToDayWeeks크기 : ${startToDayWeeks.size}")
        return startToDayWeeks.size
    }

    override fun createFragment(position: Int): Fragment {
        Log.d(Constants.TAG,"WeekFragmentStateAdapter - createFragment() called / position = $position")
        val fragment=mainFragment()
        fragments.add(fragment)
        Log.d(Constants.TAG,"WeekFragmentStateAdapter - createFragment() called / fragments.size = ${fragments.size}")
        return fragment
    }

    fun getFragment(position: Int):mainFragment{
        return fragments[position]
    }

    fun removeFragment() {
        fragments.removeLast()
        notifyItemRemoved(fragments.size)
    }

}
