package com.happiestminds.reminderapplication

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context:Context) : SQLiteOpenHelper(context,"reminder.db",null,1){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    companion object{
        const val TABLE_NAME ="ReminderData"
        const val CLM_TITLE = "title"
        const val CLM_DESC = "desc"
        const val CLM_DATE = "date"
        const val CLM_TIME = "time"
        const val TABLE_QUERY = "create table $TABLE_NAME($CLM_TITLE text primary key,$CLM_DESC text,$CLM_DATE date,$CLM_TIME text)"
    }
}