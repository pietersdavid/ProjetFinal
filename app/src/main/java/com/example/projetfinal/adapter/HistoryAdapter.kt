package com.example.projetfinal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.projetfinal.R

class HistoryAdapter(private val data: Array<HistoryItem>) :

        RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rowItem: View = LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        return ViewHolder(
                rowItem
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemAddress.text = data[position].adderss
        holder.history_item.setOnClickListener {
            data[position].onClick?.invoke()
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        internal val itemAddress: TextView
        internal val history_item: ConstraintLayout

        override fun onClick(view: View) {
            /*Toast.makeText(
                view.context,
                "position : " + layoutPosition + " text : " + textView.text,s
                Toast.LENGTH_SHORT
            ).show()*/
        }

        init {
            view.setOnClickListener(this)
            itemAddress = view.findViewById(R.id.history_item_address_text)
            history_item = view.findViewById(R.id.history_item)
        }
    }

}