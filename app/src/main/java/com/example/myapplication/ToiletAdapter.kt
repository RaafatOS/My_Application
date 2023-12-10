package com.example.myapplication

import android.graphics.Color
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide

import com.example.myapplication.placeholder.PlaceholderContent.PlaceholderItem
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class ToiletAdapter(private var Toilets: List<Toilet>) : RecyclerView.Adapter<ToiletViewHolder>() {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val toiletService = retrofit.create(ToiletService::class.java)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToiletViewHolder {
        val rowView = LayoutInflater.from(parent.context)
            .inflate(R.layout.toilet_record, parent, false)
        return ToiletViewHolder(rowView)
    }

    override fun onBindViewHolder(holder: ToiletViewHolder, position: Int) {
        val item = Toilets[position]
        Glide.with(holder.itemView.context)
            .load(item.ImageURL)
            .into(holder.imgid)
        holder.txvid.text = item.Id
        holder.txvcode.text = item.Code_Postal
        holder.txvcomune.text = item.Commune
        if (item.isFavorite) {
            holder.favbutton.setColorFilter(holder.itemView.context.getColor(R.color.fav_yellow))
        } else {
            holder.favbutton.setColorFilter(holder.itemView.context.getColor(R.color.black))
        }
        holder.favbutton.setOnClickListener {
            item.isFavorite = !item.isFavorite
            // now we update the color of the button to reflect the new state
            if (item.isFavorite) {
                holder.favbutton.setColorFilter(holder.itemView.context.getColor(R.color.fav_yellow))
            } else {
                holder.favbutton.setColorFilter(holder.itemView.context.getColor(R.color.black))
            }
            toiletService.updateToilet(item.Id).enqueue(object : Callback<JSONObject> {
                override fun onResponse(call: Call<JSONObject>, response: Response<JSONObject>) {
                }
                override fun onFailure(call: Call<JSONObject>, t: Throwable) {
                }
            })
        }
        // setting click listener for the whole row
        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, item.Id, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = Toilets.size

}