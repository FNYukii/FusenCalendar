package com.example.y.fusencalendar

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_setting.*


class SettingFragment : Fragment(),SelectNotificationTimeDialogFragment.DialogListener {

    private var pref : SharedPreferences? = null
    //スイッチボタンの状態フラグ
    private var flag = false

    var timeId :Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //各画面パーツのIDと紐づけ
        val slideText :ViewGroup = view.findViewById(R.id.linearLayout3)
        val alarmText : ViewGroup = view.findViewById(R.id.linearLayout)
        val helpText : ViewGroup = view.findViewById(R.id.linearLayout4)
        //tap(slideText)
        //tap(alarmText)
        //スライドのスイッチボタンとテキストは初期状態では非存在状態
        linearLayout5.visibility = View.GONE

        //プレファレンスのインスタンスを取得
        pref = activity?.getSharedPreferences("Setting",MODE_PRIVATE)

        //プレファレンスのファイルに書き込むEditorのインスタンスを取得
        val editor = pref!!.edit()


        //スライド方向メニューが押された時の処理
        slideText.setOnClickListener {
            //スイッチボタンメニューが表示されていない状態でスライド方向メニューが押された場合
            if (flag == false){
                //処理
                //スイッチボタンを表示
                linearLayout5.visibility = View.VISIBLE
                //状態を反転
                flag = !flag
                //デバッグ用
                Log.d("状態確認スライドメニュー出現時", flag.toString())

            }else{
                //処理
                //スイッチボタンを表示
                linearLayout5.visibility = View.GONE
                //状態を反転
                flag = !flag
                //デバッグ用
                Log.d("状態確認スライドメニュー消去時", flag.toString())


            }

        }
        //スイッチボタンの状態が変更された時のリスナ
        switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            //ボタンがチェックされているとき
            if (isChecked){
                //設定を書き込む(縦方向はtrue)
                editor.putBoolean("SLIDE_DIRECTION",true)
                //設定を保存する
                editor.commit()
                //デバッグ用
                Log.d("保存確認","縦方向で保存しました")
            }else{      //ボタンがチェックされていないとき
                //設定を書き込む(横方向はfalse)
                editor.putBoolean("SLIDE_DIRECTION",false)
                //設定を保存する
                editor.commit()
                //デバッグ用
                Log.d("保存確認","横方向で保存しました")
            }
        }


        //通知メニューをタップした時の処理
        alarmText.setOnClickListener {v: View? ->
            val timeDialog = SelectNotificationTimeDialogFragment()
            timeDialog.show(parentFragmentManager, "select_time_dialog")
        }

        //ヘルプメニューをタップした時の処理
        helpText.setOnClickListener {
            val dialog = ConfirmDialog("キャンセルしますか？","キャンセル",{

            },"戻る",{})
            dialog.show(parentFragmentManager,"colorselectdialog")
        }
    }



    fun tap(linerLayout: ViewGroup){
        linerLayout.setOnClickListener {
            val dialog = ConfirmDialog("キャンセルしますか？","キャンセル",{

            },"戻る",{})
            dialog.show(parentFragmentManager,"cancell_dialog")
        }
    }

    override fun onDialogTimeIdReceive(dialog: DialogFragment, timeId: Int) {
        this.timeId = timeId
    }

}