package com.example.myapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FavoritesActivity : AppCompatActivity() {
    private val favDataset = ToiletCollection()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val toiletService = retrofit.create(ToiletService::class.java)
    private lateinit var recyclerView : RecyclerView
    private val adapter = ToiletAdapter(favDataset.getAllToilets())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        recyclerView = findViewById<RecyclerView>(R.id.toilet_fav_list_rv)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

        toiletService.getFavToilets().enqueue(object : Callback<List<Toilet>> {
                override fun onResponse(call: Call<List<Toilet>>,response: Response<List<Toilet>>) {
                    if (response.isSuccessful){
                        val toilets:List<Toilet> = response.body()!!
                        if (toilets != null) {
                            favDataset.addToilets(toilets)
                            Toast.makeText(this@FavoritesActivity, "gotFav" + favDataset.size(), Toast.LENGTH_SHORT).show()
                            adapter.refreshData(favDataset.getAllToilets())
                            adapter?.notifyDataSetChanged()
                        }
                    }
                }

                override fun onFailure(call: Call<List<Toilet>>, t: Throwable) {
                    Log.e("MainActivity", "WebService call failed")
                }
            }
        )

        findViewById<FloatingActionButton>(R.id.toilet_fav_list_fab).setOnClickListener {
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


}