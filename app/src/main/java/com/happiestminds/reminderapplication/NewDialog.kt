package com.happiestminds.reminderapplication

import android.app.Dialog
import android.content.ContentValues
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat

class NewDialog:androidx.fragment.app.DialogFragment(){
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var newDlg:Dialog?=null
        val confirmation = arguments?.getString("confirm")
        context?.let {
            val newBuilder = AlertDialog.Builder(it)
            newBuilder.setTitle("Do you want to add this reminder?")
            newBuilder.setMessage(confirmation)
            newBuilder.setPositiveButton("Yes"){dialog,i ->
                //executed button clicking
                (activity as AddReminder).addToDatabase()
                (activity as AddReminder).addToCalendar()
                activity?.finish()
            }
            newBuilder.setNegativeButton("No"){dialog,i->
                dialog.cancel()
            }
            //allocation
            newDlg=newBuilder.create()
        }
        return newDlg!!
    }
}