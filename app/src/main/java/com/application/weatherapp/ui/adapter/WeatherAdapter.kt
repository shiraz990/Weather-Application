package com.application.weatherapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.weatherapp.R
import com.application.weatherapp.callBacks.DiffUtilCallback
import com.application.weatherapp.databinding.AdapterWeatherBinding
import com.application.weatherapp.model.weather.WeatherEntity
import kotlin.properties.Delegates

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.MyViewHolder>(), DiffUtilCallback {

    var items: List<WeatherEntity> by Delegates.observable(emptyList()) { _, oldItem, newItem ->
        autoNotify(oldItem, newItem) { old, new -> old.timeStamp == new.timeStamp }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: AdapterWeatherBinding = DataBindingUtil
            .inflate(LayoutInflater.from(parent.context), R.layout.adapter_weather, parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items[position])
    }

    // Gets the number of Items in the list
    override fun getItemCount(): Int = items.size

    inner class MyViewHolder(private val binding: AdapterWeatherBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(weatherEntity: WeatherEntity) {
            binding.entity = weatherEntity
        }
    }
}
