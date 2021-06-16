package com.example.y.fusencalendar

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Event : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var datetime: Date = Date()
    var colorId: Int = 0
    var title: String = ""
    var memo: String = ""
}