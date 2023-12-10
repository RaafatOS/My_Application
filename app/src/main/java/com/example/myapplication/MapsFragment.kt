package com.example.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.media.audiofx.BassBoost
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import java.util.*

//private const val ARG_TOILETTES = "param1"
class MapsFragment : Fragment(), OnMapReadyCallback {
    private var userLatitude: Double = 0.0
    private var userLongitude: Double = 0.0
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val permissionId = 2
    private lateinit var googleMap: GoogleMap
    private var toiletList: List<Toilet> = emptyList()
    private var mapClusters: List<MapCluster> = emptyList()

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
        this.googleMap = googleMap
        googleMap.uiSettings.isCompassEnabled = true
        googleMap.uiSettings.isMapToolbarEnabled = true
        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.uiSettings.isMyLocationButtonEnabled = true
        googleMap.setOnMyLocationButtonClickListener { getLocation(); true }
        val gardanne = LatLng(43.452277, 5.469722)
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(gardanne))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gardanne, 12f))
//        for (toilet in toiletList) {
//            val toiletLatLng = LatLng(toilet.PointGeo.lat, toilet.PointGeo.lon)
//            if(toilet.isFavorite)
//                googleMap.addMarker(MarkerOptions().position(toiletLatLng).title(toilet.Commune))?.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
//            else
//                googleMap.addMarker(MarkerOptions().position(toiletLatLng).title(toilet.Commune))
//        }
        getLocation()
        setUpClusterManager(googleMap)
    }
    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (requireActivity().let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            } == PackageManager.PERMISSION_GRANTED &&
            requireActivity().let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        requireActivity().let {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                permissionId
            )
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionId) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }
        }
    }

    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        val geocoder = this.context?.let { Geocoder(it, Locale.getDefault()) }
                        val list: List<Address> =
                            geocoder?.getFromLocation(location.latitude, location.longitude, 1) as List<Address>
                        userLatitude = list[0].latitude
                        userLongitude = list[0].longitude
                        googleMap.addMarker(MarkerOptions().position(LatLng(userLatitude, userLongitude)).title("Your location"))?.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(userLatitude, userLongitude), 14.0f))
                    }
                }
            } else {
               Toast.makeText(this.context, "Please turn on location", Toast.LENGTH_LONG).show()
            }
        } else {
            requestPermissions()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mFusedLocationClient = requireActivity().let {
            LocationServices.getFusedLocationProviderClient(
                it
            )
        }
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

    private fun setUpClusterManager(gMap : GoogleMap){
        val clusterManager = ClusterManager<MapCluster>(this.context, gMap)
        gMap.setOnCameraIdleListener(clusterManager)
        gMap.setOnMarkerClickListener(clusterManager)
        gMap.setOnInfoWindowClickListener(clusterManager)
        mapClusters = getAllClusters()
        clusterManager.addItems(mapClusters)
        clusterManager.cluster()
    }

    private fun getAllClusters(): List<MapCluster> {
        return toiletList.map { MapCluster(it.Commune, it.PointGeo) }
    }
}