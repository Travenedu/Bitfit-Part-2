package com.example.bitfitpart1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AddHealthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_health_item)

        val healthDao = (application as HealthApplication).database.HealthMetricDao()

        // Find the EditText views by their IDs
        val healthNameEditText = findViewById<EditText>(R.id.healthItemNameEditText)
        val caloriesEditText = findViewById<EditText>(R.id.calorieCountEditText)

        // Find the save button by its ID
        val Button = findViewById<Button>(R.id.addFoodButton)

        // Set a click listener for the save button
        Button.setOnClickListener {
            val healthName = healthNameEditText.text.toString().trim()
            val caloriesText = caloriesEditText.text.toString()
            val calories = caloriesText.toIntOrNull()

            if (healthName.isNotEmpty() && calories != null) {
                val healthRecord = HealthMetricEntity(
                    name = healthName,
                    calories = calories
                )
                saveHealthRecord(healthDao, healthRecord)
            }
        }
    }

    private fun saveHealthRecord(healthDao: HealthMetricDao, healthRecord: HealthMetricEntity) {
        runBlocking {
            launch(Dispatchers.IO) {
                healthDao.insert(healthRecord)
            }
        }
        finish()
    }
}