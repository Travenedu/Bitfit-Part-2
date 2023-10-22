package com.example.bitfitpart2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.math.BigDecimal
import java.math.RoundingMode

class HealthSummaryFragment(private val healthRecords: List<HealthRecord>) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_health_summary, container, false)

        if (healthRecords.isNotEmpty()) {
            Log.v("Health Summary/", "The list of records is not empty")
            updateView(view, healthRecords)
        } else {
            Log.e("Health Summary/", "The list of records is empty")
        }
        return view
    }

    private fun updateView(view: View, healthRecords: List<HealthRecord>) {
        val avgCaloriesView = view.findViewById(R.id.avgCaloriesNumber) as TextView
        val minCaloriesView = view.findViewById(R.id.minCaloriesNumber) as TextView
        val maxCaloriesView = view.findViewById(R.id.maxCaloriesNumber) as TextView
        val totalCaloriesView = view.findViewById(R.id.totalCaloriesNumber) as TextView


        var totalRecords = healthRecords.size
        var totalCalories = 0
        var minCalories = 100000
        var maxCalories = 0
        // var totalTime = 0.0

        // Get minimum
        healthRecords.forEach { record ->
            if (record.calories != null) {
                if (record.calories < minCalories){
                    minCalories = record.calories
                }
            }
        }

        // Get maximum
        healthRecords.forEach { record ->
            if (record.calories != null) {
                if (record.calories > maxCalories){
                    maxCalories = record.calories
                }
            }
        }

        healthRecords.forEach { record ->
            if (record.calories != null) {
                totalCalories += record.calories
            }
        }

        var avgCalories = BigDecimal(totalCalories / totalRecords).setScale(2, RoundingMode.HALF_UP).toDouble()
        // var avgTime = BigDecimal(totalTime / totalRecords).setScale(2, RoundingMode.HALF_UP).toDouble()

        avgCaloriesView.text = "$minCalories Cal."
        minCaloriesView.text = "$avgCalories Cal."
        maxCaloriesView.text = "$maxCalories Cal"
        totalCaloriesView.text = "$totalCalories Cal"

//        if (totalRecords == 1) {
//            maxCaloriesView.text = "$totalRecords workout"
//        } else {
//            maxCaloriesView.text = "$totalRecords workouts"
//        }
//        totalCaloriesView.text = "$totalCalories Cal."
        // totalTimeView.text = "$totalTime mins."
    }

}