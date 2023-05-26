package com.example.georunner

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.georunner.databinding.ActivityMapBinding
import com.example.georunner.room.User
import com.example.georunner.room.UserRoomRepository
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope



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
    private var distance: Int = 0


    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        user = intent.getSerializableExtra("USER_OBJECT") as User
        lifecycleScope.launch(Dispatchers.IO) {
            userRoomRepository = UserRoomRepository(applicationContext)
        }

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sFrag = SearchFragment(this)
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
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, this)
            currentlocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)!!
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

    override fun onLocationChanged(location: Location) {
    }

    override fun onProviderDisabled(provider: String) {}

    override fun onProviderEnabled(provider: String) {}

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}

}