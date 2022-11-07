package com.happiestminds.reminderapplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor

class DBWrapper(ctx:Context) {
    val helper =DBHelper(ctx)
    val db = helper.writableDatabase

    fun addReminder(rem:Reminder):Boolean{
        val values = ContentValues()
        values.put(DBHelper.CLM_TITLE,rem.title)
        values.put(DBHelper.CLM_DESC,rem.desc)
        values.put(DBHelper.CLM_DATE,rem.date)
        values.put(DBHelper.CLM_TIME,rem.time)

        val rowID = db.insert(DBHelper.TABLE_NAME,null,values)

        if (rowID.toInt()==-1)
            return false
        return true
    }

    fun getReminder():Cursor{
        val clms = arrayOf(DBHelper.CLM_TITLE,DBHelper.CLM_DESC,DBHelper.CLM_DATE,DBHelper.CLM_TIME)
        return db.query(DBHelper.TABLE_NAME,clms,null,null,null,null,null)
    }

    fun deleteReminder(rem:Reminder){
        db.delete(DBHelper.TABLE_NAME,"${DBHelper.CLM_TITLE}= ?", arrayOf(rem.title))
    }
}