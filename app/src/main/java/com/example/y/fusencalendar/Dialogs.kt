package com.example.y.fusencalendar

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import java.util.*


/*
* このクラスでダイアログ作成処理の全般を行います
* 呼び出し側で必要な情報をこのクラスの各ダイアログクラスに渡すことで
* ダイアログが作成され表示されます
* ※このクラスはダイアログの表示処理のみを行いその他の処理は呼び出し側で行います
* */


//DialogFagmentを継承する２つボタン選択のダイアログ確認を行うダイアログ全般
class ConfirmDialog(private val message:String,          //ダイアログのタイトル
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

//時刻選択を提供するダイアログ
class TimeDialog(private val onSelected:(String) -> Unit):DialogFragment(),TimePickerDialog.OnTimeSetListener{

    //ダイアログを作成する関数
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //カレンダークラスのインスタンスを取得
        val c = Calendar.getInstance()
        //現在の時刻を初期値として取得
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        //インスタンスを生成し返す
        /*
        * 第1引数：ダイアログを表示する対象のコンテキスト
        * 第2引数：日付がセットされたときに呼ばれるリスナー
        * 第3引数、第4引数：初期設定される時刻
        * 第5引数：24時間表示(true)かAM/PM表記(false)
        * */
        return TimePickerDialog(context,this,hour,minute,true)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        onSelected("%1$2d:%2$2d".format(hourOfDay,minute))
    }
}

class ColorSelectDialog(private val message:String,          //ダイアログのタイトル
                        private val okLable:String,          //OKボタンのラベル
                        private val okSelected:() -> Unit,   //OKボタンがタップされたときに実行する関数
                        private val cancelLable:String,      //Cancellボタンのラベル
                        private val cancellSelected : () -> Unit):DialogFragment(){

    override fun onCreateDialog(savedInstanceState: Bundle?):Dialog {

        //AlertDialog.Builderのインスタンスを生成(引数はダイアログを生成するアクティビティ)
        //requireActivity()はフラグメントを呼び出したアクティビティを返す
        val builder = AlertDialog.Builder(requireActivity())
        //表示するメッセージを設定
        builder.setMessage(message)
        //ダイアログに表示する１番目のボタン
        builder.setPositiveButton(okLable) {
            //onClickメソッドをSAM変換でラムダ式として記述
            //dialogタップが発生したダイアログ
            //whichタップが発生したボタンの種類
                dialog, which ->
            okSelected()
        }
        //ダイアログに表示する２番めのボタン
        builder.setNegativeButton(cancelLable) {

                dialog, which ->
            cancellSelected()
        }
        //builderのcreate()メソッドを返しAlertDialogオブジェクトを返す
        return builder.create()
    }
}