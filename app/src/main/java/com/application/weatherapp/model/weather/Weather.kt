package com.application.weatherapp.model.weather

import com.google.gson.annotations.SerializedName

data class Weather(

    val name: String,
    val id: String,

    @SerializedName("main")
    val temp: TempData,
    @SerializedName("wind")
    val wind: WindData,
    @SerializedName("weather")
    val weather: ArrayList<WeatherData>

)

data class TempData(
    val temp: Double,
    val temp_min:Double,
    val temp_max:Double,

    val humidity: Int
)

data class WindData(
    val speed: Double
)


data class WeatherData(
    val description: String
)