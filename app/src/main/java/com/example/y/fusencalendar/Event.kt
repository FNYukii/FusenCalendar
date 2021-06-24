package com.example.y.fusencalendar

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Event : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var year: Int = 0
    var month: Int = 0
    var date: Int = 0
    var hour: Int = 0
    var minute: Int = 0
    var colorId: Int = 0
    var title: String = ""
    var memo: String = ""
}