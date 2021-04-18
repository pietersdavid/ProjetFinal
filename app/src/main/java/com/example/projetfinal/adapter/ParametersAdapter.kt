package com.example.projetfinal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.projetfinal.R

class ParametersAdapter(private val data: Array<SettingsItem>) :

    RecyclerView.Adapter<ParametersAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val rowItem: View = LayoutInflater.from(parent.context).inflate(R.layout.parameter_item, parent, false)
            return ViewHolder(
                    rowItem
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.textView.text = data[position].name
            holder.imageIcon.setImageResource(data[position].icon)
            // Avant ici
            holder.parameter_item.setOnClickListener {
                data[position].onClick?.invoke()
            }
        }

        override fun getItemCount(): Int {
            return data.size
        }

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

            internal val textView: TextView
            internal val imageIcon: ImageView
            internal val parameter_item: ConstraintLayout

            override fun onClick(view: View) {
                Toast.makeText(
                    view.context,
                    "position : " + layoutPosition + " text : " + textView.text,
                    Toast.LENGTH_SHORT
                ).show()
            }

            init {
                view.setOnClickListener(this)
                textView = view.findViewById(R.id.textView)
                imageIcon = view.findViewById(R.id.image_icon)
                // Doit être implémenté ici
                parameter_item = view.findViewById(R.id.parameter_item)
            }
        }
    }