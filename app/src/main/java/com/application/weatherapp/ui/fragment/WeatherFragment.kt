package com.application.weatherapp.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.weatherapp.R
import com.application.weatherapp.databinding.FragmentViewBinding
import com.application.weatherapp.helper.UiHelper
import com.application.weatherapp.model.weather.Weather
import com.application.weatherapp.model.weather.WeatherEntity
import com.application.weatherapp.ui.activity.MainActivity
import com.application.weatherapp.ui.adapter.WeatherAdapter
import com.application.weatherapp.ui.viewmodel.WeatherViewModel
import com.github.harmittaa.koinexample.networking.Resource
import com.github.harmittaa.koinexample.networking.Status
import kotlinx.android.synthetic.main.fragment_view.*
import kotlinx.coroutines.delay
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.ArrayList

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class WeatherFragment : Fragment() {
    private val uiHelper : UiHelper by inject()
    private val weatherVM : WeatherViewModel by viewModel()
    private lateinit var binding: FragmentViewBinding
    private val weatherAdapter = WeatherAdapter()

    lateinit var cities :Array<String>
    private val observer = Observer<Resource<Weather>> {
        when (it.status) {
            Status.SUCCESS -> updateTemperature(it.data!!)
            Status.ERROR -> showError(it.message!!)
            Status.LOADING -> showLoading()
        }
    }

    override fun onResume() {
        super.onResume()
        initRecyclerView()
        subscribeObserver()
    }
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_view, container, false)
        binding.viewModel = weatherVM
        weatherVM.weather.observe(viewLifecycleOwner, observer)
        return binding.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btn_enter).setOnClickListener {

            val input = view.findViewById<EditText>(R.id.edt_name).text.toString().split(",").toTypedArray()
           cities =input
            weatherVM.truncateData()
            if(cities.size in 3..7){
                    weatherVM.getWeather(cities)
            }
            else{
                uiHelper.showSnackBar(binding.root,resources.getString(R.string.msg_cities_limit))
            }
            uiHelper.hideKeyboard(view)

        }
    }

    //Setup the adapter class for the RecyclerView
    private fun initRecyclerView() {
        recylv_weather?.layoutManager = LinearLayoutManager(binding.root.context)
        recylv_weather?.adapter = weatherAdapter
    }
    private fun subscribeObserver() {
        // OBSERVABLES ---
        weatherVM.getAllWeatherData().observe(viewLifecycleOwner, Observer {

            it?.let {
                if (it.isNotEmpty()) {
                    uiHelper.sortTimeStamp(it as ArrayList<WeatherEntity>)
                    weatherAdapter.items = it
                    recylv_weather.scrollToPosition(0)
                }

            }
        })
    }

     private fun showLoading() {
        binding.pbFetchData.visibility = View.VISIBLE
    }

     private fun showError(message: String) {
        uiHelper.showSnackBar(binding.root, "Error: $message")
        binding.pbFetchData.visibility = View.GONE

    }

     private fun updateTemperature(weather: Weather) {
        binding.pbFetchData.visibility = View.GONE
        val currentTimestamp = System.currentTimeMillis()
        weatherVM.addWeatherData(WeatherEntity(currentTimestamp,weather.name,weather.temp.temp_min,weather.temp.temp_max,weather.weather.get(0).description,weather.wind.speed))

         if(cities.get(cities.lastIndex).capitalize().contains(weather.name.capitalize())){

             subscribeObserver()
         }
    }
}
