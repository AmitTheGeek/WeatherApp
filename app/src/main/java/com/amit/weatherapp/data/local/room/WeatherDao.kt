package com.amit.weatherapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather_table WHERE saved = 1 ORDER BY timestamp DESC")
    fun observeSaved(): LiveData<List<Weather>>

    @Query("SELECT * FROM weather_table ORDER BY timestamp DESC")
    fun observeSearch(): LiveData<Weather>

    @Query("SELECT * FROM weather_table WHERE id = :id LIMIT 1")
    fun getRecord(id: Long): Weather?

    @Query("UPDATE weather_table SET saved = NOT saved WHERE id = :id")
    fun toggleSaved(id: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weather: Weather)
}