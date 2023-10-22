package com.example.bitfitpart2

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "health_metrics")
data class HealthMetricEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name="name") val name: String,
    @ColumnInfo(name="calorieCount") val calories: Int
)

