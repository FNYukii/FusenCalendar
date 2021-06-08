package com.example.y.fusencalendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.one_card.view.*

class CustomRecyclerViewAdapter(): RecyclerView.Adapter<ViewHolder>() {

    //Todo: Realmを使用するため、rResultsを宣言
//    private val rResults: RealmResults<Fusen> = realmResults


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_card, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        //Todo: Realmから取得したレコード数を返す。
//        return rResults.size
        return 9
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.cardTitleText.text = "ゲームする！"
        holder.itemView.cardMemoText.text = "Fallout76\nThe Forest\nGTA5"
        holder.itemView.cardTimeText.text = "12:00"
        holder.itemView.setOnClickListener {
            //Todo: Intentを作って、EditActivityへ遷移する
        }
    }

}