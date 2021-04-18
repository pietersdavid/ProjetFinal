package com.example.projetfinal.adapter

import android.graphics.drawable.Drawable

// Définition de la Class qui sera dans notre RecyclerView
data class HistoryItem(val adderss: String, val onClick: (() -> Unit)? = null)