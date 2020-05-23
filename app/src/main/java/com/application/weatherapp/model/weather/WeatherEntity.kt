package com.application.weatherapp.model.weather

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.application.weatherapp.util.Constants.Companion.CITY_NAME
import com.application.weatherapp.util.Constants.Companion.TABLE_NAME
import com.application.weatherapp.util.Constants.Companion.TEMP_MAX
import com.application.weatherapp.util.Constants.Companion.TEMP_MIN
import com.application.weatherapp.util.Constants.Companion.TIME_STAMP
import com.application.weatherapp.util.Constants.Companion.WEATHER_DESC
import com.application.weatherapp.util.Constants.Companion.WIND_SPEED

@Entity(tableName = TABLE_NAME)
data class WeatherEntity (@PrimaryKey @ColumnInfo(name = TIME_STAMP) val timeStamp : Long,
                          @ColumnInfo(name = CITY_NAME) val cityName : String?,
                          @ColumnInfo(name = TEMP_MIN) val tempMin : Double?,
                          @ColumnInfo(name = TEMP_MAX) val tempMax : Double?,
                          @ColumnInfo(name = WEATHER_DESC) val weatherDesc : String?,
                          @ColumnInfo(name = WIND_SPEED) val windSpeed : Double?)