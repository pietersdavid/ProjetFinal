package com.example.projetfinal.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.projetfinal.R
import com.example.projetfinal.databinding.ActivityWelcomeBinding

private lateinit var binding: ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private val SPLASH_DISPLAY_LENGTH = 3000

    // Commentaires
    // Traductions
    // Vidéo
    // Thème noir

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        // Set up binding
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler().postDelayed({ /* Create an Intent that will start the Menu-Activity. */
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            this.finish()

        }, SPLASH_DISPLAY_LENGTH.toLong())

    }
}