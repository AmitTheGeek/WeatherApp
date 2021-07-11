package com.amit.weatherapp.data.remote

import com.amit.weatherapp.data.remote.response.WeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface NetworkService {

    companion object {
        const val TAG = "NetworkService"
        const val BASE_URL = "https://api.openweathermap.org/"
        const val API_KEY = "ffb46dae9fd1292b3927d1bca63c9bad"
    }

    @GET("data/2.5/weather")
    fun searchByCityName(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String
    ): Single<WeatherResponse>
}