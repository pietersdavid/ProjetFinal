package com.example.projetfinal.ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
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

class DistanceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDistanceBinding
    val PERMISSION_REQUEST_LOCATION = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var currentLatLong = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_distance)

        binding = ActivityDistanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.distanceToolbar.setNavigationOnClickListener {
            finish()
        }

        binding.distanceSaveButton.setOnClickListener {
            LocalPreferences.getInstance(this).addToHistory(currentLatLong)
        }

        if (hasPermission())
            getLocation()
        else
            requestPermission()

    } private fun hasPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        if (!hasPermission()) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUEST_LOCATION)
        } else {
            getLocation()
        }
    }

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

    private fun getLocation() {
        if (hasPermission()) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY, CancellationTokenSource().token)
                    .addOnSuccessListener { geoCode(it) }
                    .addOnFailureListener {
                        // Remplacer par un vrai bon message
                        Toast.makeText(this, "Localisation impossible", Toast.LENGTH_SHORT).show()
                    }

        }
    }

    private fun geoCode(location: Location){
        val latEseo = 47.4932702
        val longEseo = -0.5522239

        val eseoPosition = Location("")
        eseoPosition.setLatitude(latEseo)
        eseoPosition.setLongitude(longEseo)

        val currentPosition = Location("")
        currentPosition.setLatitude(location.latitude)
        currentPosition.setLongitude(location.longitude)

        currentLatLong = location.latitude.toString() + ',' + location.longitude.toString()
        Log.d("Heyyyyyyyy>", currentLatLong)

        val distanceInMeters: Float = eseoPosition.distanceTo(currentPosition)
        if (distanceInMeters > 1000)
            binding.distanceText.setText((distanceInMeters/1000).toString() + " km")
        else if (distanceInMeters < 1000 && distanceInMeters > 300)
            binding.distanceText.setText(distanceInMeters.toString() + " m" )
        else
            binding.distanceText.setText("Vous êtes à l'eseo !")

    }

}