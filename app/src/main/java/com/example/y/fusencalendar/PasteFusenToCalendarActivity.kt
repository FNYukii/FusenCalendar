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

interface ShowDateTimePickerListener {
    fun showDatePicker(date: LocalDate)
}
class PasteFusenToCalendarActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener, ShowDateTimePickerListener {

    //calendarPagerのページ数
    private val pageSize = Int.MAX_VALUE //約20億

    //SharedPreferencesオブジェクト用の変数を宣言
    private lateinit var sharedPref: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paste_fusen_to_calendar)

        //SharedPreferencesのオブジェクト取得&changeListenerをセット
        sharedPref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        //sharedPref.registerOnSharedPreferenceChangeListener(this)

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


    //SharedPreferencesの値が更新されたら、
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
            val selectedYear = sharedPref.getInt("selectedYear", 0)
            val selectedMonth = sharedPref.getInt("selectedMonth", 0)
            val selectedDay = sharedPref.getInt("selectedDay", 0)
//            val frag = sharedPref.getBoolean("frag", false)
            Log.d("hello", "$selectedYear-$selectedMonth-$selectedDay")

       //

    }

    override fun showDatePicker(date: LocalDate) {
        val timePickerDialogFragment = TimePickerDialogFragment()
        timePickerDialogFragment.show(supportFragmentManager, "dialog")

    }

    override fun onResume() {
        super.onResume()
        sharedPref.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()

        sharedPref.unregisterOnSharedPreferenceChangeListener(this)
    }


}