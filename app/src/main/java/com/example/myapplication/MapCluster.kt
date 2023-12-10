package com.example.myapplication

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class MapCluster(val commune : String, val pointGeo : PointGeo) : ClusterItem {
    override fun getSnippet(): String {
        return ""
    }

    override fun getTitle(): String {
        return ""
    }

    override fun getPosition(): LatLng {
        return LatLng(pointGeo.lat, pointGeo.lon)
    }
}