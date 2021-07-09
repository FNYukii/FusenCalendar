package com.example.y.fusencalendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_edit_fusen.*
import kotlinx.android.synthetic.main.activity_edit_fusen.backButton
import kotlinx.android.synthetic.main.activity_edit_fusen.deleteButton
import kotlinx.android.synthetic.main.activity_edit_fusen.memoEdit
import kotlinx.android.synthetic.main.activity_edit_fusen.titleEdit

class EditFusenActivity : AppCompatActivity(), ColorDialogFragment.DialogListener {
    private lateinit var realm: Realm
    private var fusenId = 0

    var title: String = ""
    var memo: String = ""
    var colorId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_fusen)

        realm = Realm.getDefaultInstance()
        fusenId = intent.getIntExtra("id",0)

        if(fusenId > 0){
            val fusen = realm.where<Fusen>()
                .equalTo("id",fusenId).findFirst()
            titleEdit.setText(fusen?.title.toString())
            memoEdit.setText(fusen?.memo.toString())
            title = fusen?.title!!
            memo = fusen.memo
            colorId = fusen.colorId
        }
        colorChange(colorId)

        backButton.setOnClickListener{ v: View? ->
            save()
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

        //eventButtonを押すと、ふせんをカレンダーに貼るために日時を選択するActivityへ遷移する
        eventButton.setOnClickListener {
            val intent = Intent(this, PasteFusenToCalendarActivity::class.java)
            intent.putExtra("fusenId", fusenId)
            startActivity(intent)
        }

        colorButton02.setOnClickListener{ v: View? ->
            val colorDialog = ColorDialogFragment()
            colorDialog.show(supportFragmentManager, "hello")
        }

        copyButton.setOnClickListener{ v: View? ->
            save()
            title = titleEdit.text.toString()
            memo = memoEdit.text.toString()
            realm.executeTransaction {
                val maxId = realm.where<Fusen>().max("id")
                val nextId = (maxId?.toInt() ?: 0) + 1
                val Fusen = realm.createObject<Fusen>(nextId)
                Fusen.title = title + "(コピー)"
                Fusen.memo = memo
                Fusen.colorId = colorId
            }
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        save()
        Toast.makeText(applicationContext, "保存しました", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    fun save(){
        if(titleEdit.text.isNotEmpty() || memoEdit.text.isNotEmpty()) {
            title = titleEdit.text.toString()
            memo = memoEdit.text.toString()
            when (fusenId) {
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
                            .equalTo("id", fusenId).findFirst()
                        Fusen?.title = title
                        Fusen?.memo = memo
                        Fusen?.colorId = colorId
                    }
                }
            }
        }else{
            finish()
        }
    }

    override fun onDialogColorIdReceive(dialog: DialogFragment, colorId: Int) {
        this.colorId = colorId
        colorChange(colorId)
    }

    fun colorChange(colorId: Int){
        var backGroundColor = ContextCompat.getColor(this, R.color.blue)
        when(colorId){
            0 -> {
                backGroundColor = ContextCompat.getColor(this, R.color.blue)
            }
            1 -> {
                backGroundColor = ContextCompat.getColor(this, R.color.green)
            }
            2 -> {
                backGroundColor = ContextCompat.getColor(this, R.color.yellow)
            }
            3 -> {
                backGroundColor = ContextCompat.getColor(this, R.color.red)
            }
            4 -> {
                backGroundColor = ContextCompat.getColor(this, R.color.purple)
            }
        }
        Edit_Fusen_Layout.setBackgroundColor(backGroundColor)
        toolBar02.setBackgroundColor(backGroundColor)
        window.statusBarColor = backGroundColor
        window.navigationBarColor = backGroundColor
    }
}