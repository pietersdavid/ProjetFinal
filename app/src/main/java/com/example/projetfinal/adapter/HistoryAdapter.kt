package com.example.projetfinal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.projetfinal.R

// Adapter for the history activity recycler view
class HistoryAdapter(private val data: Array<HistoryItem>) :

    // Adapter for the recycler view
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val rowItem: View = LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
            return ViewHolder(
                    rowItem
            )
        }

        // Set each item with it's value
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemAddress.text = data[position].adderss
            // Set the action when clicking
            holder.history_item.setOnClickListener {
                data[position].onClick?.invoke()
            }
        }

        // Get the number of elements
        override fun getItemCount(): Int {
            return data.size
        }

        // Get the elements of the item layout
        class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

            internal val itemAddress: TextView
            internal val history_item: ConstraintLayout

            override fun onClick(view: View) {        }

            init {
                view.setOnClickListener(this)
                itemAddress = view.findViewById(R.id.history_item_address_text)
                history_item = view.findViewById(R.id.history_item)
            }
        }
    }