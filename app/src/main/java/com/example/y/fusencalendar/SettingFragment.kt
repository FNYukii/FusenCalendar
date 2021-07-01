package com.example.y.fusencalendar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment


class SettingFragment : Fragment(),SelectNotificationTimeDialogFragment.DialogListener {


    var timeId :Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val slideText :ViewGroup = view.findViewById(R.id.linearLayout3)
        val alarmText : ViewGroup = view.findViewById(R.id.linearLayout)
        val helpText : ViewGroup = view.findViewById(R.id.linearLayout4)
        tap(slideText)
        tap(alarmText)

        alarmText.setOnClickListener {v: View? ->
            val timeDialog = SelectNotificationTimeDialogFragment()
            timeDialog.show(parentFragmentManager, "select_time_dialog")
        }

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