package com.example.arduino

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import java.io.IOException
import java.io.Serializable


class ConnectDevice(private val context: Context,
                    private val address: String,
                    private val viewModel: BluetoothViewModel
) : Thread(), Serializable {

    override fun run() {
        try {
            if (viewModel.bluetoothSocket == null || viewModel.isConnected) {
                val bluetoothAdapter: BluetoothAdapter? = getSystemService(context, BluetoothManager::class.java)?.adapter
                val device: BluetoothDevice = bluetoothAdapter!!.getRemoteDevice(address)
                bluetoothAdapter.cancelDiscovery()
                viewModel.updateSocket(device)
                viewModel.bluetoothSocket!!.connect()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            viewModel.updateIsConnected()
        }
    }

    // Closes the client socket and causes the thread to finish.
    fun cancel() {
        try {
            viewModel.bluetoothSocket?.close()
        } catch (e: IOException) {
            Log.e(TAG, "Could not close the client socket", e)
        }
    }
}

