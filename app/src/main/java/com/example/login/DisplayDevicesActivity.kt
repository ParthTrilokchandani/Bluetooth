package com.example.login

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity


class DisplayDevicesActivity : AppCompatActivity() {

    private var receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            Log.i("TAG", "onReceive $action")
            if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED == action) {
                Log.i("TAG", "Discovery finished, hide loading")
            } else if (BluetoothDevice.ACTION_FOUND == action) {
                val device =
                    intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)

                Log.i("TAG", "Device Name: " + (device?.name ?: ""))
                Log.i("TAG", "Device Address:" + (device?.address ?: ""))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_devices)

        Log.e("Blue Error : ", "hello1")

        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val adapter = bluetoothManager.adapter
        Log.e("hello", "${adapter.isDiscovering}")
        Log.e("hello1", adapter.isDiscovering.toString())
        adapter.startDiscovery()

        val intentFilter = IntentFilter().apply {
            addAction(BluetoothDevice.ACTION_FOUND)
            addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        }

        // Register A Reciever
        registerReceiver(receiver, intentFilter)

        Log.e("Blue", "Hello")

        val actionBar: ActionBar? = supportActionBar
        actionBar.also {
            it?.title = "Available Devices"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}