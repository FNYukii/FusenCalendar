package com.example.y.fusencalendar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_daily_event_list.*
import kotlinx.android.synthetic.main.fragment_fusen_list.recyclerView

class DailyEventListActivity : AppCompatActivity() {

    //RecyclerView用のインスタンス宣言
    private lateinit var adapter: FusenRecyclerViewAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_event_list)

        backButton.setOnClickListener {
            finish()
        }

    }

    override fun onStart() {
        super.onStart()

        //RecyclerViewを表示
        layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager
        adapter = FusenRecyclerViewAdapter()
        recyclerView.adapter = this.adapter

    }
}