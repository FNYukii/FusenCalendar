package com.example.y.fusencalendar

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.one_card.view.*

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    //変数宣言
    private var cardBackground: ConstraintLayout? = null
    private var cardTitleText: TextView? = null
    private var cardMemoText: TextView? = null
    private var cardTimeText: TextView? = null

    //ViewHolderのプロパティとone_eventのViewを対応させる。
    init {
        cardBackground = itemView.cardBackground
        cardTitleText = itemView.cardTitleText
        cardMemoText = itemView.cardMemoText
        cardTimeText = itemView.cardTimeText
    }

}