package com.example.arduino

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.collections.ArrayList

class BluetoothViewModel: ViewModel() {

    private val _myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    private var _isConnected: Boolean = true
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

    val myUUID: UUID
        get() = _myUUID

    fun updateListItems(bt: BluetoothDevice){
        _listItems.add(bt)
    }

    fun updateIsConnected(){
        _isConnected = false
    }
}