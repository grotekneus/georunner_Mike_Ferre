package com.example.georunner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.model.LatLng
import java.lang.Math.*

class SearchFragment() : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        val button = view.findViewById<Button>(R.id.btnSearch)
        button.setOnClickListener {
            val latlng = getRandomLocation((activity as MapActivity))
            (activity as MapActivity).placeMarker(latlng)
            (activity as MapActivity).isRunning = true


            val newfragment = InfoFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.search_container, newfragment)
                .addToBackStack(null)
                .commit()

        }
        return view
    }

    fun getRandomLocation(mapActivity: MapActivity): LatLng {
        if(mapActivity.currentlocation== null){
            return LatLng(0.0, 0.0)
        }
        mapActivity.currentlocation


        val text = view?.findViewById<EditText>(R.id.edtDistance)
        val distance = text?.text.toString().toIntOrNull()

        val earthRadius = 6371000.0 // radius of the earth in meters
        if(distance == null) {
            return LatLng(mapActivity.currentlocation.latitude,mapActivity.currentlocation.longitude)
        }
            val distanceRadians = distance / earthRadius

            val startLatRadians = toRadians(mapActivity.currentlocation.latitude)
            val startLonRadians = toRadians(mapActivity.currentlocation.longitude)


            val randomBearing = toRadians(random() * 360)
            val endLatRadians = asin(
                sin(startLatRadians) * cos(distanceRadians) +
                        cos(startLatRadians) * sin(distanceRadians) * cos(randomBearing)
            )

            val endLonRadians = startLonRadians + atan2(
                sin(randomBearing) * sin(distanceRadians) * cos(startLatRadians),
                cos(distanceRadians) - sin(startLatRadians) * sin(endLatRadians)
            )

            val endLatDegrees = toDegrees(endLatRadians)
            val endLonDegrees = toDegrees(endLonRadians)

        return LatLng(endLatDegrees,endLonDegrees)
    }

}