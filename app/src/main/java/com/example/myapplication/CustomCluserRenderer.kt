package com.example.myapplication

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

class CustomClusterRenderer(
    context: Context?,
    map: GoogleMap,
    clusterManager: ClusterManager<MapCluster>
) : DefaultClusterRenderer<MapCluster>(context, map, clusterManager) {

    override fun onBeforeClusterItemRendered(item: MapCluster, markerOptions: MarkerOptions) {
        // Customize the appearance of the marker based on whether it's a favorite toilet
        if (item.isFavorite) {
            // Set a different color or any other customization for favorite toilets
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
        } else {
            // Default appearance for non-favorite toilets
            super.onBeforeClusterItemRendered(item, markerOptions)
        }
    }
}
