package com.application.weatherapp.di

import androidx.room.Room
import com.application.weatherapp.BuildConfig
import com.application.weatherapp.BuildConfig.API_KEY
import com.application.weatherapp.dataBase.WeatherDatabase
import com.application.weatherapp.helper.UiHelper
import com.application.weatherapp.model.weather.Weather
import com.application.weatherapp.network.WeatherApi
import com.application.weatherapp.repository.weather.WeatherRepository
import com.application.weatherapp.ui.viewmodel.WeatherViewModel
import com.application.weatherapp.util.Constants.Companion.WEATHER_DATABASE_NAME
import com.github.harmittaa.koinexample.networking.Resource
import com.github.harmittaa.koinexample.networking.ResponseHandler
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun injectFeature() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
            listOf(viewModelModule,
                   repositoryModule,
                   weatherAppModule,
                    networkModule,
                   uiHelperModule)
    )
}

val viewModelModule = module {
    viewModel { WeatherViewModel(weatherRepository = get()) }
 }

val repositoryModule = module {
    single { WeatherRepository( weatherApi = get(),responseHandler = get(),weatherDAO = get()) }
}

val uiHelperModule = module {
    single { UiHelper(androidContext()) }
}


val weatherAppModule = module {

    // Room Database
    single { Room.databaseBuilder(androidApplication(), WeatherDatabase::class.java, WEATHER_DATABASE_NAME).build() }

    // WeatherDAO
    single { get<WeatherDatabase>().weatherDAO() }
}



val forecastModule = module {
    factory { WeatherRepository(get(),get(), get()) }
}

val networkModule = module {
    factory { provideOkHttpClient( get()) }
    factory { provideForecastApi(get()) }
    factory { provideLoggingInterceptor() }
    single { provideRetrofit(get()) }
    factory { ResponseHandler() }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(BuildConfig.API_URL).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient( loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient().newBuilder().addInterceptor(loggingInterceptor).build()
}

fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val logger = HttpLoggingInterceptor()
    logger.level = HttpLoggingInterceptor.Level.BASIC
    return logger
}

fun provideForecastApi(retrofit: Retrofit): WeatherApi = retrofit.create(WeatherApi::class.java)



