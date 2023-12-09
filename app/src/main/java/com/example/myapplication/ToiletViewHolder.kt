package com.example.myapplication

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ToiletViewHolder(rootView : View) : RecyclerView.ViewHolder(rootView) {
    val txvid = rootView.findViewById<TextView>(R.id.r_id_txt)
    val txvcode = rootView.findViewById<TextView>(R.id.r_codeP_txt)
    val txvcomune = rootView.findViewById<TextView>(R.id.r_commune_txt)
}