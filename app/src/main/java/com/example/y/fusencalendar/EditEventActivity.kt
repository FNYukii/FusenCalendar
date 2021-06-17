package com.example.y.fusencalendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class EditEventActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_event)

        val eventId = intent.getIntExtra("eventId", 0)
        Toast.makeText(applicationContext, "eventId: $eventId", Toast.LENGTH_SHORT).show()

    }
}