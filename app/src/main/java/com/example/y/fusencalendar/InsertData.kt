package com.example.y.fusencalendar

import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where

class InsertData {

    //Realmのインスタンスを取得
    private var realm: Realm = Realm.getDefaultInstance()

    //ダミーデータ
    private val contents = arrayOf(
        "バイト", "制服を忘れない",
        "勉強", "集中する"
    )


    //ダミーデータ追加
    init {
        for (i in contents.indices step 2){
            realm.executeTransaction {
                var maxId = realm.where<Fusen>().max("id")?.toInt()
                if (maxId == null) maxId = 0
                val fusen = realm.createObject<Fusen>(maxId + 1)
                fusen.title = contents[i]
                fusen.memo = contents[i + 1]
            }
        }
    }



}