package com.example.georunner

import android.os.Bundle
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.example.georunner.databinding.ActivityMainBinding
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.example.georunner.databinding.ActivityMapBinding
import com.google.android.gms.maps.GoogleMap

class MapActivity : AppCompatActivity(),OnMapReadyCallback{
    private lateinit var binding: ActivityMapBinding

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var mapFragment = supportFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(@NonNull googleMap:GoogleMap){}

}