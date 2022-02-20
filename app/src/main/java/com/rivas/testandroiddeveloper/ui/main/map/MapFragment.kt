package com.rivas.testandroiddeveloper.ui.main.map

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.rivas.testandroiddeveloper.R
import com.rivas.testandroiddeveloper.data.LocationFirestore
import com.rivas.testandroiddeveloper.databinding.FragmentMapBinding
import com.rivas.testandroiddeveloper.repository.room.movie.MovieRepository
import com.rivas.testandroiddeveloper.utils.extensions.observer
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import java.util.ArrayList
import javax.inject.Inject
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng

import com.google.android.gms.maps.model.MarkerOptions
import com.rivas.testandroiddeveloper.AndroidApp


class MapFragment : DaggerFragment(), OnMapReadyCallback {

    @Inject
    lateinit var mapViewModel: MapViewModel

    @Inject
    lateinit var mapRepository: MovieRepository

    private var _binding: FragmentMapBinding? = null

    private val binding get() = _binding!!

    private lateinit var mMap: GoogleMap


    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        createObservers()
        setupTitle()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
        return binding.root
    }

    private fun setupTitle() {
        requireActivity().setTitle(R.string.title_map)
    }

    private fun createObservers() {
        locationsObserver()
    }

    private fun locationsObserver() {
        mapViewModel.listLocationsLiveData.observer(viewLifecycleOwner, {
            createMarkers(it)
        })
    }

    private fun createMarkers(locations: ArrayList<LocationFirestore>) {
        for(location in locations){
            mMap.addMarker(
                MarkerOptions()
                    .position(LatLng(location.latitude!!, location.longitude!!))
                    .title(location.date)
            )
            if(locations.last()==location)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude!!, location.longitude!!),
                    12.0F
                ))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mapViewModel.loadLocations()
    }

}