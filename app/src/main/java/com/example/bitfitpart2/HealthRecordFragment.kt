package com.example.bitfitpart2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class HealthRecordFragment(private val activity: MainActivity) : Fragment() {
    private val healthRecords = mutableListOf<HealthRecord>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_health_record, container, false)
        val recyclerView = view.findViewById<View>(R.id.health_list) as RecyclerView
        val recordsAdapter = HealthAdapter(healthRecords)
        val context = view.context

        lifecycleScope.launch {
            (activity.application as HealthApplication).database.HealthMetricDao().getAllMetrics()
                .collect { databaseList ->
                databaseList.map { entity ->
                    HealthRecord(
                        entity.name,
                        entity.calories,
                        // entity.workoutTime
                    )
                }.also { mappedList ->
                    healthRecords.clear()
                    healthRecords.addAll(mappedList)
                    recordsAdapter.notifyDataSetChanged()
                }
            }
        }

        recyclerView.adapter = recordsAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        return view
    }
}