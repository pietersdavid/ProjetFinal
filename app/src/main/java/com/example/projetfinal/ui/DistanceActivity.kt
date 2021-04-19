package com.example.projetfinal.ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.projetfinal.data.LocalPreferences
import com.example.projetfinal.R
import com.example.projetfinal.databinding.ActivityDistanceBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource

// Activity to check distance from ESEO
class DistanceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDistanceBinding
    val PERMISSION_REQUEST_LOCATION = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var currentLatLong = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_distance)

        // Bind ressources
        binding = ActivityDistanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set return button of the toolbar
        binding.distanceToolbar.setNavigationOnClickListener {
            finish()
        }

        // Set button to add position to history
        binding.distanceSaveButton.setOnClickListener {
            // Add to history
            LocalPreferences.getInstance(this).addToHistory(currentLatLong)
            // Give the information to the user
            Toast.makeText(this, R.string.distance_add_toast, Toast.LENGTH_SHORT).show()
        }

        // Check for permissions
        if (hasPermission())
            getLocation()
        else
            requestPermission()

    }

    // Check for permission
    fun hasPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // Request the permission
    private fun requestPermission() {
        if (!hasPermission()) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUEST_LOCATION)
        } else {
            getLocation()
        }
    }

    // On permission request responce
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSION_REQUEST_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission obtenue, Nous continuons la suite de la logique.
                    getLocation()
                } else {
                    // TODO
                    // Permission non accepté, expliqué ici via une activité ou une dialog pourquoi nous avons besoin de la permission
                }
                return
            }
        }
    }

    // Function to get user location
    private fun getLocation() {
        // Check if localisation is granted
        if (hasPermission()) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return
            }
            // Get the current location
            fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY, CancellationTokenSource().token)
                    .addOnSuccessListener { computeDistance(it) }
                    .addOnFailureListener {
                        // Remplacer par un vrai bon message
                        Toast.makeText(this, R.string.distance_impossible_location, Toast.LENGTH_SHORT).show()
                    }
        }
    }

    // Compute and display the distance from ESEO to the user
    private fun computeDistance(location: Location){

        // Set ESEO position
        val latEseo = 47.4932702
        val longEseo = -0.5522239
        val eseoPosition = Location("")
        eseoPosition.setLatitude(latEseo)
        eseoPosition.setLongitude(longEseo)

        // Set user position
        val currentPosition = Location("")
        currentPosition.setLatitude(location.latitude)
        currentPosition.setLongitude(location.longitude)

        // Set currentLatLong value (used for history)
        currentLatLong = location.latitude.toString() + ',' + location.longitude.toString()

        // Compute distance
        val distanceInMeters: Float = eseoPosition.distanceTo(currentPosition)
        // If distance > 1000 -> Display in km (metric system)
        if (distanceInMeters > 1000)
            binding.distanceText.setText((distanceInMeters/1000).toString() + " km")
        // If distance < 1000 -> Display in metters
        else if (distanceInMeters < 1000 && distanceInMeters > 300)
            binding.distanceText.setText(distanceInMeters.toString() + " m" )
        // If distance <= 300m -> User is at ESEO
        else
            binding.distanceText.setText("Vous êtes à l'eseo !")

    }
}