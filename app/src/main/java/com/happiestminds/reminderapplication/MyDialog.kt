package com.happiestminds.reminderapplication

import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import androidx.appcompat.app.AlertDialog

class MyDialog: androidx.fragment.app.DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var dlg: Dialog? = null


        //retrieve bundle
        val message = arguments?.getString("msg")
        val pos = arguments?.getString("pos")?.toInt()
        val title = arguments?.getString("title")
        val desc = arguments?.getString("desc")
        val date = arguments?.getString("date")
        val time =arguments?.getString("time")

        val rem =Reminder(title!!,desc!!,date!!,time!!)

        //create dialog here
        context?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Details")
            builder.setMessage(message)
//            builder.setPositiveButton("Yes"){dialog,i ->
//                //executed button clicking
//                activity?.finish()
//            }
          builder.setNegativeButton("Delete"){dialog,i->
              lstReminder.removeAt(pos!!)
              DBWrapper(it).deleteReminder(rem)
              (activity as ListActivity).adapter.notifyDataSetChanged()
              dialog.dismiss()
            }
            builder.setNeutralButton("Cancel"){dialog,i->
                dialog.cancel()
            }
            //allocation
            dlg=builder.create()
        }
        return dlg!!
    }
}

