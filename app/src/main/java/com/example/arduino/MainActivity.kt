package com.example.arduino

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import com.example.arduino.R.menu.menu_devices
import com.example.arduino.fragment.BluetoothDevicesFragment
import com.example.arduino.fragment.ControllerFragment
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(),
    BluetoothDevicesFragment.Callbacks {

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(menu_devices, menu)
        return true
    }

    override fun onBluetoothDeviceSelected(address: String) {
        val contextView = findViewById<View>(R.id.test)
        Snackbar.make(contextView, "$address connecting...", Snackbar.LENGTH_LONG).show()
        val bltObject = ConnectDevice(address)
        bltObject.run()
        if(connectSuccess){
            val args = Bundle().apply {
                putSerializable("bluetooth_object", bltObject)
            }
            val fragment = ControllerFragment().apply {
                arguments = args
            }

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}