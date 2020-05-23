package com.application.weatherapp.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.application.weatherapp.model.weather.WeatherEntity

@Dao
interface WeatherDAO {

    /**
     * Save Weather Data weatherEntity
     */
    @Insert
    suspend fun insertWeatherData(weatherEntity : WeatherEntity)

    /**
     * Get all the data of WeatherEntity
     */

    @Query("SELECT * FROM Weather")
    suspend fun fetchWeatherData() : List<WeatherEntity>


    /**
     * Truncate all the data of WeatherEntity
     */

    @Query(" DELETE FROM Weather")
    suspend fun truncateData()
}