package com.example.y.fusencalendar

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_calendar_page.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class CalendarPageFragment : Fragment() {


    //ページ数
    private val pageSize = Int.MAX_VALUE

    //一か月分の日付情報
    private lateinit var days: Array<LocalDate?>

    //CalendarRecyclerViewのセルの高さ
    private var cellheight: Int = 100

    private var isPasteFusen: Boolean = false


    private val onDateTimeSelectedListener: (View, Int, LocalDate)->Unit = { _, _, date ->
        (requireActivity() as? ShowDateTimePickerListener)?.showDatePicker(date)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calendar_page, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //SharedPreferencesのオブジェクトを取得
        val sharedPref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

        //SharedPreferencesに保存していたcellの高さを取得
        cellheight = sharedPref.getInt("cellHeight", 0)

        //ホストFragmentもしくはホストActivityからページ番号を受け取って、当月との差を計算
        val position = arguments?.getInt("position") ?: 0
        val offsetMonth: Int = 0 - (pageSize / 2 - position)

        //ホストFragmentもしくはホストActivityからisPasteFusenの真偽を受け取る
        isPasteFusen = arguments?.getBoolean("isPasteFusen")!!

        //一ヵ月分の日付情報をDayRecyclerViewAdapterへ渡して、それをcalendarRecyclerViewのAdapterとする
        days = createDays(offsetMonth)
        calendarRecyclerView.adapter = CalendarRecyclerViewAdapter(days, cellheight, isPasteFusen, onDateTimeSelectedListener)
        calendarRecyclerView.layoutManager = GridLayoutManager(this.context, 7)

        //labelTextに年月のテキストをセット
        labelText.text = SimpleDateFormat("yyyy年 M月",Locale.JAPANESE).format(Date().apply { offset(month = offsetMonth) })

        //calendarRecyclerViewのレイアウトが完了後、Viewの高さを取得してRecyclerViewAdapterへ渡す
        calendarRecyclerView.afterMeasured {
            if(calendarRecyclerView != null){
                cellheight = calendarRecyclerView.height / 6
                calendarRecyclerView.adapter = CalendarRecyclerViewAdapter(days, cellheight, isPasteFusen, onDateTimeSelectedListener)
            }
            //セルの高さをSharedPreferencesに保存
            sharedPref.edit().putInt("cellHeight", cellheight).apply()
        }

    }


    override fun onStart() {
        super.onStart()
        calendarRecyclerView.adapter = CalendarRecyclerViewAdapter(days, cellheight, isPasteFusen, onDateTimeSelectedListener)
    }


    private fun Date.offset(month: Int = 0) {
        time = Calendar.getInstance().apply {
            add(Calendar.MONTH, month)
        }.timeInMillis
    }


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


    //Viewのレイアウト完了時に処理を行うための拡張関数
    private inline fun <T : View> T.afterMeasured(crossinline f: T.() -> Unit) {
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (measuredWidth > 0 && measuredHeight > 0) {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                    f()
                }
            }
        })
    }


}