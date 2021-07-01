package com.example.y.fusencalendar

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_edit_event.*

class EditEventActivity :
    AppCompatActivity(),
    ColorDialogFragment.DialogListener,
    DatePickerDialogFragment.OnSelectedDateListener,
    TimePickerDialogFragment.OnSelectedTimeListener {

    private lateinit var realm: Realm
    private var eventId = 0
    var title: String = ""
    var memo: String = ""
    var colorId: Int = 0
    var year: Int = 0
    var month: Int = 0
    var date: Int = 0
    var hour: Int = 0
    var minute: Int = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_event)

        realm = Realm.getDefaultInstance()
        eventId = intent.getIntExtra("eventId", 0)
        if(eventId > 0){
            val event = realm.where<Event>()
                .equalTo("id",eventId).findFirst()

            titleEdit.setText(event?.title.toString())
            memoEdit.setText(event?.memo.toString())
            dateText.setText(event?.year.toString() + "年" + event?.month.toString() + "月" + event?.date.toString() + "日")
            timeText.setText(event?.hour.toString() + "時" + event?.minute.toString() + "分")
            year = event?.year!!
            month = event.month
            date = event.date
            hour = event.hour
            minute = event.minute


            deleteButton.visibility = View.VISIBLE
        } else{
            deleteButton.visibility = View.INVISIBLE
        }

        backButton.setOnClickListener{ v: View? ->
            save()
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

        colorButton.setOnClickListener{ v: View? ->
            val colorDialog = ColorDialogFragment()
            colorDialog.show(supportFragmentManager, "dialog")
        }

        dateText.setOnClickListener {
            val datePickerDialog = DatePickerDialogFragment()
            datePickerDialog.show(supportFragmentManager, "dialog")
        }

        timeText.setOnClickListener {
            val timePickerDialog = TimePickerDialogFragment()
            timePickerDialog.show(supportFragmentManager, "dialog")
        }




    }

    override fun onBackPressed() {
        super.onBackPressed()
        save()
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    fun save(){

        if(titleEdit.text.isNotEmpty() || memoEdit.text.isNotEmpty()) {

            title = titleEdit.text.toString()
            memo = memoEdit.text.toString()

            when (eventId) {
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
                            .equalTo("id", eventId).findFirst()
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
        }
        finish()
    }

    override fun onDialogColorIdReceive(dialog: DialogFragment, colorId: Int) {
        this.colorId = colorId
    }

    override fun selectedDate(year: Int, month: Int, date: Int) {
        Log.d("hello", "year: $year month: $month date: $date")
        this.year = year
        this.month = month
        this.date = date
        val dateString = year.toString() + "年" + month.toString() + "月" + date.toString() + "日"
        dateText.text = dateString
    }

    override fun selectedTime(hour: Int, minute: Int) {
        this.hour = hour
        this.minute = minute
        val timeString = hour.toString() + "時" + minute.toString() + "分"
        timeText.text = timeString
    }
}
