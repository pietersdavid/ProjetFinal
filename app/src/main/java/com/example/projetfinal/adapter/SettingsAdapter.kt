package com.example.projetfinal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.projetfinal.R

// Adapter for the settings activity recycler view
class SettingsAdapter(private val data: Array<SettingsItem>) :

    // Adapter for the recycler view
    RecyclerView.Adapter<SettingsAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val rowItem: View = LayoutInflater.from(parent.context).inflate(R.layout.parameter_item, parent, false)
            return ViewHolder(
                    rowItem
            )
        }

        // Set each item with it's value
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.textView.text = data[position].name
            holder.imageIcon.setImageResource(data[position].icon)
            // Set the action when clicking
            holder.parameter_item.setOnClickListener {
                data[position].onClick?.invoke()
            }
        }

         // Get the number of elements
        override fun getItemCount(): Int {
            return data.size
        }

        // Get the elements of the item layout
        class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

            internal val textView: TextView
            internal val imageIcon: ImageView
            internal val parameter_item: ConstraintLayout

            override fun onClick(view: View) {}

            init {
                view.setOnClickListener(this)
                textView = view.findViewById(R.id.textView)
                imageIcon = view.findViewById(R.id.image_icon)
                parameter_item = view.findViewById(R.id.parameter_item)
            }
        }
    }