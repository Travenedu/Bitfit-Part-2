package com.example.bitfitpart1

import android.app.Application

class HealthApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getInstance(this) }
}