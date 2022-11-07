package com.happiestminds.reminderapplication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(checkSelfPermission(Manifest.permission.READ_CALENDAR) or checkSelfPermission(Manifest.permission.WRITE_CALENDAR) ==PackageManager.PERMISSION_DENIED ){
            Log.d("MainActivity","Permission Not granted Request user")
            requestPermissions(arrayOf(android.Manifest.permission.READ_CALENDAR,Manifest.permission.WRITE_CALENDAR),1)
        }
        else
            Log.d("MainActivity","Permission granted")
    }

//    private fun setup() {
//        if (checkSelfPermission(Manifest.permission.READ_CALENDAR) or checkSelfPermission(Manifest.permission.WRITE_CALENDAR) ==PackageManager.PERMISSION_DENIED) {
//            Log.d("MainActivity","Permission NOT granted")
//            requestPermissions(
//                arrayOf(
//                    Manifest.permission.READ_CALENDAR,
//                    Manifest.permission.WRITE_CALENDAR
//                ), 1
//            )
//        }
//        else
//            Log.d("MainActivity","Permission already granted")
//    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1){
            if (permissions.isNotEmpty()){
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Log.d("MainActivity","User accepted permission")
                }
            }else{
                Log.d("MainActivity","User denied permission")

            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun buttonClick(view: View) {
        when(view.id){
            R.id.addButton->{
                val addIntent = Intent(this,AddReminder().javaClass)
                startActivity(addIntent)
            }
            R.id.showButton->{
                val showIntent = Intent(this,ListActivity().javaClass)
                startActivity(showIntent)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add(0,100,0,"Exit")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        exitProcess(1)
        return super.onOptionsItemSelected(item)
    }
}