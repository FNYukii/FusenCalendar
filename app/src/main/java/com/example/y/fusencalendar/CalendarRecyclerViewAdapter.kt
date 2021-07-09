package com.example.y.fusencalendar

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.one_calendar_cell.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter



class CalendarRecyclerViewAdapter(
    private val days: Array<LocalDate?>,
    private val cellHeight: Int,
    private val isPasteFusen: Boolean,
    var onCalendarCellSelectedListener: ((view: View, position: Int, date: LocalDate)->Unit)? = null
): RecyclerView.Adapter<CalendarRecyclerViewAdapter.CustomViewHolder>() {


    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val dateText: TextView = itemView.dateText
        val cardImage01: View = itemView.cardImage01
        val cardImage02: View = itemView.cardImage02
        val cardImage03: View = itemView.cardImage03
        val cardImage04: View = itemView.cardImage04
        val calendarCellParentLayout: ConstraintLayout = itemView.calendarCellParentLayout
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_calendar_cell, parent, false)
        return CustomViewHolder(view)
    }


    override fun getItemCount(): Int {
        return days.size //days.sizeは35or42
    }


    @SuppressLint("CommitPrefEdits")
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        //変数を用意
        val year = days[position]?.year ?: -1
        val month = days[position]?.monthValue ?: -1
        val day = days[position]?.dayOfMonth ?: -1
        val realm = Realm.getDefaultInstance()

        //セルの高さを決定
        val params: ConstraintLayout.LayoutParams = holder.calendarCellParentLayout.layoutParams as ConstraintLayout.LayoutParams
        params.height = cellHeight
        holder.calendarCellParentLayout.layoutParams = params


        //TextViewに値をセット
        if(days[position] == null){
            holder.dateText.text = ""
        }else{
            val formatter = DateTimeFormatter.ofPattern("d")
            holder.dateText.text = days[position]?.format(formatter)
        }

        //もし当日が本日なら、dateTextを赤色で表示
        if(days[position] == LocalDate.now()){
            holder.dateText.setTextColor(Color.RED)
        }

        //当日に登録されている全イベントを取得
        val realmResults = realm.where<Event>()
            .equalTo("year", year)
            .and()
            .equalTo("month", month)
            .and()
            .equalTo("date", day)
            .findAll()
            .sort("hour", Sort.ASCENDING, "minute", Sort.ASCENDING)

        //当日登録されているイベント数が1以上なら、cardImage01を表示
        if(realmResults.size >= 1){
            when(realmResults[0]?.colorId){
                0 -> {
                    holder.cardImage01.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.blue))
                }
                1 -> {
                    holder.cardImage01.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.green))
                }
                2 -> {
                    holder.cardImage01.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.yellow))
                }
                3 -> {
                    holder.cardImage01.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.red))
                }
                4 -> {
                    holder.cardImage01.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.purple))
                }
            }
        }

        //当日登録されているイベント数が2以上なら、cardImage02を表示
        if(realmResults.size >= 2){
            when(realmResults[1]?.colorId){
                0 -> {
                    holder.cardImage02.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.blue))
                }
                1 -> {
                    holder.cardImage02.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.green))
                }
                2 -> {
                    holder.cardImage02.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.yellow))
                }
                3 -> {
                    holder.cardImage02.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.red))
                }
                4 -> {
                    holder.cardImage02.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.purple))
                }
            }
        }

        //当日登録されているイベント数が3以上なら、cardImage03を表示
        if(realmResults.size >= 3){
            when(realmResults[2]?.colorId){
                0 -> {
                    holder.cardImage03.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.blue))
                }
                1 -> {
                    holder.cardImage03.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.green))
                }
                2 -> {
                    holder.cardImage03.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.yellow))
                }
                3 -> {
                    holder.cardImage03.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.red))
                }
                4 -> {
                    holder.cardImage03.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.purple))
                }
            }
        }

        //当日登録されているイベント数が4以上なら、cardImage04を表示
        if(realmResults.size >= 4){
            when(realmResults[3]?.colorId){
                0 -> {
                    holder.cardImage04.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.blue))
                }
                1 -> {
                    holder.cardImage04.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.green))
                }
                2 -> {
                    holder.cardImage04.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.yellow))
                }
                3 -> {
                    holder.cardImage04.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.red))
                }
                4 -> {
                    holder.cardImage04.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.purple))
                }
            }
        }


        //日付がnull以外のセルをタップすると…
        holder.itemView.setOnClickListener {

            //ホストがCalendarFragmentならDailyEventListActivityへ遷移。
            if(days[position] != null && !isPasteFusen){
                val intent = Intent(it.context, DailyEventListActivity::class.java)

                //日付情報をintentに付与
                intent.putExtra("year", year)
                intent.putExtra("month", month)
                intent.putExtra("day", day)

                //intent実行
                it.context.startActivity(intent)
            }


            //ホストがPasteFusenToCalendarActivityなら、
            if(days[position] != null && isPasteFusen){

                days[position]?.let { date ->
                    onCalendarCellSelectedListener?.invoke(holder.itemView, position, date)
                }

            }


        }

    }



}