package com.example.y.fusencalendar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_fusen_list.*

class FusenListFragment : Fragment() {


    //RecyclerView用のインスタンス宣言
    private lateinit var adapter: CustomRecyclerViewAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fusen_list, container, false)
    }


    override fun onStart() {
        super.onStart()

        //RecyclerViewを表示
        layoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = layoutManager
        adapter = CustomRecyclerViewAdapter()
        recyclerView.adapter = this.adapter


    }



}