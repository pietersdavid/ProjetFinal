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


class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        // Set up binding
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.historyToolbar.setNavigationOnClickListener {
            finish()
        }

        binding.historyButtonErase.setOnClickListener {
            this.eraseHistory()
        }

        binding.historyRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.historyRecyclerView.adapter = generateData()?.let {
            HistoryAdapter(
                    it
            )
        }
        binding.historyRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun eraseHistory(){
        LocalPreferences.getInstance(this).reset()

        binding.historyRecyclerView.adapter = generateData()?.let {
            HistoryAdapter(
                    it
            )
        }
        binding.historyRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    private fun generateData(): Array<HistoryItem> {

        var data: Array<HistoryItem> = arrayOf()

        if(LocalPreferences.getInstance(this).getHistory() != null){

            var history = LocalPreferences.getInstance(this).getHistory()

            if (history != null) {
                for (location in history){

                    val latlongPosition = location.split(",".toRegex()).toTypedArray()
                    val latitude = latlongPosition[0].toDouble()
                    val longitude = latlongPosition[1].toDouble()

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
        }

        return data
    }
}