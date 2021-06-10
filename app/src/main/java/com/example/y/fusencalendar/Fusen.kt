package com.example.y.fusencalendar

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Fusen : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var colorId: Int = 0
    var title: String = ""
    var memo: String = ""
}