package com.example.arduino

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import androidx.lifecycle.ViewModel
import java.io.InputStream
import java.io.OutputStream
import java.util.*
import kotlin.collections.ArrayList

class BluetoothViewModel: ViewModel() {

    private val _myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    private var _isConnected: Boolean = true
    private var _bluetoothSocket: BluetoothSocket? = null
    private lateinit var mmInStream: InputStream
    private lateinit var mmOutStream: OutputStream
    private val _listItems = ArrayList<BluetoothDevice>()
    private var _pairedDevices: Set<BluetoothDevice>? = null

    fun getPairedDevices(bt: BluetoothAdapter?){
        _pairedDevices = bt?.bondedDevices
    }

    val listItems: ArrayList<BluetoothDevice>
        get() = _listItems

    val pairedDevices: Set<BluetoothDevice>?
        get() = _pairedDevices

    val isConnected: Boolean
        get() = _isConnected

    val bluetoothSocket: BluetoothSocket?
        get() = _bluetoothSocket

    fun updateSocket(device: BluetoothDevice){
        _bluetoothSocket = device.createRfcommSocketToServiceRecord(_myUUID)
    }

    fun updateListItems(bt: BluetoothDevice){
        _listItems.add(bt)
    }
    fun setupTransfer(){
        mmInStream = bluetoothSocket!!.inputStream
        mmOutStream = bluetoothSocket!!.outputStream
    }

    val ina: InputStream
        get() = mmInStream

    val outa: OutputStream
        get() = mmOutStream

    fun updateIsConnected(){
        _isConnected = false
    }
}