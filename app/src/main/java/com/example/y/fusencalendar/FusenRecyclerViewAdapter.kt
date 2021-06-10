package com.example.y.fusencalendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import kotlinx.android.synthetic.main.one_fusen.view.*

class FusenRecyclerViewAdapter(
    private val collection: OrderedRealmCollection<Fusen>
    ): RealmRecyclerViewAdapter<Fusen, FusenRecyclerViewAdapter.FusenViewHolder>(collection, true) {


    class FusenViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val fusenBackground: ConstraintLayout = itemView.fusenBackground
        val fusenTitleText: TextView = itemView.fusenTitleText
        val fusenMemoText: TextView = itemView.fusenMemoText
    }


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): FusenViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_fusen, parent, false)
        return FusenViewHolder(view)
    }


    override fun getItemCount(): Int {
        return collection.size
    }


    override fun onBindViewHolder(holder: FusenViewHolder, position: Int) {

        //レコードを取得
        val fusen = collection?.get(position)

        holder.itemView.fusenTitleText.text = fusen?.title.toString()
        holder.itemView.fusenMemoText.text = fusen?.memo.toString()
        holder.itemView.setOnClickListener {
            //Todo: Intentを作って、EditActivityへ遷移する
        }
    }

}