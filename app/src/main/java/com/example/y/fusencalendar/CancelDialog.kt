package com.example.y.fusencalendar

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

//DialogFagmentを継承する２つボタン選択のダイアログ
class CnacelDialog(private val message:String,          //ダイアログのタイトル
                   private val okLable:String,          //OKボタンのラベル
                   private val okSelected:() -> Unit,   //OKボタンがタップされたときに実行する関数
                   private val cancelLable:String,      //Cancellボタンのラベル
                   private val cancellSelected : () -> Unit     //Cancellボタンがタップされたときに実行する関数
                    ):DialogFragment(){

    //onCreateDialogをオーバーライドしたダイアログを作成する関数
    //ダイアログが生成された時(onCreateonとCreateViewの間)に呼び出される
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        //AlertDialog.Builderのインスタンスを生成(引数はダイアログを生成するアクティビティ)
        //requireActivity()はフラグメントを呼び出したアクティビティを返す
        val builder = AlertDialog.Builder(requireActivity())
        //表示するメッセージを設定
        builder.setMessage(message)
        //ダイアログに表示する１番目のボタン
        builder.setPositiveButton(okLable){
            //onClickメソッドをSAM変換でラムダ式として記述
            //dialogタップが発生したダイアログ
            //whichタップが発生したボタンの種類
            dialog, which ->  okSelected()
        }
        //ダイアログに表示する２番めのボタン
        builder.setNegativeButton(cancelLable){

            dialog, which ->  cancellSelected()
        }
        //builderのcreate()メソッドを返しAlertDialogオブジェクトを返す
        return builder.create()
    }


}