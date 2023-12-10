package com.example.myapplication

import java.io.Serializable

data class Toilet (
    val Commune: String,
    val Code_Postal: String,
    val PointGeo: PointGeo,
    val Id: String,
    val Longitude: String,
    val OpeningHours: String,
    val ImageURL: String ?= null,
    var isFavorite: Boolean ) : Serializable