package com.example.projetfinal.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.projetfinal.data.LocalPreferences
import com.example.projetfinal.R
import com.example.projetfinal.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Set up binding
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.parametersButton.setOnClickListener {
            val intent = Intent(this, ParametersActivity::class.java)
            startActivity(intent)
        }

        binding.historyButton.setOnClickListener {
            if (LocalPreferences.getInstance(this).isEmpty())
                Toast.makeText(this, "Il faut d'abord enregistrer une position !", Toast.LENGTH_SHORT).show()
            else{
                val intent = Intent(this, HistoryActivity::class.java)
                startActivity(intent)
            }
        }

        binding.distanceButton.setOnClickListener {
            val intent = Intent(this, DistanceActivity::class.java)
            startActivity(intent)
        }

    }
}