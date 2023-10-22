package com.example.bitfitpart2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HealthMetricDao {
    @Insert
    fun insert(metric: HealthMetricEntity)

    @Query("SELECT * FROM health_metrics")
    fun getAllMetrics(): Flow<List<HealthMetricEntity>>
}