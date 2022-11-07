package com.happiestminds.reminderapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

class ListActivity : AppCompatActivity() {
    lateinit var listViewRem: ListView
    var lst = lstReminder
    lateinit var adapter:ArrayAdapter<Reminder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        listViewRem = findViewById(R.id.listViewReminder)



        adapter = ArrayAdapter<Reminder>(
            this,
            android.R.layout.simple_list_item_1, lst
        )
        listViewRem.adapter = adapter
        setup()

        listViewRem.setOnItemClickListener { parent, view, position, id ->
            val selectedTitle = lstReminder[position]
            val dlg = MyDialog()
            val dataBundle = Bundle()

            dataBundle.putString(
                "msg", """Description: ${selectedTitle.desc}
                |Date: ${selectedTitle.date}
                |Time: ${selectedTitle.time}
            """.trimMargin()
            )
            dataBundle.putString("title","${selectedTitle.title}")
            dataBundle.putString("desc","${selectedTitle.desc}")
            dataBundle.putString("date","${selectedTitle.date}")
            dataBundle.putString("time","${selectedTitle.time}")
            dataBundle.putString("pos", "$position")

            dlg.arguments = dataBundle

            dlg.show(supportFragmentManager, null)
        }
    }

    private fun setup() {
        val cursor = DBWrapper(this).getReminder()
        if (cursor.count > 0) {
            val idx_title = cursor.getColumnIndexOrThrow("${DBHelper.CLM_TITLE}")
            val idx_desc = cursor.getColumnIndexOrThrow("${DBHelper.CLM_DESC}")
            val idx_date = cursor.getColumnIndexOrThrow("${DBHelper.CLM_DATE}")
            val idx_time = cursor.getColumnIndexOrThrow("${DBHelper.CLM_TIME}")
            lst.clear()
            cursor.moveToFirst()
            do {
                val title = cursor.getString(idx_title)
                val desc = cursor.getString(idx_desc)
                val date = cursor.getString(idx_date)
                val time = cursor.getString(idx_time)

                val rem = Reminder(title,desc,date,time)

                lst.add(rem)
            } while (cursor.moveToNext())

            adapter.notifyDataSetChanged()
            Log.d("ReminderList", "List: $lst")
//            Toast.makeText(this, "Found: ${lst.count()}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume() {
        super.onResume()
        setup()
        adapter.notifyDataSetChanged()
    }

    fun backClick(view: View) {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

}
