package com.example.y.fusencalendar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_daily_event_list.*
import kotlinx.android.synthetic.main.fragment_fusen_list.recyclerView

class DailyEventListActivity : AppCompatActivity() {

    //RecyclerView用のインスタンス宣言
    private lateinit var adapter: EventRecyclerViewAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager

    //Realmのインスタンスを取得
    var realm: Realm = Realm.getDefaultInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_event_list)

        //CalendarAdapterから日付情報を受け取る
        val month = intent.getIntExtra("month", 0)
        val day = intent.getIntExtra("day", 0)
        val titleString = month.toString() + "月" + day + "日のイベント"
        titleText.text = titleString

        backButton.setOnClickListener {
            finish()
        }

    }

    override fun onStart() {
        super.onStart()

//        RecyclerViewを表示
        val realmResults = realm.where<Event>()
            .findAll()
        layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager
        adapter = EventRecyclerViewAdapter(realmResults)
        recyclerView.adapter = this.adapter

    }
}