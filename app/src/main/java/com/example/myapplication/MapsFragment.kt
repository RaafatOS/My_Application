package com.example.myapplication

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

//private const val ARG_TOILETTES = "param1"
class MapsFragment : Fragment(), OnMapReadyCallback {
    private lateinit var googleMap: GoogleMap
    private var toiletList: List<Toilet> = emptyList()

    fun setToiletList(toilets: List<Toilet>) {
        toiletList = toilets
    }
    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val gardanne = LatLng(43.452277, 5.469722)
        googleMap.addMarker(MarkerOptions().position(gardanne).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(gardanne))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gardanne, 12f))
        for (toilet in toiletList) {
            val toiletLatLng = LatLng(toilet.PointGeo.lat, toilet.PointGeo.lon)
            googleMap.addMarker(MarkerOptions().position(toiletLatLng).title(toilet.OpeningHours))
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
    override fun onMapReady(gMap: GoogleMap) {
        googleMap = gMap

        // Add markers for each toilet in the list
        for (toilet in toiletList) {
            val toiletLatLng = LatLng(toilet.PointGeo.lat, toilet.PointGeo.lon)
            googleMap.addMarker(MarkerOptions().position(toiletLatLng).title(toilet.OpeningHours))
        }

        // Center the camera based on the first toilet in the list
        if (toiletList.isNotEmpty()) {
            val firstToilet = toiletList[0]
            val firstToiletLatLng = LatLng(firstToilet.PointGeo.lat, firstToilet.PointGeo.lon)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstToiletLatLng, 10f))
        }
    }
}