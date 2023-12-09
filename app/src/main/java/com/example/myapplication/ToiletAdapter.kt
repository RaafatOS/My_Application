package com.example.myapplication

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide

import com.example.myapplication.placeholder.PlaceholderContent.PlaceholderItem

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class ToiletAdapter(private var Toilets: List<Toilet>) : RecyclerView.Adapter<ToiletViewHolder>() {

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
        holder.favbutton.setOnClickListener {
            item.isFavorite = !item.isFavorite
            // now we update the color of the button to reflect the new state
            if (item.isFavorite) {
                holder.favbutton.setColorFilter(holder.itemView.context.getColor(R.color.fav_yellow))
            } else {
                holder.favbutton.setColorFilter(holder.itemView.context.getColor(R.color.black))
            }
        }
    }

    override fun getItemCount(): Int = Toilets.size

}