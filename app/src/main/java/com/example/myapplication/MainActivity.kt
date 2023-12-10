package com.example.myapplication

import android.content.Intent
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
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
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
    private lateinit var toggle: ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val drawerLayout: DrawerLayout = findViewById(R.id.a_main_fragment)
        val navView: NavigationView = findViewById(R.id.nav_view)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_fav -> {
                    val intent = Intent(this, FavoritesActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_refresh -> {
                    Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    private fun displayMapFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        mapFragment.setToiletList(dataset.getAllToilets())
        // Use the same instance of MapsFragment
        transaction.replace(
            R.id.a_const,
            mapFragment
        )
        //transaction.addToBackStack("map")
        transaction.commit()
    }

    private fun displauInfoFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(
            R.id.a_const,
            InfoFragment()
        )
        //transaction.addToBackStack("info")
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
            R.id.a_const,
            ToiletListFragment.newInstance(dataset.getAllToilets()))
        fragmentTransaction.commit()
    }
}