package com.example.bitfitpart1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val healthList: MutableList<HealthRecord> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        val adapter = HealthAdapter(healthList)

        lifecycleScope.launch {
            (application as HealthApplication).database.HealthMetricDao().getAllMetrics()
                .collect { databaseList ->
                    databaseList.map { entity ->
                        HealthRecord(
                            entity.name,
                            entity.calories
                        )
                    }.also { mappedList ->
                        healthList.clear()
                        healthList.addAll(mappedList)
                        adapter.notifyDataSetChanged()
                    }
                }
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val addSessionBtn = findViewById<Button>(R.id.addButton)

        addSessionBtn.setOnClickListener {
            // launch the detail activity
            val intent = Intent(this, AddHealthActivity::class.java)
            this.startActivity(intent)
        }
    }
}
