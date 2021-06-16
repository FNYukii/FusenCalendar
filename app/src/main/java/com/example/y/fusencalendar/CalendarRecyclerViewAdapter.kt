package com.example.y.fusencalendar

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.one_calendar_cell.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CalendarRecyclerViewAdapter(
    private val days: Array<LocalDate?>
): RecyclerView.Adapter<CalendarRecyclerViewAdapter.CustomViewHolder>() {


    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val dateText: TextView = itemView.dateText
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_calendar_cell, parent, false)
        return CustomViewHolder(view)
    }


    override fun getItemCount(): Int {
        return days.size //days.sizeは35or42
    }


    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        //TextViewに値をセット
        if(days[position] == null){
            holder.dateText.text = ""
        }else{
            val formatter = DateTimeFormatter.ofPattern("d")
            holder.dateText.text = days[position]?.format(formatter)
        }

        //もし本日なら、dateTextを赤色で表示
        if(days[position] == LocalDate.now()){
            holder.dateText.setTextColor(Color.RED)
        }

        //日付がnull以外のセルをタップすると、DailyEventListActivityへ遷移。
        holder.itemView.setOnClickListener {
            if(days[position] != null){
                val intent = Intent(it.context, DailyEventListActivity::class.java)

                //独自のFormatterを作成
                val numberFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
                val stringFormatter = DateTimeFormatter.ofPattern("yyyy年 M月 d日")

                //LocalDate型の日付情報をInt型に変換。 例:20210615 レコード検索に使う。
                val dateNumber: Int = days[position]?.format(numberFormatter)?.toInt() ?: 0

                //LocalDate型の日付情報をString型に変換。 例:"2021年 6月 15日" labelTextに使う。
                val dateString: String = days[position]?.format(stringFormatter).toString()

                //日付情報を付与して、intentを実行
                intent.putExtra("dateNumber", dateNumber)
                intent.putExtra("dateString", dateString)
                it.context.startActivity(intent)
            }
        }

    }



}