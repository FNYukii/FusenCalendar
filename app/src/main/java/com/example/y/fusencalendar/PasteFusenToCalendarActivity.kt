package com.example.y.fusencalendar

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.preference.PreferenceManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_paste_fusen_to_calendar.*
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.time.LocalDate



class PasteFusenToCalendarActivity :
    AppCompatActivity(),
    ShowDateTimePickerListener,
    TimePickerDialogFragment.OnSelectedTimeListener {

    //calendarPagerのページ数
    private val pageSize = Int.MAX_VALUE //約20億

    //イベントとして登録したいふせんの内容
    private var fusenId = 0
    private var newFusenTitle = ""
    private var newFusenMemo = ""
    private var title = ""
    private var memo = ""
    private var colorId = 0

    //ふせんをイベントとして登録する日時
    private var selectedYear = 0
    private var selectedMonth = 0
    private var selectedDay = 0
    private var selectedHour = 0
    private var selectedMinute = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paste_fusen_to_calendar)

        //貼りたいふせんの内容を取得する
        fusenId = intent.getIntExtra("fusenId", 0)
        newFusenTitle = intent.getStringExtra("newFusenTitle")!!
        newFusenMemo = intent.getStringExtra("newFusenMemo")!!
        Log.d("hello", "fusenId: $fusenId, newFusenTitle: $newFusenTitle, newFusenMemo: $newFusenMemo")

        //ふせんがデータベースに登録済みなら、そのレコードを検索する
        if (fusenId != 0) {
            val realm = Realm.getDefaultInstance()
            val realmResult = realm.where<Fusen>()
                .equalTo("id", fusenId)
                .findFirst()
            title = realmResult?.title!!
            memo = realmResult.memo
            colorId = realmResult.colorId
        }


        //calendarPager02の処理
        calendarPager02.adapter = CustomPagerAdapter(this)
        calendarPager02.setCurrentItem(pageSize / 2,false)


        //SharedPreferenceに保存されているカレンダーのスライド方向の設定値を取得
        val pref = getSharedPreferences("Setting", Context.MODE_PRIVATE)
        val slideDirection: Boolean = pref?.getBoolean("SLIDE_DIRECTION", false)!!

        //calendarPagerのスクロール方向を指定
        if(slideDirection){
            calendarPager02.orientation = ViewPager2.ORIENTATION_VERTICAL
        }else{
            calendarPager02.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }

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

        //選択された日時に、ふせんをイベントとして登録する
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            val maxId = realm.where<Event>().max("id")
            val nextId = (maxId?.toInt() ?: 0) + 1
            val event = realm.createObject<Event>(nextId)
            event.title = title
            event.memo = memo
            event.colorId = colorId
            event.year = selectedYear
            event.month = selectedMonth
            event.date = selectedDay
            event.hour = selectedHour
            event.minute = selectedMinute
        }

        finish()

    }


}