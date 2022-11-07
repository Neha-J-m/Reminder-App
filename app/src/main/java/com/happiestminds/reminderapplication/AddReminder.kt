package com.happiestminds.reminderapplication

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.content.Intent
import android.icu.util.Calendar
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.provider.CalendarContract.ACTION_EVENT_REMINDER
import android.provider.CalendarContract.Events
import android.util.Log
import android.view.View
import android.widget.*
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

class AddReminder : AppCompatActivity(), TimePickerDialog.OnTimeSetListener {
    lateinit var titleEditText : EditText
    lateinit var descEditText : EditText
    lateinit var dateText : TextView
    lateinit var timeText : TextView
    lateinit var timeSelect : Button


    lateinit var beginTime: Calendar

    var selectedDate:String = ""
    var selectedTime:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_reminder)
        titleEditText = findViewById(R.id.titleT)
        descEditText = findViewById(R.id.descT)
        dateText = findViewById(R.id.dateT)
        timeText = findViewById(R.id.timeT)
        timeSelect = findViewById(R.id.selectTimeB)


    }

    fun selectDate(view: View) {
        val dlg = DatePickerDialog(this)
        dlg.setOnDateSetListener { datePicker, year, month, day ->
            selectedDate = "$day-${month + 1}-$year"
            dateText.text = selectedDate
        }
        dlg.show()
    }

    fun selectTime(view: View) {
        val dlg = TimePickerDialog(this,this,10,0,false)
        dlg.show()
    }

    override fun onTimeSet(p0: TimePicker?, hour: Int, min: Int) {
        selectedTime = "$hour:$min"
        timeText.text = selectedTime
    }

    fun remClick(view: View) {
        when(view.id){
            R.id.addButton ->{
                if (titleEditText.text.toString().isEmpty()) {
                    titleEditText.error = "Title field cannot be empty"
                }
                if(titleEditText.text.toString().isNotEmpty() and selectedDate.isNotEmpty() and selectedTime.isNotEmpty()){
                    val dlg = NewDialog()
                    val dataBundle = Bundle()
                    dataBundle.putString(
                        "confirm", """Title: ${titleEditText.text}
                    |Description: ${descEditText.text}
                    |Date: $selectedDate
                    |Time: $selectedTime
                """.trimMargin()
                    )
                    dlg.arguments = dataBundle
                    dlg.show(supportFragmentManager, null)

//                    val newReminder = Reminder(
//                        titleEditText.text.toString(),
//                        descEditText.text.toString(),
//                        selectedDate,
//                        selectedTime
//                    )
//                    lstReminder.add(newReminder)
//                        addToDatabase()
//                        addToCalendar()


//                    if (DBWrapper(this).addReminder(newReminder)) {
//                        Toast.makeText(this, "Reminder added to database", Toast.LENGTH_LONG).show()
//                    } else
//                        Toast.makeText(this, "Reminder NOT added", Toast.LENGTH_LONG).show()
//
//                    val shwIntent = Intent(this, ListActivity().javaClass)
//                    shwIntent.putExtra("Title", titleEditText.text.toString())

                }
                else {
                    val addB = findViewById<Button>(R.id.addButton)
                    Snackbar.make(this, addB, "Please fill the details", Snackbar.LENGTH_LONG)
                        .show()
                }
            }
            R.id.cancelRemB -> finish()
        }
    }

    fun addToDatabase() {
        val newReminder = Reminder(
            titleEditText.text.toString(),
            descEditText.text.toString(),
            selectedDate,
            selectedTime
        )
        lstReminder.add(newReminder)
        if (DBWrapper(this).addReminder(newReminder)) {
            Toast.makeText(this, "Reminder added to database", Toast.LENGTH_LONG).show()
        } else
            Toast.makeText(this, "Reminder NOT added", Toast.LENGTH_LONG).show()

        val shwIntent = Intent(this, ListActivity().javaClass)
        shwIntent.putExtra("Title", titleEditText.text.toString())
    }

    fun addToCalendar(){
        val cr:ContentResolver = contentResolver
        val dateString = "$selectedDate $selectedTime"
        val format = SimpleDateFormat("dd-MM-yyyy HH:mm")
        val date = format.parse(dateString)
        val cal = Calendar.getInstance()
        cal.time = date
        Log.d("Add reminder", "milli: ${cal.timeInMillis}")
        val value = ContentValues().apply {
            put(Events.TITLE, titleEditText.text.toString())
            put(Events.DESCRIPTION, descEditText.text.toString())
            put(Events.DTSTART, cal.timeInMillis)
            put(Events.DTEND, cal.timeInMillis + 60 * 1000)
            put(Events.CALENDAR_ID, 1)
            put(Events.EVENT_TIMEZONE, "IST")
            put(Events.ALL_DAY, false)
            put(Events.HAS_ALARM, 1)
        }

        val uri = cr.insert(Events.CONTENT_URI, value)
        val eventId = uri?.lastPathSegment?.toInt()

        val values = ContentValues()
        values.put(CalendarContract.Reminders.EVENT_ID, eventId)
        values.put(
            CalendarContract.Reminders.METHOD,
            CalendarContract.Reminders.METHOD_DEFAULT
        )
        values.put(
            CalendarContract.Reminders.MINUTES,
            CalendarContract.Reminders.MINUTES_DEFAULT
        )
        val reminderUri = cr.insert(CalendarContract.Reminders.CONTENT_URI, values)
        Toast.makeText(this, "Reminder added successfully", Toast.LENGTH_LONG).show()
        Log.d("AddReminder", "Reminder URI: $reminderUri")
    }
}



