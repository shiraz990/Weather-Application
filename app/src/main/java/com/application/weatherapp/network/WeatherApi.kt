package com.application.weatherapp.network

import com.application.weatherapp.model.weather.Weather
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    suspend fun getForecast(@Query("q")location: String,
                            @Query("units") unit: String,
                            @Query("APPID") apiKey: String): Weather
}