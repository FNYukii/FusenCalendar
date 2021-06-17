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
        val event = collection.get(position)

        holder.itemView.eventTitleText.text = event?.title.toString()
        holder.itemView.eventMemoText.text = event?.memo.toString()
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, EditEventActivity::class.java)
            intent.putExtra("eventId", event.id)
            it.context.startActivity(intent)
        }
    }

}