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

                //日付情報をintentに付与
                intent.putExtra("year", days[position]?.year)
                intent.putExtra("month", days[position]?.monthValue)
                intent.putExtra("day", days[position]?.dayOfMonth)

                //intent実行
                it.context.startActivity(intent)
            }
        }

    }



}