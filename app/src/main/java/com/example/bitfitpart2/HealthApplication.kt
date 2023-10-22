package com.example.bitfitpart2

import android.app.Application

class HealthApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getInstance(this) }
}