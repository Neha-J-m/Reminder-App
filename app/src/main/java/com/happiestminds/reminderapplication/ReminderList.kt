package com.happiestminds.reminderapplication

import java.util.Date


var lstReminder = mutableListOf<Reminder>()

data class Reminder(var title:String,var desc:String,var date:String,var time:String){
    override fun toString(): String {
        return "$title"
    }
}
