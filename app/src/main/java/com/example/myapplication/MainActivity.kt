package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import com.google.android.gms.maps.OnMapReadyCallback

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private val mapTab: TabItem by lazy {
        findViewById(R.id.tab_map)
    }
    private val tab: TabLayout by lazy {
        findViewById(R.id.tab_ly)
    }
    private val mapFragment: MapsFragment by lazy {
        MapsFragment()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    1 -> displayMapFragment()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }


    private fun displayMapFragment() {
        val transaction = supportFragmentManager.beginTransaction()

        // Use the same instance of MapsFragment
        transaction.replace(
            R.id.a_const,
            mapFragment
        )

        transaction.commit()
    }

    override fun onMapReady(p0: GoogleMap) {
        val gardanne = LatLng(43.4525982, 5.4717363)

        p0.addMarker(
            MarkerOptions().position(gardanne).title("Gardanne").snippet("QG des ISMINs")
        )

        p0.moveCamera(CameraUpdateFactory.zoomTo(14f))
        p0.moveCamera(CameraUpdateFactory.newLatLng(gardanne))
    }
}