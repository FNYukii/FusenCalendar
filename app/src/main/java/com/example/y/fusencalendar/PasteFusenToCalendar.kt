package com.example.y.fusencalendar

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_paste_fusen_to_calendar.*
import kotlinx.android.synthetic.main.fragment_calendar.*

class PasteFusenToCalendar : AppCompatActivity() {

    //calendarPagerのページ数
    private val pageSize = Int.MAX_VALUE //約20億



    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paste_fusen_to_calendar)

        //calendarPagerのAdapterとして、CustomPagerAdapterをセット
        calendarPager02.adapter = CustomPagerAdapter(this)
        //calendarPagerの開始ページは、真ん中のページ
        calendarPager02.setCurrentItem(pageSize / 2,false)
        //calendarPagerのスクロール方向を指定
        calendarPager02.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        //backButton03を押すとfinish()
        backButton03.setOnClickListener {
            finish()
        }

        //Todo: calendarPager02にOnTouchListenerを実装する


    }

    private inner class CustomPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int {
            return pageSize
        }
        override fun createFragment(position: Int): Fragment {
            val fragment = CalendarPageFragment()
            val bundle = Bundle()
            bundle.putInt("position", position)
            bundle.putBoolean("isPasteFusen", true)
            fragment.arguments = bundle
            return fragment
        }
    }


}