package edu.washington.lwnash45.arewethereyet

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

class AlertActivity : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("working", "alarm recieved")
        val number = intent?.getStringExtra("number")
        val message = intent?.getStringExtra("message")
        Toast.makeText(context, "Texting{$number}: $message", Toast.LENGTH_SHORT).show()
    }
}
