package com.example.y.fusencalendar

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import kotlinx.android.synthetic.main.one_event.view.*
import kotlinx.android.synthetic.main.one_event.view.fusenBackground
import kotlinx.android.synthetic.main.one_event.view.fusenMemoText
import kotlinx.android.synthetic.main.one_event.view.fusenTitleText

class EventRecyclerViewAdapter(
    private val collection: OrderedRealmCollection<Event>
): RealmRecyclerViewAdapter<Event, EventRecyclerViewAdapter.EventViewHolder>(collection, true) {


    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val eventBackground: ConstraintLayout = itemView.fusenBackground
        val eventTitleText: TextView = itemView.fusenTitleText
        val eventMemoText: TextView = itemView.fusenMemoText
        val eventDateText: TextView = itemView.eventDateText
    }


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_event, parent, false)
        return EventViewHolder(view)
    }


    override fun getItemCount(): Int {
        return collection.size
    }


    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {

        //レコードを取得
        val event = collection[position]

        //タイトルとメモの文字列をセット
        holder.eventTitleText.text = event?.title.toString()
        holder.eventMemoText.text = event?.memo.toString()

        //時刻の文字列をセット
        val hourString = event?.hour.toString().padStart(2, '0')
        val minuteString = event?.minute.toString().padStart(2, '0')
        val timeString = "$hourString:$minuteString"
        holder.eventDateText.text = timeString

        //colorIdに応じて、カードの色を設定
        when(event.colorId){
            0 -> {
                holder.eventBackground.setBackgroundResource(R.drawable.background_card_blue)
            }
            1 -> {
                holder.eventBackground.setBackgroundResource(R.drawable.background_card_green)
            }
            2 -> {
                holder.eventBackground.setBackgroundResource(R.drawable.background_card_orange)
            }
            3 -> {
                holder.eventBackground.setBackgroundResource(R.drawable.background_card_red)
            }
            4 -> {
                holder.eventBackground.setBackgroundResource(R.drawable.background_card_purple)
            }
        }

        //クリックされたら編集画面へ遷移
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, EditEventActivity::class.java)
            intent.putExtra("eventId", event.id)
            it.context.startActivity(intent)
        }
    }

}