package com.example.arduino

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.collections.ArrayList

class BluetoothViewModel: ViewModel() {

    //private var isConnected: Boolean = false
    private val _listItems = ArrayList<BluetoothDevice>()
    private var _pairedDevices: Set<BluetoothDevice>? = null
    private val _myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    fun getPairedDevices(bt: BluetoothAdapter?){
        _pairedDevices = bt?.bondedDevices
    }

    val myUUID: UUID
        get() = _myUUID

    val listItems: ArrayList<BluetoothDevice>
        get() = _listItems

    val pairedDevices: Set<BluetoothDevice>?
        get() = _pairedDevices

    fun updateListItems(bt: BluetoothDevice){
        _listItems.add(bt)
    }

    fun sortList(){

    }

}