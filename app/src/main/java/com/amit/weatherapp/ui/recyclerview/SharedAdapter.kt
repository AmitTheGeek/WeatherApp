package com.amit.weatherapp.ui.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amit.weatherapp.R
import com.amit.weatherapp.data.local.room.Weather

class SharedAdapter(
    private val weatherList: List<Weather>,
    private val onSaved: (Long) -> Unit
) : RecyclerView.Adapter<SharedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SharedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_weather_item, parent, false)
        return SharedViewHolder(view, onSaved)
    }

    override fun onBindViewHolder(holder: SharedViewHolder, position: Int) {
        holder.bind(weatherList[position])
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }
}