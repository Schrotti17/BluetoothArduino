package com.example.arduino.fragment

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.arduino.BluetoothViewModel
import com.example.arduino.R
import com.example.arduino.databinding.FragmentBluetoothBinding
import kotlin.collections.ArrayList


class BluetoothDevicesFragment(context: Context) : Fragment() {

    interface Callbacks{
        fun onBluetoothDeviceSelected(address: String)

        //fun updateUI()

        //in connectDevice
        //fun cancelConnection()

    }
    private var _binding: FragmentBluetoothBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView

    private var callbacks: Callbacks? = null
    private var bluetoothAdapter: BluetoothAdapter? = null
    lateinit var address1: String
    private val bluetoothManager: BluetoothManager? = getSystemService(context, BluetoothManager::class.java)

    private val viewModel: BluetoothViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(requireContext().packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)) {
            bluetoothAdapter = bluetoothManager?.adapter
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBluetoothBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerM
        recyclerView.adapter = ItemAdapter(viewModel.listItems)
        recyclerView.setHasFixedSize(true)
        refresh()
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private inner class ItemAdapter(private var dataset: ArrayList<BluetoothDevice>): RecyclerView.Adapter<ItemViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val adapterLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false)
            return ItemViewHolder(adapterLayout)
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            val device = dataset[position]
            holder.onBind(device)
        }

        override fun getItemCount(): Int {
            return dataset.size
        }
    }

    private inner class ItemViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener{
        private lateinit var device: BluetoothDevice
        val name: TextView = view.findViewById(R.id.name)
        val address: TextView = view.findViewById(R.id.address)
        init{
            itemView.setOnClickListener(this)
        }

        fun onBind(device: BluetoothDevice){
            this.device = device
            name.text = device.name
            address.text = device.address
        }
        override fun onClick(p0: View?) {
            val position = adapterPosition
            address1 = viewModel.listItems[position].address
            callbacks?.onBluetoothDeviceSelected(address1)
        }
    }

    private fun refresh(){
        viewModel.listItems.clear()
        viewModel.getPairedDevices(bluetoothAdapter)
        val pairedDevices = viewModel.pairedDevices
        if(bluetoothAdapter!=null){
            pairedDevices?.forEach{
                if(it.type != BluetoothDevice.DEVICE_TYPE_LE){
                    viewModel.updateListItems(it)
                }
            }
        }
        //Collections.sort(listItems, BluetoothDevicesFragment::compareTwoDevices)
    }

    /*private fun compareTwoDevices(a: BluetoothDevice, b: BluetoothDevice): Int {
        val aValid: Boolean = a.name !=null && a.address.isNotEmpty()
        val bValid: Boolean = b.name !=null && b.address.isNotEmpty()
        if(aValid && bValid){
            val ret = a.name.compareTo(b.name)
            if(ret != 0) return ret
            return a.address.compareTo(b.address)
        }
        if(aValid) return -1
        if(bValid) return +1
        return a.address.compareTo(b.address)
    }*/
}

/*override fun onResume() {
    super.onResume()
    /*if(bluetoothAdapter == null){
        text.text = getString(R.string.notSupported)
    }
    else if(!bluetoothAdapter!!.isEnabled){
        text.text = getString(R.string.disabled)
    }
    else{
        text.text = getString(R.string.notFound)
    }*/
    refresh()
}*/