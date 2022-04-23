package com.example.arduino

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.arduino.fragment.BluetoothDevicesFragment
import com.example.arduino.fragment.ControllerFragment
import java.io.IOException

class MainActivity : AppCompatActivity(),
    BluetoothDevicesFragment.Callbacks,
    ControllerFragment.Callbacks
{

    private val viewModel: BluetoothViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (currentFragment == null) {
            val fragment = BluetoothDevicesFragment(this)
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }

    override fun onBluetoothDeviceSelected(address: String) {
        val bltObject = ConnectDevice(this, address, viewModel)

        bltObject.run()
        if(viewModel.isConnected){
            val fragment = ControllerFragment()

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun write(message: String) {
        viewModel.setupTransfer()
        try{
            viewModel.outa.write(message.toByteArray())
        } catch(e: IOException) {
            Log.e(TAG, "Error occurred when sending data", e)
        }
    }
}