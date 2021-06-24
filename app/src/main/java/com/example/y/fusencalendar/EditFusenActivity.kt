package com.example.y.fusencalendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_edit_fusen.*
import java.util.*

class EditFusenActivity : AppCompatActivity() {
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_fusen)

        realm = Realm.getDefaultInstance()

        val fusenId = intent.getIntExtra("id",0)

        if(fusenId > 0){
            val fusen = realm.where<Fusen>()
                .equalTo("id",fusenId).findFirst()
            titleEdit.setText(fusen?.title.toString())
            memoEdit.setText(fusen?.memo.toString())
            Toast.makeText(applicationContext, "色は${fusen?.colorId.toString()}番です。", Toast.LENGTH_SHORT).show()
            deleteButton.visibility = View.VISIBLE
        } else{
            deleteButton.visibility = View.INVISIBLE
        }

        backButton.setOnClickListener{ v: View? ->
            var title: String = "タイトル"
            var memo: String = "メモ"
            var colorId: Int = 0
            if(!titleEdit.text.isNullOrEmpty()){
                title = titleEdit.text.toString()
            }
            if(!memoEdit.text.isNullOrEmpty()){
                memo = memoEdit.text.toString()
            }

            when(fusenId){
                0 -> {
                    realm.executeTransaction {
                        val maxId = realm.where<Fusen>().max("id")
                        val nextId = (maxId?.toInt() ?: 0) + 1
                        val Fusen = realm.createObject<Fusen>(nextId)
                        Fusen.title = title
                        Fusen.memo = memo
                        Fusen.colorId = colorId
                    }
                }
                else -> {
                    realm.executeTransaction {
                        val Fusen = realm.where<Fusen>()
                            .equalTo("id",fusenId).findFirst()
                        Fusen?.title = title
                        Fusen?.memo = memo
                        Fusen?.colorId = colorId
                    }
                }
            }
            Toast.makeText(applicationContext, "保存しました", Toast.LENGTH_SHORT).show()
            finish()
        }
        deleteButton.setOnClickListener(){
            realm.executeTransaction{
                val fusen = realm.where<Fusen>()
                    .equalTo("id",fusenId)
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