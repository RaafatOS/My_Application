package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://toiletmergency.cleverapps.io/"
class MainActivity : AppCompatActivity() {

    private val dataset = ToiletCollection()
    private val retrofit= Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val toiletService = retrofit.create(ToiletService::class.java)

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
    }

    private fun displayToiletsList() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(
            R.id.r_list_fragment,
            ToiletListFragment.newInstance(dataset.getAllToilets()))
    }
}