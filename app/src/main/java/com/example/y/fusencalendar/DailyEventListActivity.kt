package com.example.y.fusencalendar

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_daily_event_list.*
import kotlinx.android.synthetic.main.fragment_fusen_list.recyclerView

class DailyEventListActivity : AppCompatActivity() {


    //Realmのインスタンスを取得
    var realm: Realm = Realm.getDefaultInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_event_list)

        //CalendarRecyclerViewAdapterから日付情報を受け取る
        val dateNumber: Int = intent.getIntExtra("dateNumber", 0)
        val dateString: String? = intent.getStringExtra("dateString")

        //labelTextに、表示日をセット 例: "2021年 6月 16日"
        labelText.text = dateString

        //backButtonを押すとアクティビティ終了
        backButton.setOnClickListener {
            finish()
        }

        //floatingButtonを押すと、新規イベント作成のためにEditEventActivityへ遷移する
        floatingButton.setOnClickListener {
            val intent = Intent(this, EditEventActivity::class.java)
            startActivity(intent)
        }

        //Todo: 日付が一致するレコードを検索
        val realmResults = realm.where<Event>()
            .findAll()

        //recyclerViewを表示
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = EventRecyclerViewAdapter(realmResults)
    }


}