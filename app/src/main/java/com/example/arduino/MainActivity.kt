package com.example.arduino

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.activity.viewModels
import com.example.arduino.R.menu.menu_devices
import com.example.arduino.fragment.BluetoothDevicesFragment
import com.example.arduino.fragment.ControllerFragment

class MainActivity : AppCompatActivity(),
    BluetoothDevicesFragment.Callbacks {

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(menu_devices, menu)
        return true
    }

    override fun onBluetoothDeviceSelected(address: String) {
        val bltObject = ConnectDevice(this, address, viewModel)

        bltObject.run()
        if(viewModel.isConnected){
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