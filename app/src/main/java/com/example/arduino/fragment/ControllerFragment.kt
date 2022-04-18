package com.example.arduino.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.arduino.databinding.FragmentControllerBinding
import java.io.Serializable

class ControllerFragment : Fragment(), Serializable{

    interface Callbacks{
        fun write(message: String)
        //fun cancelConnection() //Implementation missing!
    }

    private var _binding: FragmentControllerBinding? = null
    private val binding
        get() = _binding!!

    private var callbacks: Callbacks? = null
    private lateinit var forward: ImageView
    private lateinit var left: ImageView
    private lateinit var right: ImageView
    private lateinit var backwards: ImageView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentControllerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        forward = binding.forwards
        left = binding.left
        right = binding.right
        backwards = binding.backwards

        forward.setOnClickListener { callbacks?.write("1") }
        left.setOnClickListener { callbacks?.write("2") }
        right.setOnClickListener { callbacks?.write("3") }
        backwards.setOnClickListener {callbacks?.write("4")}
    }
}
