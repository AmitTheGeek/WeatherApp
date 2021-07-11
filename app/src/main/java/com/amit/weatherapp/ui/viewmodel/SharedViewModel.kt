package com.amit.weatherapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.amit.weatherapp.data.Repository

class SharedViewModel(private val repository: Repository) : ViewModel() {

    override fun onCleared() {
        repository.clear()
        super.onCleared()
    }

    fun search(
        cityName: String,
        apiKey: String,
        error: (String) -> Unit
    ) = repository.search(cityName, apiKey, error)
    fun toggleSaved(id: Long) = repository.toggleSaved(id)

    fun observeSearch() = repository.observeSearch()
    fun observeSaved() = repository.observeSaved()
}