package com.example.projetfinal.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projetfinal.R
import com.example.projetfinal.adapter.SettingsItem
import com.example.projetfinal.adapter.ParametersAdapter
import com.example.projetfinal.databinding.ActivityParameterBinding

class ParametersActivity : AppCompatActivity() {

    /* CHANGER PARAMETER TO PARAMETERS*/

    private lateinit var binding: ActivityParameterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parameter)

        binding = ActivityParameterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.parametersToolbar.setNavigationOnClickListener {
            finish()
        }

        binding.parametersRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.parametersRecyclerView.adapter = generateData()?.let {
            ParametersAdapter(
                    it
            )
        }
        binding.parametersRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun generateData(): Array<SettingsItem> {

        val data = arrayOf(

                SettingsItem("Settings", R.drawable.ic_baseline_settings_24) {
                    Toast.makeText(this, "Bouton settings", Toast.LENGTH_SHORT).show()

                },

                SettingsItem("Paramètres de localisation", R.drawable.location_parameters) {
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                },

                SettingsItem("Position de l'ESEO", R.drawable.location) {
                    val gmmIntentUri = Uri.parse("geo:47.4932702,-0.5522239")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    startActivity(mapIntent)

                },

                SettingsItem("Site de l'ESEO", R.drawable.web_site) {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://eseo.fr/"))
                    startActivity(browserIntent)
                },

                SettingsItem("Nous contacter", R.drawable.e_mail) {
                    val i = Intent(Intent.ACTION_SENDTO)
                    i.data = Uri.parse("mailto:david.pieters@reseau.eseo.fr")
                    i.putExtra(Intent.EXTRA_SUBJECT, "Feedback/Support")
                    startActivity(Intent.createChooser(i, "Send feedback"))
                }
        )
        return data
    }
}