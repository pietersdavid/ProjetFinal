package com.example.projetfinal.ui

import android.content.Intent
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projetfinal.adapter.HistoryItem
import com.example.projetfinal.data.LocalPreferences
import com.example.projetfinal.R
import com.example.projetfinal.adapter.HistoryAdapter
import com.example.projetfinal.databinding.ActivityHistoryBinding
import java.util.*

// Activity to display location history
class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        // Bind ressources
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set return button of the toolbar
        binding.historyToolbar.setNavigationOnClickListener {
            finish()
        }

        // Set action for reset history button
        binding.historyButtonErase.setOnClickListener {
            this.eraseHistory()
        }

        // Set history recyclerview with generated items
        binding.historyRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.historyRecyclerView.adapter = generateData()?.let {
            HistoryAdapter(
                    it
            )
        }

        // Add separator decoration to recycler view
        binding.historyRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    // Function to erase the history
    private fun eraseHistory(){
        // Reset history in shared preferences
        LocalPreferences.getInstance(this).reset()

        // Regenerate and reload recycler view
        binding.historyRecyclerView.adapter = generateData()?.let {
            HistoryAdapter(
                    it
            )
        }
        binding.historyRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    // Generate data set for history recycler view
    private fun generateData(): Array<HistoryItem> {

        var data: Array<HistoryItem> = arrayOf()

        // Get history
        var history = LocalPreferences.getInstance(this).getHistory()

        // Check that history is not null
        if (history != null) {
            for (location in history){

                // Convert string position by spliting
                val latlongPosition = location.split(",".toRegex()).toTypedArray()
                // Get latitude and longitude
                val latitude = latlongPosition[0].toDouble()
                val longitude = latlongPosition[1].toDouble()

                // Get the address
                val geocoder = Geocoder(this, Locale.getDefault())
                val results = geocoder.getFromLocation(latitude, longitude, 1)
                val adderss = results[0].getAddressLine(0)

                // Add to the array
                data += (HistoryItem(adderss) {
                    val gmmIntentUri = Uri.parse("geo:" + location)
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    startActivity(mapIntent)
                })

            }
        }

        return data
    }
}