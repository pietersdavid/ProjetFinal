package com.example.projetfinal.adapter

import android.graphics.drawable.Drawable

// Définition de la Class qui sera dans notre RecyclerView
data class SettingsItem(val name: String, val icon: Int, val onClick: (() -> Unit)? = null)