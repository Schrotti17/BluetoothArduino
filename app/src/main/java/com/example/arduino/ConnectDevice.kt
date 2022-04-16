package com.example.arduino

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import java.io.IOException
import java.io.Serializable
import java.util.*

var connectSuccess = true
class ConnectDevice(address: String) : Thread(), Serializable {
    private var address: String = address
    var myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    var bluetoothSocket: BluetoothSocket? = null
    lateinit var bluetoothAdapter: BluetoothAdapter
    var isConnected: Boolean = false
    init {
        this.address = address
    }

    override fun run() {
        try {
            if (bluetoothSocket == null || !isConnected) {
                bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                val device: BluetoothDevice = bluetoothAdapter.getRemoteDevice(address)
                bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(myUUID)
                bluetoothAdapter.cancelDiscovery()
                bluetoothSocket!!.connect()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            isConnected = true
            connectSuccess = false
        }
    }
}

