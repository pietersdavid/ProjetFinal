package com.example.projetfinal.adapter

// Data format for menu adapter
data class MenuItem(val name: String, val icon: Int, val onClick: (() -> Unit)? = null)