package com.example.y.fusencalendar

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.preference.PreferenceManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_paste_fusen_to_calendar.*
import java.time.LocalDate



class PasteFusenToCalendarActivity :
    AppCompatActivity(),
    ShowDateTimePickerListener,
    TimePickerDialogFragment.OnSelectedTimeListener {

    //calendarPagerのページ数
    private val pageSize = Int.MAX_VALUE //約20億

    //ふせんをイベントとして登録する日時
    private var selectedYear = 0
    private var selectedMonth = 0
    private var selectedDay = 0
    private var selectedHour = 0
    private var selectedMinute = 0




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paste_fusen_to_calendar)

        //calendarPager02の処理
        calendarPager02.adapter = CustomPagerAdapter(this)
        calendarPager02.setCurrentItem(pageSize / 2,false)
        calendarPager02.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        //backButton03を押すとfinish()
        backButton03.setOnClickListener {
            finish()
        }
    }


    //CalendarPager用のAdapter
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



    override fun showDatePicker(date: LocalDate) {

        //選択された日付を取得
        selectedYear = date.year
        selectedMonth = date.monthValue
        selectedDay = date.dayOfMonth

        //timePickerを表示
        val timePickerDialogFragment = TimePickerDialogFragment()
        timePickerDialogFragment.show(supportFragmentManager, "dialog")
    }


    //timePickerで時刻が選択されたら…
    override fun selectedTime(hour: Int, minute: Int) {

        //選択された時刻を取得
        selectedHour = hour
        selectedMinute = minute

        Log.d("hello", "selected $selectedYear-$selectedMonth-$selectedDay $selectedHour:$selectedMinute")
    }


}