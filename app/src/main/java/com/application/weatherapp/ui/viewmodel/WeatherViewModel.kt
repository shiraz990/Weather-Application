package com.application.weatherapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.application.weatherapp.base.BaseViewModel
import com.application.weatherapp.model.weather.WeatherEntity
import com.application.weatherapp.network.Resource
import com.application.weatherapp.repository.weather.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel(private val weatherRepository : WeatherRepository) : BaseViewModel() {


    private val location = MutableLiveData<Array<String>>()

    fun getWeather(input: Array<String>) {
        location.value = input
    }

    var weather = location.switchMap { location ->

            liveData(Dispatchers.IO) {
                for (city in location){
                    emit(Resource.loading(null))
                    emit(weatherRepository.getWeather(city))
                }
            }

    }
    // FOR DATA --
    private lateinit var data : MutableLiveData<List<WeatherEntity>>

    fun addWeatherData(weatherEntity : WeatherEntity)  {
        ioScope.launch { weatherRepository.insertDataAsync(weatherEntity) }
    }


    fun truncateData()  {
        ioScope.launch { weatherRepository.truncateData() }
    }
    fun getAllWeatherData() : LiveData<List<WeatherEntity>> {
        data = MutableLiveData()

        ioScope.launch { data.postValue(weatherRepository.getListAsync()) }

        return data
    }
}