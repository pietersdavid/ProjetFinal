package com.example.projetfinal.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projetfinal.R
import com.example.projetfinal.adapter.SettingsAdapter
import com.example.projetfinal.adapter.SettingsItem
import com.example.projetfinal.databinding.ActivityParameterBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityParameterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parameter)

        // Bind ressources
        binding = ActivityParameterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set return button of the toolbar
        binding.parametersToolbar.setNavigationOnClickListener {
            finish()
        }

        // Set history recyclerview with generated items
        binding.parametersRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.parametersRecyclerView.adapter = generateData()?.let {
            SettingsAdapter(
                    it
            )
        }

        // Add separator decoration to recycler view
        binding.parametersRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    // Generate data set for settings recycler view
    private fun generateData(): Array<SettingsItem> {

        // The datas
        val data = arrayOf(

                SettingsItem(getResources().getString(R.string.settings_recycler_settings), R.drawable.ic_baseline_settings_24_dark) {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                },

                SettingsItem(getResources().getString(R.string.settings_recycler_location_parameters), R.drawable.location_parameters) {
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                },

                SettingsItem(getResources().getString(R.string.settings_recycler_location_eseo), R.drawable.location) {
                    val gmmIntentUri = Uri.parse("geo:47.4932702,-0.5522239")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    startActivity(mapIntent)

                },

                SettingsItem(getResources().getString(R.string.settings_recycler_website), R.drawable.web_site) {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://eseo.fr/"))
                    startActivity(browserIntent)
                },

                SettingsItem(getResources().getString(R.string.settings_recycler_mail), R.drawable.e_mail) {
                    val i = Intent(Intent.ACTION_SENDTO)
                    i.data = Uri.parse("mailto:david.pieters@reseau.eseo.fr")
                    i.putExtra(Intent.EXTRA_SUBJECT, "Feedback/Support")
                    startActivity(Intent.createChooser(i, "Send feedback"))
                }
        )
        return data
    }
}