package com.application.weatherapp.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.application.weatherapp.model.weather.WeatherEntity

@Database(entities = [WeatherEntity::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDAO() : WeatherDAO
}