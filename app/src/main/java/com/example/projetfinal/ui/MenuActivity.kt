package com.example.projetfinal.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projetfinal.data.LocalPreferences
import com.example.projetfinal.R
import com.example.projetfinal.adapter.MenuAdapter
import com.example.projetfinal.adapter.MenuItem
import com.example.projetfinal.databinding.ActivityMenuBinding

// Main activity
class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Bind ressources
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set parameters button action
        binding.parametersButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        // Set history recyclerview with generated items
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.menuRecyclerView.adapter = generateData()?.let {
            MenuAdapter(
                    it
            )
        }

        // Add separator decoration to recycler view
        binding.menuRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    // Generate data set for menu recycler view
    private fun generateData(): Array<MenuItem> {

        // The datas
        val data = arrayOf(

                MenuItem(getResources().getString(R.string.menu_distance), R.drawable.ic_baseline_directions_walk_24) {
                    val intent = Intent(this, DistanceActivity::class.java)
                    startActivity(intent)
                },

                MenuItem(getResources().getString(R.string.menu_history), R.drawable.ic_baseline_history_24) {
                    if (LocalPreferences.getInstance(this).isEmpty())
                        Toast.makeText(this, R.string.menu_history_toast, Toast.LENGTH_SHORT).show()
                    else{
                        val intent = Intent(this, HistoryActivity::class.java)
                        startActivity(intent)
                    }
                }
        )
        return data
    }

}