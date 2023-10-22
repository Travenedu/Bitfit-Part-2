package com.example.bitfitpart2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class HealthAdapter(private val healthMetric: List<HealthRecord>) : RecyclerView.Adapter<HealthAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.health_item_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val healthMetric = healthMetric[position]
        holder.healthNameTextView.text = healthMetric.name
        holder.calorieCountTextView.text = healthMetric.calories.toString()
    }

    override fun getItemCount(): Int {
        return healthMetric.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val healthNameTextView: TextView = itemView.findViewById(R.id.healthNameTextView)
        val calorieCountTextView: TextView = itemView.findViewById(R.id.calorieCountTextView)
    }
}

