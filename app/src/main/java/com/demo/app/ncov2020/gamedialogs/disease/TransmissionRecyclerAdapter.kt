package com.demo.app.ncov2020.gamedialogs.disease

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.demo.app.ncov2020.R
import com.demo.app.ncov2020.logic.Disease.Ability
import com.demo.app.ncov2020.logic.Disease.Transmission

class TransmissionRecyclerAdapter(var items: List<Transmission>, var currItems: List<Transmission>, val callback: Callback) : RecyclerView.Adapter<TransmissionRecyclerAdapter.MainHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = MainHolder(LayoutInflater.from(parent.context).inflate(R.layout.disease_item, parent, false))
    override fun getItemCount() = items.size
    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bind(items[position])
    }
    inner class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name = itemView.findViewById<TextView>(R.id.name)
        fun bind(item: Transmission) {
            name.text = item.name
            if(currItems.contains(item)) {
                name.setTextColor(Color.GRAY)
            }
            else {
                name.setTextColor(Color.BLACK)
            }
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) callback.onItemClicked(items[adapterPosition])
            }
        }
    }
    interface Callback {
        fun onItemClicked(item: Transmission)
    }
}