package com.application.weatherapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.weatherapp.R
import com.application.weatherapp.databinding.FragmentListBinding
import com.application.weatherapp.databinding.FragmentViewBinding
import com.application.weatherapp.helper.UiHelper
import com.application.weatherapp.model.weather.WeatherEntity
import com.application.weatherapp.ui.adapter.WeatherAdapter
import com.application.weatherapp.ui.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_view.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.ArrayList

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class WeatherListFragment : Fragment() {

    private val uiHelper : UiHelper by inject()
    private val weatherVM : WeatherViewModel by viewModel()
    private lateinit var binding: FragmentListBinding
    private val weatherAdapter = WeatherAdapter()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        return binding.root
    }



    override fun onResume() {
        super.onResume()

        initRecyclerView()
        subscribeObserver()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    //Setup the adapter class for the RecyclerView
    private fun initRecyclerView() {
        recylv_weather_?.layoutManager = LinearLayoutManager(binding.root.context)
        recylv_weather_?.adapter = weatherAdapter
    }
    private fun subscribeObserver() {
        // OBSERVABLES ---
        weatherVM.getAllWeatherData().observe(viewLifecycleOwner, Observer {

            it?.let {
                if (it.isNotEmpty()) {
                    uiHelper.sortTimeStamp(it as ArrayList<WeatherEntity>)
                    weatherAdapter.items = it
                    recylv_weather_.scrollToPosition(0)
                }

            }
        })
    }
}
