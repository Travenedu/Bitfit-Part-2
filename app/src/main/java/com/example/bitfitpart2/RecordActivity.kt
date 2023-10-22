package com.example.bitfitpart2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class RecordActivity : AppCompatActivity() {
    private lateinit var healthNameInputView: EditText
    private lateinit var healthCaloriesInputView: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        healthNameInputView = findViewById(R.id.inputName)
        healthCaloriesInputView = findViewById(R.id.inputCalories)

        val saveRecordBtn = findViewById<Button>(R.id.saveWorkoutBtn)
        var healthRecord: HealthRecord? = null

        saveRecordBtn.setOnClickListener {
            healthRecord = HealthRecord(
                healthNameInputView.text.toString(),
                healthCaloriesInputView.text.toString().toInt()
            )

            healthRecord?.let { obj ->
                lifecycleScope.launch(IO) {
                    (application as HealthApplication).database.HealthMetricDao().insert(
                        HealthMetricEntity(
                            name = obj.name,
                            calories = obj.calories
                        )
                    )
                }
            }

            // launch main activity
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
        }
    }
}