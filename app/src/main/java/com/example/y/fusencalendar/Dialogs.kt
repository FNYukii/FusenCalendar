package com.example.y.fusencalendar

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import java.lang.Exception
import java.util.*


/*
* このクラスでダイアログ作成処理の全般を行います
* 呼び出し側で必要な情報をこのクラスの各ダイアログクラスに渡すことで
* ダイアログが作成され表示されます
* ※このクラスはダイアログの表示処理のみを行いその他の処理は呼び出し側で行います
* */


//DialogFragmentを継承する２つボタン選択のダイアログ確認を行うダイアログ全般
class ConfirmDialog(private val message:String,          //ダイアログのタイトル
                    private val okLable:String,          //OKボタンのラベル
                    private val okSelected:() -> Unit,   //OKボタンがタップされたときに実行する関数
                    private val cancelLable:String,      //Cancelボタンのラベル
                    private val cancelSelected : () -> Unit     //Cancelボタンがタップされたときに実行する関数
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

            dialog, which ->  cancelSelected()
        }
        //builderのcreate()メソッドを返しAlertDialogオブジェクトを返す
        return builder.create()
    }

}


//DatePicker
class DatePickerDialogFragment: DialogFragment(), DatePickerDialog.OnDateSetListener {

    //呼び出し元のActivityに値を渡すリスナー
    interface OnSelectedDateListener {
        fun selectedDate(year: Int, month: Int, date: Int)
    }

    private lateinit var listener: OnSelectedDateListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnSelectedDateListener){
            listener = context
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = context
        if(context != null) {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = Date().time
            calendar.timeInMillis = Date().time
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(context, this, year, month, day)
            return datePickerDialog
        }else{
            return super.onCreateDialog(savedInstanceState)
        }
    }

    //DatePickerDialogから選択結果を受け取る
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        listener.selectedDate(year, month, dayOfMonth)
    }
}


//TimePicker
class TimePickerDialogFragment: DialogFragment(), TimePickerDialog.OnTimeSetListener {

    //呼び出し元のActivityに値を渡すリスナー
    interface OnSelectedTimeListener {
        fun selectedTime(hour: Int, minute: Int)
    }

    private lateinit var listener: OnSelectedTimeListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnSelectedTimeListener){
            listener = context
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = Date().time
        val context = context
        return when {
            context != null -> {
                TimePickerDialog(
                    context,
//                    R.style.CustomTimePickerDialog,
                    this,
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                )
            }
            else -> super.onCreateDialog(savedInstanceState)
        }
    }

    //TimePickerDialogから選択結果を受け取る
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        listener.selectedTime(hourOfDay, minute)
    }
}



//時刻選択を提供するダイアログ
//class TimeDialog(private val onSelected:(String) -> Unit):DialogFragment(),TimePickerDialog.OnTimeSetListener{
//
//    //ダイアログを作成する関数
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        //カレンダークラスのインスタンスを取得
//        val c = Calendar.getInstance()
//        //現在の時刻を初期値として取得
//        val hour = c.get(Calendar.HOUR_OF_DAY)
//        val minute = c.get(Calendar.MINUTE)
//        //インスタンスを生成し返す
//        /*
//        * 第1引数：ダイアログを表示する対象のコンテキスト
//        * 第2引数：日付がセットされたときに呼ばれるリスナー
//        * 第3引数、第4引数：初期設定される時刻
//        * 第5引数：24時間表示(true)かAM/PM表記(false)
//        * */
//        return TimePickerDialog(context,this,hour,minute,true)
//    }
//
//    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
//        onSelected("%1$2d:%2$2d".format(hourOfDay,minute))
//    }
//}



//色を選択するダイアログ
class ColorDialogFragment: DialogFragment(){

    //EditActivityへcolorIdを渡すためのインターフェース
    interface DialogListener{
        fun onDialogColorIdReceive(dialog: DialogFragment, colorId: Int)
    }
    private var listener:DialogListener? = null

    //配列や変数を宣言
    private val colors = arrayOf("緑", "青", "オレンジ", "赤", "紫")
    private var colorId: Int = -1

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        //dialogをセッティング
        return activity?.let {
            val builder = android.app.AlertDialog.Builder(it)
            builder.setTitle("色を選択")
                .setSingleChoiceItems(colors, -1){ _, which ->
                    colorId = which
                }
                .setPositiveButton("OK"
                ) { _, _ ->
                    //ラジオボタンで色が選択されていたら、ホストActivityへcolorIdを渡す
                    if (colorId != -1) {
                        listener?.onDialogColorIdReceive(this, colorId)
                    }
                }
                .setNegativeButton("キャンセル"
                ) { _, _ ->
                    //do nothing
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as DialogListener
        }catch (e: Exception){
            Toast.makeText(this.context, "Error! Can not find listener", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

}