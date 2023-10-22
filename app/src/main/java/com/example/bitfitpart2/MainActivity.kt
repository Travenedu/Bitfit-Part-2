package com.example.bitfitpart2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val healthRecords = mutableListOf<HealthRecord>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            (application as HealthApplication).database.HealthMetricDao().getAllMetrics()
                .collect { databaseList ->
                    databaseList.map { entity ->
                        HealthRecord(
                            entity.name,
                            entity.calories
                        )
                    }.also { mappedList ->
                        healthRecords.clear()
                        healthRecords.addAll(mappedList)
                    }
                }
        }

        val healthRecordFragment: Fragment = HealthRecordFragment(this)
        val healthSummaryFragment: Fragment = HealthSummaryFragment(healthRecords)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener { item ->
            lateinit var fragment: Fragment
            when (item.itemId) {
                R.id.nav_food -> fragment = healthRecordFragment
                R.id.nav_summary -> fragment = healthSummaryFragment
            }
            replaceFragment(fragment)
            true
        }

        // Set default selection
        bottomNavigationView.selectedItemId = R.id.nav_food

        val addSessionBtn = findViewById<Button>(R.id.launchRecordBtn)
        addSessionBtn.setOnClickListener {
            // launch the detail activity
            val intent = Intent(this, AddHealthActivity::class.java)
            this.startActivity(intent)
        }
    }

    private fun replaceFragment(newFragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_frame_layout, newFragment)
        fragmentTransaction.commit()
    }
}
