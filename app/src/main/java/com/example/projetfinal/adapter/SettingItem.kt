package com.example.projetfinal.adapter

// Data format for setttings adapter
data class SettingsItem(val name: String, val icon: Int, val onClick: (() -> Unit)? = null)