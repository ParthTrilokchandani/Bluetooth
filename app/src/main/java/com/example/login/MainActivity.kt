package com.example.login

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("Blue Error : ", "hello")
        setContentView(R.layout.activity_main)

        val blue = BluetoothAdapter.getDefaultAdapter()

        val buttonOn = findViewById<Button>(R.id.BtnTurnOn).setOnClickListener {
            if(!blue.isEnabled) {
                Toast.makeText(this, "TURNING ON BLUETOOTH", Toast.LENGTH_SHORT).show()
                val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(intent, 0)
            } else {
                Toast.makeText(this, "BLUETOOTH IS ALREADY ON", Toast.LENGTH_SHORT).show()
            }
        }

        val buttonOff = findViewById<Button>(R.id.BtnTurnOff).setOnClickListener {
            if(!blue.isEnabled) {
                Toast.makeText(this, "BLUETOOTH IS ALREADY OFF", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "TURNING OFF BLUETOOTH", Toast.LENGTH_SHORT).show()
                blue.disable()
            }
        }

        val buttonDiscover = findViewById<Button>(R.id.BtnDiscover).setOnClickListener {
            if(!blue.isDiscovering) blue.startDiscovery()
            val discoverableIntent: Intent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE).apply {
                putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300)
                val intent = Intent(applicationContext, DisplayDevicesActivity::class.java)
                startActivity(intent)
            }

            startActivityForResult(discoverableIntent, 1)
        }

        val actionBar: ActionBar? = supportActionBar
        actionBar.also {
            it?.title = " Bluetooth Activity"
        }
    }
}