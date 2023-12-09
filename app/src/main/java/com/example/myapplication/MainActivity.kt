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
import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://toiletmergency.cleverapps.io/"
class MainActivity : AppCompatActivity() , OnMapReadyCallback {

    private val dataset = ToiletCollection()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val toiletService = retrofit.create(ToiletService::class.java)
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

        toiletService.getAllToilets().enqueue(object : Callback<List<Toilet>> {
            override fun onResponse(call: Call<List<Toilet>>, response: Response<List<Toilet>>) {
                Log.println(Log.INFO, "TAG", response.body().toString())
                if (response.isSuccessful) {
                    val toilets:List<Toilet> = response.body()!!
                    dataset.addToilets(toilets)
                    displayToiletsList()
                }
            }

            override fun onFailure(call: Call<List<Toilet>>, t: Throwable) {
                Log.e("TAG", "onFailure: ", t)

                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })

        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> displayToiletsList()
                    1 -> displayMapFragment()
                    2 -> displauInfoFragment()
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
        mapFragment.setToiletList(dataset.getAllToilets())
        // Use the same instance of MapsFragment
        transaction.replace(
            R.id.a_const,
            mapFragment
        )
        transaction.commit()
    }

    private fun displauInfoFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(
            R.id.a_const,
            InfoFragment()
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

    private fun displayToiletsList() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(
            R.id.r_list_fragment,
            ToiletListFragment.newInstance(dataset.getAllToilets()))
    }
}