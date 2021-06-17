package com.example.y.fusencalendar

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.fragment_fusen_list.*

class FusenListFragment : Fragment() {


    //Realmのインスタンスを取得
    var realm: Realm = Realm.getDefaultInstance()


    //Fragmentを生成開始
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fusen_list, container, false)
    }


    //Fragment生成後
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //floatingButtonを押すと、EditFusenActivityへ遷移する
        floatingButton.setOnClickListener {
            val intent = Intent(this.context, EditFusenActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onStart() {
        super.onStart()

        //Fusenテーブルの全レコードを変数realmResultsに取得
        val realmResults = realm.where<Fusen>()
            .findAll()

        //RecyclerViewを表示
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = FusenRecyclerViewAdapter(realmResults)
    }

}