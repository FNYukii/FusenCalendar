package com.example.y.fusencalendar

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_calendar.*

class CalendarFragment : Fragment() {


    //CalendarAdapterのオブジェクトを宣言
    private lateinit var mCalendarAdapter: CalendarAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //titleTextに表示月をセット
//        monthText.text = mCalendarAdapter.title

        //前の月へ移動
        prevButton.setOnClickListener {
            mCalendarAdapter.prevMonth()
//            monthText.text = mCalendarAdapter.title
        }

        //次の月へ移動
        nextButton.setOnClickListener {
            mCalendarAdapter.nextMonth()
//            monthText.text = mCalendarAdapter.title
        }

        dateButton.setOnClickListener {
            val intent = Intent(this.context, DailyEventListActivity::class.java)
            startActivity(intent)
        }



    }


    override fun onStart() {
        super.onStart()

        //カレンダーを表示
        mCalendarAdapter = CalendarAdapter(this.context)
        calendarGridView.adapter = mCalendarAdapter

    }


}