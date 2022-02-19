package com.rivas.testandroiddeveloper.ui.main.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.rivas.testandroiddeveloper.databinding.FragmentMapBinding

class MapFragment : Fragment() {

    private lateinit var mapViewModel: MapViewModel
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mapViewModel =
            ViewModelProvider(this)[MapViewModel::class.java]

        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        mapViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}