package com.example.georunner

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.georunner.databinding.ActivityMapBinding
import com.example.georunner.room.User
import com.example.georunner.room.UserRoomRepository
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MapActivity : AppCompatActivity(),OnMapReadyCallback,android.location.LocationListener {
    private lateinit var binding: ActivityMapBinding
    private lateinit var map: GoogleMap
    private val REQUEST_LOCATION_PERMISSION = 1
    private lateinit var mapFragment : SupportMapFragment
    private lateinit var locationManager: LocationManager
    lateinit var currentlocation : Location
    private lateinit var userRoomRepository: UserRoomRepository
    private lateinit var user: User

    private var timeSpentSeconds: Int = 0
    private var timeSpentMinutes: Int = 0
    private var timeSpentHours: Int=0
    private var polyline: Polyline? = null
    var isRunning = false
    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sFrag = SearchFragment()

        user = intent.getSerializableExtra("USER_OBJECT") as User
        lifecycleScope.launch(Dispatchers.IO) {
            userRoomRepository = UserRoomRepository(applicationContext)
        }

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        // Request location updates


        supportFragmentManager.beginTransaction().apply {
            replace(R.id.search_container, sFrag)
            commit()
        }
        mapFragment = supportFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment
        mapFragment?.getMapAsync(this)

    }

    fun getUser(): User {
        return user
    }

    fun increaseScoreBy10(){
        lifecycleScope.launch(Dispatchers.IO) {
            user.score += 10
            user.distanceCovered
            userRoomRepository.userDao.updateUser(user)
        }
        Snackbar.make(binding.root, "score is"+user.score, Snackbar.LENGTH_LONG).setAction("Action", null).show()

    }

    fun getDistance(){

    }

    fun addDistanceToUser(){

    }
    fun setTimeSpent(seconds:Int,minuts:Int,hours:Int){
        timeSpentSeconds=seconds
        timeSpentMinutes=minuts
        timeSpentHours=hours
    }

    fun addTimeSpentToUser(){
        lifecycleScope.launch(Dispatchers.IO) {
            user.timeSpentRunning+=timeSpentSeconds
            userRoomRepository.userDao.updateUser(user)
        }
    }

    fun calculateScore(){

    }

    fun increaseAmountOfGamesPlayed(){

    }
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        checkPermissions()


    }
    fun checkPermissions(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            map.isMyLocationEnabled = true
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 0f, this)
            //currentlocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)!!
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf<String>(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                REQUEST_LOCATION_PERMISSION
            )

        }
    }
    fun placeMarker(latLng: LatLng){
        if(LatLng(0.0, 0.0) == latLng){
            Snackbar.make(binding.root, "location not initialised", Snackbar.LENGTH_LONG).setAction("Action", null).show()
        }
        if(map != null) {
            map.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title("Destination")
            )
            map.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkPermissions()
            } else {
                // Permission denied
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
    /*fun startRouteDrawing() {
        val backgroundThread = Thread {
        }
        backgroundThread.start()
    }*/
    fun drawLine() {
        val backgroundThread = Thread {
            while (isRunning) {
                // Perform your background task here
                    // Create a new point using thecurrent location
                val newPoint = LatLng(currentlocation.latitude, currentlocation.longitude)

                    // Execute the UI-related code on the main thread
                runOnUiThread {
                    if(polyline!= null) {
                        Snackbar.make(binding.root, polyline?.points?.get(polyline!!.points.lastIndex).toString(), Snackbar.LENGTH_LONG).setAction("Action", null).show()
                        //Log.i("thread", polyline?.points?.get(polyline!!.points.lastIndex).toString())
                    }
                    val points: MutableList<LatLng> = polyline?.points?.toMutableList() ?: ArrayList()
                    if (polyline != null) {
                        // Remove the existing polyline
                        polyline!!.remove()
                    }
                    // Add the new point to the existing polyline or create a new one

                    points.add(newPoint)
                    // Create a newpolyline with the updated points
                    val polylineOptions = PolylineOptions()
                        .addAll(points)
                        .color(Color.RED)
                        .width(5f)
                    polyline = map.addPolyline(polylineOptions)
                }
                try {
                    Thread.sleep(2000) // Adjust the sleep duration as needed
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        backgroundThread.start()
    }

    override fun onLocationChanged(location: Location) {
        //snackbar.make(binding.root, "location changed", Snackbar.LENGTH_LONG).setAction("Action", null).show()
        currentlocation = location

    }

    override fun onProviderDisabled(provider: String) {}

    override fun onProviderEnabled(provider: String) {}

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}

}