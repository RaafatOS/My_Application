package com.example.myapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.floatingactionbutton.FloatingActionButton

const val resultat = "toilets"

class AddToiletActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_toilet)

        val addBtn = findViewById<Button>(R.id.add_btn)
        addBtn.setOnClickListener {

            val Commune = findViewById<EditText>(R.id.add_commune).text.toString()
            val CodeP = findViewById<EditText>(R.id.add_codeP).text.toString()
            val lat = findViewById<EditText>(R.id.add_lat).text.toString().toDouble()
            val lon = findViewById<EditText>(R.id.add_lon).text.toString().toDouble()
            val openingHours = findViewById<EditText>(R.id.add_opening_hours).text.toString()

            val toiletToAdd =Toilet(Commune, CodeP, PointGeo(lon,lat), (lon*lat).toString(), lon.toString(), openingHours, null, false)
            val returnIntent = Intent()
            returnIntent.putExtra(resultat, toiletToAdd)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }
}