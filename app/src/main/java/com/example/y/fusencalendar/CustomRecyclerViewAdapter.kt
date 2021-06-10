package com.example.y.fusencalendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.one_card.view.*

class CustomRecyclerViewAdapter(): RecyclerView.Adapter<CustomRecyclerViewAdapter.CustomViewHolder>() {


    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val cardBackground: ConstraintLayout = itemView.cardBackground
        val cardTitleText: TextView = itemView.cardTitleText
        val cardMemoText: TextView = itemView.cardMemoText
        val cardTimeText: TextView = itemView.cardTimeText
    }


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_card, parent, false)
        return CustomViewHolder(view)
    }


    override fun getItemCount(): Int {
        return 9
    }


    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.itemView.cardTitleText.text = "ゲームする！"
        holder.itemView.cardMemoText.text = "Fallout76\nThe Forest\nGTA5"
        holder.itemView.cardTimeText.text = "12:00"
        holder.itemView.setOnClickListener {
            //Todo: Intentを作って、EditActivityへ遷移する
        }
    }

}