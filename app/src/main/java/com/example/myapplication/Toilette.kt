package com.example.myapplication

import java.io.Serializable

data class Toilette(val Commune: String, val Code_Postal: String, val PointGeo: String,
                    val Id: String, val Longitude: String, val OpeningHours: List<String>): Serializable