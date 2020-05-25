package com.application.weatherapp.repository.weather

import com.application.weatherapp.BuildConfig.API_KEY
import com.application.weatherapp.dataBase.WeatherDAO
import com.application.weatherapp.model.weather.Weather
import com.application.weatherapp.model.weather.WeatherEntity
import com.application.weatherapp.network.Resource
import com.application.weatherapp.network.ResponseHandler
import com.application.weatherapp.network.WeatherApi
import kotlinx.coroutines.delay

class WeatherRepository(
    private val weatherApi: WeatherApi,
    private val responseHandler: ResponseHandler,
    private val weatherDAO : WeatherDAO) {

    suspend fun insertDataAsync(weatherEntity : WeatherEntity) {
        weatherDAO.insertWeatherData(weatherEntity)


    }
    suspend fun truncateData() = weatherDAO.truncateData()

    suspend fun getListAsync() :List<WeatherEntity> {
        delay(10)
        return weatherDAO.fetchWeatherData()

    }

    suspend fun getWeather(location: String): Resource<Weather> {
        return try {

             val response = weatherApi.getForecast(location, "metric",API_KEY)
            return responseHandler.handleSuccess(response)


        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }


}