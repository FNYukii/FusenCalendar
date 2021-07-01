package com.example.y.fusencalendar

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import kotlinx.android.synthetic.main.one_event.view.*
import kotlinx.android.synthetic.main.one_event.view.eventBackground
import kotlinx.android.synthetic.main.one_event.view.eventMemoText
import kotlinx.android.synthetic.main.one_event.view.eventTitleText

class EventRecyclerViewAdapter(
    private val collection: OrderedRealmCollection<Event>
): RealmRecyclerViewAdapter<Event, EventRecyclerViewAdapter.EventViewHolder>(collection, true) {


    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val eventBackground: ConstraintLayout = itemView.eventBackground
        val eventTitleText: TextView = itemView.eventTitleText
        val eventMemoText: TextView = itemView.eventMemoText
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
                holder.eventBackground.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.green))
            }
            1 -> {
                holder.eventBackground.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.blue))
            }
            2 -> {
                holder.eventBackground.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.orange))
            }
            3 -> {
                holder.eventBackground.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.red))
            }
            4 -> {
                holder.eventBackground.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.purple))
            }
        }


        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, EditEventActivity::class.java)
            intent.putExtra("eventId", event.id)
            it.context.startActivity(intent)
        }
    }

}