package com.example.y.fusencalendar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.fragment_calendar.*

class CalendarFragment : Fragment() {


    //calendarPagerのページ数
    private val pageSize = Int.MAX_VALUE //約20億


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //calendarPagerのAdapterとして、CustomPagerAdapterをセット
        calendarPager.adapter =CustomPagerAdapter(this.context as FragmentActivity)
        //calendarPagerの開始ページは、真ん中のページ
        calendarPager.setCurrentItem(pageSize / 2,false)
        //calendarPagerのスクロール方向を指定
        calendarPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }


    private inner class CustomPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int {
            return pageSize
        }
        override fun createFragment(position: Int): Fragment {
            val fragment = CalendarPageFragment()
            val bundle = Bundle()
            bundle.putInt("position", position)
            fragment.arguments = bundle
            return fragment
        }
    }


}