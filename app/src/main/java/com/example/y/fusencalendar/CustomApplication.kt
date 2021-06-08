package com.example.y.fusencalendar

import android.app.Application
import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration

class CustomApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        //データベース初期化 Modelクラスが変更されたらDB再構成
        Realm.init(this)
        val realmConfig: RealmConfiguration = RealmConfiguration
            .Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
//        Realm.deleteRealm(realmConfig)
        Realm.setDefaultConfiguration(realmConfig)



    }

}