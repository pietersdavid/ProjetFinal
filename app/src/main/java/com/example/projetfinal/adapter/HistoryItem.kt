package com.example.projetfinal.adapter

// Data format for history adaptero
data class HistoryItem(val adderss: String, val onClick: (() -> Unit)? = null)