package com.example.georunner

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.georunner.databinding.ActivityMapBinding
import com.example.georunner.room.User
import com.example.georunner.room.UserRoomRepository
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.location.LocationResult
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.pow
import kotlin.math.round


class MapActivity : AppCompatActivity(),OnMapReadyCallback,com.google.android.gms.location.LocationListener {
    private lateinit var binding: ActivityMapBinding
    private lateinit var map: GoogleMap
    private val REQUEST_LOCATION_PERMISSION = 1
    private lateinit var mapFragment : SupportMapFragment
    lateinit var currentlocation : Location
    private lateinit var userRoomRepository: UserRoomRepository
    private lateinit var user: User
    private var timeSpentSeconds: Int = 0
    private var timeSpentMinutes: Int = 0
    private var timeSpentHours: Int=0
    private lateinit var menuBarToggle: ActionBarDrawerToggle
    private var polyline: Polyline? = null
    var isRunning = false
    private var distance: Int = 0
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var currentGoal : LatLng
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

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.search_container, sFrag)
            commit()
        }
        mapFragment = supportFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment
        mapFragment?.getMapAsync(this)
        setupMenuDrawer(user)
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

    fun getDistance(distance:Int){
        this.distance=distance
    }

    fun addDistanceToUser(){
        lifecycleScope.launch(Dispatchers.IO){
            user.distanceCovered+=distance
            userRoomRepository.userDao.updateUser(user)
        }
    }
    fun setTimeSpent(seconds:Int,minuts:Int,hours:Int){
        timeSpentSeconds=seconds
        timeSpentMinutes=minuts
        timeSpentHours=hours
    }

    fun addTimeSpentToUser(){
        lifecycleScope.launch(Dispatchers.IO) {
            user.timeSpentRunningSeconds+=timeSpentSeconds
            if(user.timeSpentRunningSeconds>=60){
                user.timeSpentRunningSeconds-=user.timeSpentRunningSeconds-60
                user.timeSpentRunningMinutes++
            }
            user.timeSpentRunningMinutes+=timeSpentMinutes
            if(user.timeSpentRunningMinutes>=60){
                user.timeSpentRunningMinutes-=user.timeSpentRunningMinutes-60
                user.timeSpentRunningHours++
            }
            user.timeSpentRunningHours+=timeSpentHours
            userRoomRepository.userDao.updateUser(user)
        }
    }

    fun addActivity(){
        lifecycleScope.launch(Dispatchers.IO) {
            //val user = userRoomRepository.userDao.getUserByUsername(username)
            val activityData = ActivityData(user.gamesPlayed, distance, timeSpentHours, timeSpentMinutes, timeSpentSeconds)
            val updatedActivities = user.activities.toMutableList()
            updatedActivities.add(activityData)
            userRoomRepository.updateActivities(user.id, updatedActivities)
        }
    }

    fun calculateScore(): Int {
        return (((distance+1)%10)%(timeSpentMinutes+1))+1
    }

    fun addScoreToUser(score :Int){
        lifecycleScope.launch(Dispatchers.IO){
            user.score+=score
        }
    }

    fun increaseAmountOfGamesPlayed(){
        lifecycleScope.launch(Dispatchers.IO){
            user.gamesPlayed++
            userRoomRepository.userDao.updateUser(user)
        }
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
            //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 0f, this)
            val locationRequest = LocationRequest.create().apply {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                interval = 1000 // Update interval in milliseconds
            }
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                this,
                Looper.getMainLooper() // Use the main thread looper to receive updates on the main thread
            )
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
    fun clearMap(){
        map.clear()
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
            currentGoal = latLng
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
    fun hasReachedGoal(): Boolean {
        val distanceResults = FloatArray(1)
        Location.distanceBetween(currentGoal.latitude,currentGoal.longitude,currentlocation.latitude,currentlocation.longitude,distanceResults)
        Snackbar.make(binding.root, distanceResults[0].toString(), Snackbar.LENGTH_LONG).setAction("Action", null).show()
        return distanceResults[0] <= 15
    }

    fun drawLine() {
        val backgroundThread = Thread {
            while (isRunning) {
                val newPoint = LatLng(currentlocation.latitude, currentlocation.longitude)
                runOnUiThread {

                    val points: MutableList<LatLng> = polyline?.points?.toMutableList() ?: ArrayList()
                    if (polyline != null) {
                        polyline!!.remove()
                    }

                    points.add(newPoint)
                    val polylineOptions = PolylineOptions()
                        .addAll(points)
                        .color(Color.RED)
                        .width(5f)
                    polyline = map.addPolyline(polylineOptions)
                }
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        backgroundThread.start()
    }


    fun calculateTotalDistance(): Double {
        var totalDistance = 0.0
        val points: MutableList<LatLng> = polyline?.points?.toMutableList() ?: ArrayList()
        if(points != null && points .size > 2) {
            for (i in 0 until points.size - 1) {
                val startLatLng = points[i]
                val endLatLng = points[i + 1]

                val startLat = Math.toRadians(startLatLng.latitude)
                val startLng = Math.toRadians(startLatLng.longitude)
                val endLat = Math.toRadians(endLatLng.latitude)
                val endLng = Math.toRadians(endLatLng.longitude)

                val dLat = endLat - startLat
                val dLng = endLng - startLng

                val a =
                    Math.sin(dLat / 2).pow(2) + Math.cos(startLat) * Math.cos(endLat) * Math.sin(
                        dLng / 2
                    ).pow(2)
                val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

                val earthRadius = 6371
                val distance = earthRadius * c

                totalDistance += distance
            }
        }

        return round(totalDistance*1000)
    }
    override fun onLocationChanged(location: Location) {
        currentlocation = location
    }
    private fun setupMenuDrawer(user: User) {
        menuBarToggle = ActionBarDrawerToggle(this,binding.drawerLayoutMap, R.string.menu_open, R.string.menu_close)
        binding.drawerLayoutMap.addDrawerListener(menuBarToggle)
        menuBarToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.navViewMap.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.HomeScreen -> switchToMap(user, "home")
                R.id.runHistory -> switchToMap(user, "recyclerview")
            }
            true
        }
    }
    private fun switchToMap(user: User, a : String){
        if(a == "home"){
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("USER_OBJECT", user)
            startActivity(intent)
        }
        else if(a == "recyclerview"){
            val intent = Intent(this, RecyclerViewActivity::class.java)
            intent.putExtra("USER_OBJECT", user)
            startActivity(intent)
        }
    }
}