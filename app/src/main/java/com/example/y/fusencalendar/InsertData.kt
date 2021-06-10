package com.example.y.fusencalendar

import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where

class InsertData {

    //Realmのインスタンスを取得
    private var realm: Realm = Realm.getDefaultInstance()

    //イベントのダミーデータ
    private val events = arrayOf(
        "テレビ特番", "10分前には待機",
        "友達と遊ぶ", "Switchを持っていく"
    )

    //ふせんのダミーデータ
    private val fusens = arrayOf(
        "バイト", "制服を忘れない",
        "勉強", "集中する"
    )

    //ダミーデータ追加
    init {
        realm.executeTransaction {
            realm.where<Event>().findAll().deleteAllFromRealm()
        }
        realm.executeTransaction {
            realm.where<Fusen>().findAll().deleteAllFromRealm()
        }
        for (i in events.indices step 2){
            realm.executeTransaction {
                var maxId = realm.where<Event>().max("id")?.toInt()
                if (maxId == null) maxId = 0
                val event = realm.createObject<Event>(maxId + 1)
                event.title = events[i]
                event.memo = events[i + 1]
            }
        }


        for (i in fusens.indices step 2){
            realm.executeTransaction {
                var maxId = realm.where<Fusen>().max("id")?.toInt()
                if (maxId == null) maxId = 0
                val fusen = realm.createObject<Fusen>(maxId!! + 1)
                fusen.title = fusens[i]
                fusen.memo = fusens[i + 1]
            }
        }
    }



}