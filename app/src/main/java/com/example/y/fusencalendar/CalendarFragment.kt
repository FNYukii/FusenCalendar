package com.example.y.fusencalendar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


class CalendarFragment : Fragment() {


    //現在カレンダーに表示している月と現在の月との差を表す変数
    private var offsetMonth = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //titleTextに表示月をセット
        monthText.text = SimpleDateFormat("yyyy年 M月",Locale.JAPANESE).format(Date().apply { offset(month = offsetMonth) })

        //前の月へ移動
        prevButton.setOnClickListener {
            //表示月を前の月にする
            offsetMonth--
            //前の月のデータをcalendarRecyclerViewのAdapterへ流す
            calendarRecyclerView.adapter = CalendarRecyclerViewAdapter(createDays(offsetMonth))
            //titleTextのテキストを、新しい表示月に更新
            monthText.text = SimpleDateFormat("yyyy年 M月",Locale.JAPANESE).format(Date().apply { offset(month = offsetMonth) })
        }

        //次の月へ移動
        nextButton.setOnClickListener {
            //表示月を次の月にする
            offsetMonth++
            //次の月のデータをcalendarRecyclerViewのAdapterへ流す
            calendarRecyclerView.adapter = CalendarRecyclerViewAdapter(createDays(offsetMonth))
            //titleTextのテキストを、新しい表示月に更新
            monthText.text = SimpleDateFormat("yyyy年 M月",Locale.JAPANESE).format(Date().apply { offset(month = offsetMonth) })
        }

        //calendarRecyclerViewにデータをセット
        calendarRecyclerView.adapter = CalendarRecyclerViewAdapter(createDays(offsetMonth))
        //calendarRecyclerViewのレイアウトを、横7列のグリッド形式に設定
        calendarRecyclerView.layoutManager = GridLayoutManager(this.context, 7)

    }


    //一カ月分のLocalDate?型の配列daysを生成するcreateDaysメソッド
    //例:[null, null, 2021-06-01, 2021-06-02, 2021-06-03,,,,]
    //daysの要素数は週数が5週の月なら35個、6週の月なら42個
    private fun createDays(offsetMonth: Int):Array<LocalDate?>{

        //配列や変数
        val days: MutableList<LocalDate?> = arrayListOf()
        var day: LocalDate = LocalDate.now()
        day = day.plusMonths(offsetMonth.toLong())

        //当月の日数と、一日の曜日を取得
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, offsetMonth)
        val dayOfMonth = calendar.getActualMaximum(Calendar.DATE)
        calendar.set(Calendar.DATE, 1)
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

        //当月の1日までをnullで埋める
        for (i in 1 until dayOfWeek){
            days.add(null)
        }

        //1日から月末日まで数字を埋める
        for (i in 1..dayOfMonth){
            days.add(LocalDate.of(day.year, day.month, i))
        }

        //余った領域はnullで埋める
        if(days.size > 35){
            val filledSize = (42) - days.size
            for (i in 0 until filledSize){
                days.add(null)
            }
        }else{
            val filledSize = (35) - days.size
            for (i in 0 until filledSize){
                days.add(null)
            }
        }

        //配列daysをreturn
        return days.toTypedArray()
    }


    private fun Date.offset(month: Int = 0) {
        time = Calendar.getInstance().apply {
            add(Calendar.MONTH, month)
        }.timeInMillis
    }


}