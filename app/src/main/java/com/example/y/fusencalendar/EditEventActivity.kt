package com.example.y.fusencalendar

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_edit_event.*
import kotlinx.android.synthetic.main.activity_edit_fusen.*
import kotlinx.android.synthetic.main.activity_edit_fusen.backButton
import kotlinx.android.synthetic.main.activity_edit_fusen.deleteButton
import kotlinx.android.synthetic.main.activity_edit_fusen.memoEdit
import kotlinx.android.synthetic.main.activity_edit_fusen.titleEdit

class EditEventActivity : AppCompatActivity() {
    private lateinit var realm: Realm

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_event)

        realm = Realm.getDefaultInstance()

        val eventId = intent.getIntExtra("eventId", 0)

        if(eventId > 0){
            val event = realm.where<Event>()
                .equalTo("id",eventId).findFirst()
            titleEdit.setText(event?.title.toString())
            memoEdit.setText(event?.memo.toString())
            Toast.makeText(applicationContext, "色は${event?.colorId.toString()}番です。", Toast.LENGTH_SHORT).show()
            dateText.setText(event?.year.toString() + "年" + event?.month.toString() + "月" + event?.date.toString() + "日")
            timeText.setText(event?.hour.toString() + "時" + event?.minute.toString() + "分")
            deleteButton.visibility = View.VISIBLE
        } else{
            deleteButton.visibility = View.INVISIBLE
        }

        backButton.setOnClickListener{ v: View? ->
            var title: String = "タイトル"
            var memo: String = "メモ"
            var colorId: Int = 0
            var year: Int = 0
            var month: Int = 0
            var date: Int = 0
            var hour: Int = 0
            var minute: Int = 0

            if(!titleEdit.text.isNullOrEmpty()){
                title = titleEdit.text.toString()
            }
            if(!memoEdit.text.isNullOrEmpty()){
                memo = memoEdit.text.toString()
            }

            when(eventId){
                0 -> {
                    realm.executeTransaction {
                        val maxId = realm.where<Event>().max("id")
                        val nextId = (maxId?.toInt() ?: 0) + 1
                        val Event = realm.createObject<Event>(nextId)
                        Event.title = title
                        Event.memo = memo
                        Event.colorId = colorId
                        Event.year = year
                        Event.month = month
                        Event.date = date
                        Event.hour = hour
                        Event.minute = minute
                    }
                }
                else -> {
                    realm.executeTransaction {
                        val Event = realm.where<Event>()
                            .equalTo("id",eventId).findFirst()
                        Event?.title = title
                        Event?.memo = memo
                        Event?.colorId = colorId
                        Event?.year = year
                        Event?.month = month
                        Event?.date = date
                        Event?.hour = hour
                        Event?.minute = minute
                    }
                }
            }
            Toast.makeText(applicationContext, "保存しました", Toast.LENGTH_SHORT).show()
            finish()
        }
        deleteButton.setOnClickListener(){
            realm.executeTransaction{
                val event = realm.where<Event>()
                    .equalTo("id",eventId)
                    ?.findFirst()
                    ?.deleteFromRealm()
            }
            Toast.makeText(applicationContext, "削除しました", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}