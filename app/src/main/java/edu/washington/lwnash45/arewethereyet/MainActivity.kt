package edu.washington.lwnash45.arewethereyet

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.telephony.PhoneNumberUtils
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var alarm = getSystemService(Context.ALARM_SERVICE) as AlarmManager


        var phoneNumber : EditText = findViewById(R.id.phoneNumber) as EditText
        phoneNumber.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        var message: EditText = findViewById(R.id.message) as EditText
        var interval: EditText = findViewById(R.id.interval) as EditText


        var startBtn: Button = findViewById(R.id.startBtn) as Button
        startBtn.setOnClickListener {
            if (phoneNumber.text.length !== 14) {
                makeToast("Invalid Phone Number")
            } else if (message.text.isEmpty()) {
                makeToast("Need Message to send")
            } else if (interval.text.isEmpty()) {
                makeToast("Need interval")
            } else if (interval.text.toString().toInt() === 0) {
                makeToast("Need none zero interval")
            } else  {
                val alert = Intent(this, AlertActivity::class.java)
                alert.putExtra("number", phoneNumber.text.toString())
                alert.putExtra("message", message.text.toString())

                if (startBtn.text == "Start") {
                    val intent = PendingIntent.getBroadcast(this, 0, alert, PendingIntent.FLAG_UPDATE_CURRENT)
                    val miliInterval = (interval.text.toString().toInt() * 60000).toLong()
                    alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), miliInterval, intent)
                    Toast.makeText(this@MainActivity, "Repeated Alarm Set", Toast.LENGTH_SHORT).show()
                    startBtn.text = "Stop"
                } else {
                    val intent = PendingIntent.getBroadcast(this, 0, alert, 0)
                    alarm.cancel(intent)
                    startBtn.text = "Start"
                    Toast.makeText(this@MainActivity, "Repeating alarm canceled", Toast.LENGTH_SHORT).show()
                }

            }
        }

    }

    fun makeToast(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }

}
