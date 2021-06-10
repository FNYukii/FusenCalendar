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
import kotlinx.android.synthetic.main.fragment_calendar.*
import kotlinx.android.synthetic.main.fragment_fusen_list.*

class FusenListFragment : Fragment() {


    //RecyclerView用のインスタンス宣言
    private lateinit var adapter: FusenRecyclerViewAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager

    //Realmのインスタンスを取得
    var realm: Realm = Realm.getDefaultInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fusen_list, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button.setOnClickListener { view ->
            val intent = Intent(this.context, EditFusenActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()

        //RecyclerViewを表示
        val realmResults = realm.where<Fusen>()
            .findAll()
        layoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = layoutManager
        adapter = FusenRecyclerViewAdapter(realmResults)
        recyclerView.adapter = this.adapter
    }

}